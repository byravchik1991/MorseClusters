function [Xopt]=ENERGE(TR,Xsimp)
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;
[NT,M]=size(TR);VTop=[];
[NX,M]=size(Xsimp);
for J=1:NT
    VTop=[VTop,TR(J,1),TR(J,2),TR(J,3)];
end
Vm=unique(VTop);
EA=[];
XS=Xsimp;
for IT=1:NX
    XJ=XS(IT,:);Fi=0;
    for I=1:NX
        if I~=IT
             XI=XS(I,:);
             R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
             F=exp(r0*(1-R))*(exp(r0*(1-R))-2);
             Fi=Fi+F;
        end
    end
    EA=[EA;Fi];
end
Fm=sum(EA)/2;
[Emm,Imm]=sort(EA);
[EAmin,IEA]=sort(EA);
TRsum=[];
for I=1:NX
    TRsum=[TRsum,I]; 
end
TRvn=setdiff(TRsum,Vm);
[M,NX2]=size(TRvn);Fvn=[];ITRvn=[];
for I=1:NX2
    Fvn=[Fvn,EA(TRvn(I))];
    ITRvn=[ITRvn,TRvn(I)];
end
[FvnS,IS]=sort(Fvn);
Del=ITRvn(IS(NX2)); Xsimp2=[];
for I=1:NX
    if I~=Del
        Xsimp2=[Xsimp2;Xsimp(I,:)];
    end
end
X0=[];St=[];
for I=1:NX-1
    X0=[X0,Xsimp2(I,:)];
    St=[St,1];
end
X=Xsimp2;Ntop=NX-1;
[XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-15,'MaxFunEvals',660,'MaxIter',660));
Xopt=[];k=1;
for I=1:NX-1
      XRR=[XR(k),XR(k+1),XR(k+2)];
      k=k+3;
      Xopt=[Xopt; XRR];
end
return








