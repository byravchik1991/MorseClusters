function [Xn,TRn]=SORT_MIN_TR(TRs,TRf,Xs)
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;
[NT,M]=size(TRs);
[NTf,M]=size(TRf);
[NX,M]=size(Xs);
%plot_FIG(TRs,Xs);
FTr=TRf(:,4);
[Tm,IT]=sort(FTr);
TRn=[];
Imin=1;
SN=TRf(IT(Imin),:);
TRn=[TRn;[SN(1),SN(2),NX+1,0]];
TRn=[TRn;[SN(1),SN(3),NX+1],0];
TRn=[TRn;[SN(2),SN(3),NX+1],0];
for I=1:NT
    if TRs(I,4)<0 && I~=Imin
        TRn=[TRn;TRs(I,:)];
    end
end
Ak=[];
for J=1:NX
  Ak=[Ak;J];
end
TRIAD=[SN(1),SN(2),SN(3)];
[Xsimp,Ak,FR]=UKLAD(Xs,TRIAD,Ak);
        St=[];
        for I=1:NX
            St=[St,0];
        end
        St=[St,1]; 
        X0=[];X=Xsimp;Ntop=NX+1;
        X0=[X0,Xsimp(NX+1,:)];
        [XR,FR2,exitflag,output] = fminunc(@Fmoropt3,X0,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600)); 
        X0=[];St=[];
        for I=1:NX
            X0=[X0,Xsimp(I,:)];
            St=[St,1];
        end
        St=[St,1];
        Xsimp(NX+1,1)=XR(1);Xsimp(NX+1,2)=XR(2);Xsimp(NX+1,3)=XR(3);
        X0=[X0,Xsimp(NX+1,:)];
        X=Xsimp;Ntop=NX+1;
        [XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-15,'MaxFunEvals',660,'MaxIter',660));
        Xopt=[];k=1;
        for I=1:NX+1
            XRR=[XR(k),XR(k+1),XR(k+2)];
            k=k+3;
            Xopt=[Xopt; XRR];
        end
Xn=Xopt;
%Xn=Xsimp;
%plot_FIG(TRn,Xn);
%Ris_top2(Xopt,3,Ak);
XJ=Xn(NX+1,:);Ri=[];
for I=1:NX
    XI=Xn(I,:);
    R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
    Ri=[Ri,R];
end
[Rm,Ir]=sort(Ri);
NTRI=[];
for I=1:NX-1
    if Rm(I) < 1.3
        FL=0;
        for J=1:3
            if Ir(I)==TRIAD(J)
                FL=1;
                break
            end
        end
        if FL==0
            NTRI=[NTRI,Ir(I)];
        end
    end
end
[M,NTII]=size(NTRI);

if NTII > 1
    [NT1,M]=size(TRn); 
    for I=1:NTII-1
        for J=I+1:NTII
            XI=Xn(NTRI(I),:); XJ=Xn(NTRI(J),:);
            R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
            if R < 1.3
                SN=[NTRI(I),NTRI(J),NX+1];
                SNN=sort(SN);
                if DOBL_TR(TRn,SNN,NT1)
                    TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
                end
            end
        end
    end
end

for KJ=1:NTII    
    NTR=NTRI(KJ);
    [NT1,M]=size(TRn); TRd=[];
    for K=1:NT1
         if TRn(K,1)==NTR || TRn(K,2)==NTR || TRn(K,3)==NTR
              TRd=[TRd;TRn(K,:)];
         end
    end
    [NTd,M]=size(TRd);
    for K=1:NTd
        if (TRIAD(1)==TRd(K,1) && TRIAD(2)==TRd(K,2))
            SN=[TRIAD(1),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            SN=[TRIAD(2),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
        if (TRIAD(1)==TRd(K,1) && TRIAD(3)==TRd(K,3))
            SN=[TRIAD(1),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            SN=[TRIAD(3),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
        if (TRIAD(2)==TRd(K,2) && TRIAD(3)==TRd(K,3))
            SN=[TRIAD(2),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            SN=[TRIAD(3),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
        if (TRIAD(1)==TRd(K,1) || TRIAD(1)==TRd(K,2) || TRIAD(1)==TRd(K,3))
            SN=[TRIAD(1),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
        if (TRIAD(2)==TRd(K,1) || TRIAD(2)==TRd(K,2) || TRIAD(2)==TRd(K,3))
            SN=[TRIAD(2),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
        if (TRIAD(3)==TRd(K,1) || TRIAD(3)==TRd(K,2) || TRIAD(3)==TRd(K,3))
            SN=[TRIAD(3),NTR,NX+1];
            SNN=sort(SN);
            if DOBL_TR(TRn,SNN,NT1)
                TRn=[TRn;[SNN(1),SNN(2),SNN(3),0]];
            end
            continue
        end
    end
end
plot_FIG(TRn,Xn);
return

