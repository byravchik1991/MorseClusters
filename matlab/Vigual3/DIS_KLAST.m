function [TRf,Xresult]=DIS_KLAST(Xs)

global Ntop;
global X;
global St;
global r0;
 
[NX,M]=size(Xs);
St=zeros(1,NX+1);
St(NX+1)=1;
% for I=1:NX
%    St=[St,0];
% end
% St=[St,1]; 
Rmax=1.4;
TRs=[];%просто тройки атомов
TRn=[];%тройки после оптимизации
TRf=[];%тройки с различными энергиями
Xres=[];%массив с положениями всех атомов для всех конформаций из TRf

for IT=1:NX-1   
    XJ=Xs(IT,:);
    Ri=zeros(1, NX-IT);
    
    %найдем расстояния от атома с номером IT до всех остальных атомов с
    %большим номером (запишем в массив Ri)
    for I=IT+1:NX
        XI=Xs(I,:);
        R=((XJ(1)-XI(1))^2+(XJ(2)-XI(2))^2+(XJ(3)-XI(3))^2)^0.5;
        Ri(I-IT)=R;
    end
   
    %отсортируем полученные расстояния
    [Rm,Ir]=sort(Ri);
    
    %получим упорядоченную последовательность номеров атомов, которые ближе
    %всего к атому с номером IT (Ir-эта последовательность)
    [M,NR]=size(Rm);
    for I=1:NR
        Ir(I)=Ir(I)+IT;
    end
   
    %выберем только те номера атомов, расстояние до которых меньше чем Rmax
    NTRI=[];
    for I=1:NR
        if Rm(I) < Rmax
        %if abs(Rm(1)-Rm(I)) < Rm(1)*0.0005
            NTRI=[NTRI,Ir(I)];
        else
            break
        end
    end
    
    %сформируем тройки из номеров атомов, к которым можно присоединить
    %четвертый атом (TRs-массив троек атомов)
    [M,NT]=size(NTRI);
    for I=1:NT-1
        for J=I+1:NT            
            XI=Xs(NTRI(I),:);
            XJ=Xs(NTRI(J),:);
            R=((XJ(1)-XI(1))^2+(XJ(2)-XI(2))^2+(XJ(3)-XI(3))^2)^0.5;
            
            if R < Rmax
                SN=[IT,NTRI(I),NTRI(J)];
                SNN=sort(SN);
                SNN=[SNN,0];
                TRs=[TRs;SNN];
            end
        end
    end
end


Ak=zeros(NX,1);
for J=1:NX
  Ak(J)=J;
end

[NTR,M]=size(TRs);
for I=1:NTR
    %укладываем новый атом
    TRIAD=[TRs(I,1),TRs(I,2),TRs(I,3)];
    [Xsimp,Ak,FR]=UKLAD(Xs,TRIAD,Ak);
    
    X=Xsimp;
    Ntop=NX+1;
    %параметры, по которым происходит оптимизация
    X0=Xsimp(NX+1,:);
    %X0=Xsimp;
    
    if FR < 0
        %оптимизация 
        %[XR,FR2] = fminsearch(@(x) UmorseResult(x, Ntop, r0), Xsimp);
       % [XR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, Ntop, r0),Xsimp,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));
      
      % XR=[Xsimp; XR];
      XR=Xsimp;
        
        E = UmorseResult(XR, Ntop, r0);
       %[XR,FR2,exitflag,output] = fminunc(@Fmoropt3,X0,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600)); 
       %[XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-15,'MaxFunEvals',660,'MaxIter',660));
       
       %TRn=[TRn;[TRs(I,1),TRs(I,2),TRs(I,3),FR2]];
       TRn=[TRn;[TRs(I,1),TRs(I,2),TRs(I,3),E]];
        
        [NTf,M]=size(TRf);
        if NTf==0
            %[XR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, Ntop, r0),Xsimp,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));
            %TRf=[TRf;[TRs(I,1),TRs(I,2),TRs(I,3),FR2]];
            TRf=[TRf;[TRs(I,1),TRs(I,2),TRs(I,3),E]];
            Xres=[Xres;XR];
        end
       
        %оставляем только те тройки, которые достаточно отличаются между
        %собой по энергии
        for J=1:NTf
           % if abs(FR2-TRf(J,4)) < abs(FR2*0.0005)
           if abs(E-TRf(J,4)) < abs(E*0.0005)
                break
            end
            if J==NTf
                %TRf=[TRf;[TRs(I,1),TRs(I,2),TRs(I,3),FR2]];
                TRf=[TRf;[TRs(I,1),TRs(I,2),TRs(I,3),E]];
                Xres=[Xres;XR];
            end
        end
    end
end

% Xresult=[];
% [m,n]=size(TRf);
%  for j=1:m 
%      Xj=Xres((j-1)*Ntop+1:(j-1)*Ntop+Ntop,:);
%      [XjR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, Ntop, r0),Xj,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));
%      TRf(j,4)=FR2;
%      Xresult=[Xresult; XjR];
%  end

 Xresult=Xres;
 
%TRn=[TRn; TRf(1,1), TRf(1,2), 8, 0; TRf(1,2), TRf(1,3), 8, 0; TRf(1,1), TRf(1,3), 8, 0;  ];
% plot_FIG(TRn,Xsimp);

return

