function [FR,FR1,Fs,Nt]=Fmoros(X)
global r0
global Ntop;
[Nt,M]=size(X);
R1=1.04;
FR=0;FR1=0;
for J=1:Nt-1
    XJ=[X(J,1);X(J,2);X(J,3)];
    for I=J+1:Nt
        XI=[X(I,1);X(I,2);X(I,3)];
        R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
        F=exp(r0*(1-R))*(exp(r0*(1-R))-2);
        if R<=R1
            FR1=FR1+F;
        end
        FR=FR+F;
    end
end
Fs=-3*((Nt-1)-2)-3;
return