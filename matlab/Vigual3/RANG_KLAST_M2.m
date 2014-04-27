function TRn=RANG_KLAST_M2(Xs,TRs)
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;
[NX,M]=size(Xs);
[NT,M]=size(TRs);
Ak=[];
for J=1:NX
  Ak=[Ak;J];
end

FRn=[];
for K=1:NT
   FRn=[FRn;TRs(K,4)];
end

[FRS,INDs]=sort(FRn);

if abs(FRS(1))<100
    FR5=num2str(FRS,4);
else
    FR5=num2str(FRS,4);
end
FR5=str2num(FR5);
N5=size(FR5);
%INDm=[1];
Im=1;
for J=1:N5
    if FR5(Im)~=FR5(J)
        Im=J-1;
        break
    end
end
%Nm=size(INDm);

for K=1:Im
  TRIAD=[TRs(INDs(K),1),TRs(INDs(K),2),TRs(INDs(K),3)];
  [Xsimp,Ak,F]=UKLAD(Xs,TRIAD,Ak);
  FR=TRs(INDs(K),4);
  if FR<-3*(NX-1)
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
        %Xs=Xopt;
        TRs(INDs(K),4)=FR2;
  else
        TRs(INDs(K),4)=FR;
  end
end
TRn=TRs;
return
