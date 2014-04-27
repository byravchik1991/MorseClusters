function [TR,Xn]=OBR_KLAST(Xs)
global r0;
global A1;
global A2;
global Ntop;
global St;
global X;
[NX,M]=size(Xs);
Fi=[];
for IT=1:NX
    Xn=[];
    for I=1:NX
        if I~=IT
            Xn=[Xn;Xs(I,:)];
        end
    end
    [FR,FS,Nt]=Fmoropt(Xn);
    %[TR,FS]=DIS_KLAST(Xn);
    Fi=[Fi,FR];
    %plot_FIG(TR,Xn);
end
[Fsor,If]=sort(Fi);
IT=If(1);
Xn=[];
for I=1:NX
     if I~=IT
         Xn=[Xn;Xs(I,:)];
     end
end
X0=[];St=[];
for I=1:NX-1
    X0=[X0,Xn(I,:)];
    St=[St,1];
end
X=Xn;Ntop=NX-1;
[XR,FR2,exitflag,output] = fminunc(@FmoroptGH3,X0,optimset('GradObj','on','Hessian','on','TolX',1e-12,'MaxFunEvals',460,'MaxIter',460));
%plot_FIG(TRn,Xs);
Xopt=[];k=1;
     for I=1:NX-1
         XRR=[XR(k),XR(k+1),XR(k+2)];
         k=k+3;
         Xopt=[Xopt; XRR];
     end
Xn=Xopt;
[TR,FS]=DIS_KLAST(Xn);
plot_FIG(TR,Xn);
return

