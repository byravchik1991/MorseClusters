function [TRn,FR]=DIS_KLAST_M(Xs)
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;

[NX,M]=size(Xs);
St=[];
for I=1:NX
   St=[St,0];
end
St=[St,1]; 
Rmax=1.35;
TRn=[];TRs=[];
for IT=1:NX-1
    XJ=Xs(IT,:);Ri=[];
    for I=IT+1:NX
    XI=Xs(I,:);
    R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
    Ri=[Ri,R];
    end
    [Rm,Ir]=sort(Ri);
    [M,NR]=size(Rm);
    for I=1:NR
        Ir(I)=Ir(I)+IT;
    end
    NTRI=[];
    for I=1:NR
        if Rm(I) < Rmax
            NTRI=[NTRI,Ir(I)];
        else
            break
        end
    end
    [M,NT]=size(NTRI);
    for I=1:NT-1
        for J=I+1:NT
            XI=Xs(NTRI(I),:);Ri=[];
            XJ=Xs(NTRI(J),:);
            R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
            if R < Rmax
                SN=[IT,NTRI(I),NTRI(J)];
                SNN=sort(SN);SNN=[SNN,0];
                TRs=[TRs;SNN];
            end
        end
    end
end
[NTR,M]=size(TRs);
Ak=[];
for J=1:NX
  Ak=[Ak;J];
end
for I=1:NTR
    TRIAD=[TRs(I,1),TRs(I,2),TRs(I,3)];
    [Xsimp,Ak,FR]=UKLAD(Xs,TRIAD,Ak);
    if TRIAD(1)==6 && TRIAD(2)==11 && TRIAD(3)==50
        RRR=5;
    end
    %if FR<-3*(NX-1)
    if FR < -3*(NX-1)/2
        TRn=[TRn;[TRs(I,1),TRs(I,2),TRs(I,3),FR]];
    end
end
plot_FIG(TRn,Xs);
[FR,FS,Nt]=Fmoropt(Xs);
return

