function [Xopt,FRmin]=RAND_FORM_POIK(Xsimp)
global ax ay az bx by bz;
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;
%Ckv=0.707106781186548;
ax=-2.; bx=2.;
ay=-2.; by=2.;
az=-2.; bz=2.;
MR=[];
XS=Xsimp;
[NX,M]=size(Xsimp);
for IT=1:NX-1
    XJ=XS(IT,:);Ri=[];
    for I=1:IT
        Ri=[Ri,0];
    end
    for I=IT+1:NX
    XI=XS(I,:);
    R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
    Ri=[Ri,R];
    end
    MR=[MR;Ri];
end
for J=1:NX-1
    for I=J+1:NX
        MR(I,J)=MR(J,I);
    end
end

X0=[];St=[];St1=[];
for I=1:NX-1
   St=[St,0];St1=[St1,1];
end
St=[St,1]; St1=[St1,1];X0=[X0,Xsimp(NX,:)];
X=Xsimp;Ntop=NX;St0=St;X00=X0;

    St=St0;Sig=0.015;
    [XR,FR2,exitflag,output] = fminunc(@Fmoropt3,X0,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600)); 
    %[XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-15,'MaxFunEvals',660,'MaxIter',660));
    %end
    St=St1;
    Xsimp(NX,1)=XR(1);Xsimp(NX,2)=XR(2);Xsimp(NX,3)=XR(3);
    X00=[];Xopt=Xsimp;
    for I=1:NX
        X00=[X00,Xsimp(I,:)];
    end
    X0=[];FRmin=FR2;Xmin=XR;Nisp=50;
    FRbas=FR2;
    for I=1:NX*3
            VR(I,1)=82;VR(I,2)=82;VR(I,3)=82;
    end
    for Iit=1:Nisp
        %for I=1:NX*3
        %    X0(I)=X00(I)+unifrnd(-Sig,Sig);
        %end
    FLAG=1;DR=[];    
   [X0,VR,DR]=GEN_RAND_POIK(X00,VR,DR,FRbas,FR2,FLAG);
    %[XR,FR,exitflag,output]=fminsearch(@Fmoropt3,X0,optimset('TolX',1e-12,'MaxFunEvals',2000,'MaxIter',2000)); 
    [XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-15,'MaxFunEvals',660,'MaxIter',660));
    %[XR,FR,exitflag,output]=fminsearch(@Fmoropt3,XR,optimset('TolX',1e-6,'MaxFunEvals',2000,'MaxIter',2000)); 
    FLAG=0;
    [X0,VR,DR]=GEN_RAND_POIK(X00,VR,DR,FRbas,FR2,FLAG);
    if FRmin>FR2
        FRmin=FR2;Xmin=XR;
        Xopt=[];k=1;
        for I=1:NX
             XRR=[XR(k),XR(k+1),XR(k+2)];
             k=k+3;
             Xopt=[Xopt; XRR];
        end
        %X00=XR;FRbas=FR2;
    end
    if Iit==50 || Iit==200 ||Iit==300 || Iit==400 || Iit==500
        Nisp=2*Nisp;
    end
    end

return