function FR=Fmoropt3(X0)
global r0
global Ntop;
global X;
global St;
Nopt=length(X0);
FR=0;
k=1;
for I=1:Ntop
    if St(I)==1
        X(I,1)=X0(k);X(I,2)=X0(k+1);X(I,3)=X0(k+2);
        k=k+3;
    end
end
for J=1:(Ntop-1)
    XJ=[X(J,1);X(J,2);X(J,3)];
    for I=J+1:Ntop
        XI=[X(I,1);X(I,2);X(I,3)];
        R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
        F=exp(r0*(1.0-R))*(exp(r0*(1.0-R))-2.0);
        FR=FR+F;
    end
end
return