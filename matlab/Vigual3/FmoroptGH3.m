function [FR,g,H]=FmoroptGH3(X0)
global r0
global Ntop;
global X;
global St;
Nopt=length(X0);
%Nopt=Nopt/3;
FR=0;
k=1;
for I=1:Ntop
    if St(I)==1
        X(I,1)=X0(k);X(I,2)=X0(k+1);X(I,3)=X0(k+2);
        k=k+3;
    end
end
for J=1:Ntop
    XJ=[X(J,1);X(J,2);X(J,3)];
    for I=J+1:Ntop
        XI=[X(I,1);X(I,2);X(I,3)];
        R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
        F=exp(r0*(1-R))*(exp(r0*(1-R))-2);
        FR=FR+F;
    end
end

% Вычисление градиента
g=zeros(1,Nopt);
for K=1:Ntop
    for I=1:Ntop
        if K~=I
            Xk=[X(K,1);X(K,2);X(K,3)];
            Xi=[X(I,1);X(I,2);X(I,3)];
            Rki=(Xk-Xi).^2;  Rki=(Rki(1)+Rki(2)+Rki(3))^0.5;
            Aki_l=[(Xk(1)-Xi(1))/Rki;(Xk(2)-Xi(2))/Rki;(Xk(3)-Xi(3))/Rki];
            Fki=2*r0*(exp(r0*(1-Rki))-exp(2*r0*(1-Rki)));
            g(3*(K-1)+1)=g(3*(K-1)+1)+Fki*Aki_l(1);
            g(3*(K-1)+2)=g(3*(K-1)+2)+Fki*Aki_l(2);
            g(3*(K-1)+3)=g(3*(K-1)+3)+Fki*Aki_l(3);
        end
    end
end

% Вычисление гессиана
H=zeros(Nopt,Nopt);
for i1=1:Nopt
    k=ceil(i1/3);  L=i1-(k-1)*3;
    for j1=i1:Nopt
        p=ceil(j1/3);  r=j1-(p-1)*3;
        Xk=[X(k,1);X(k,2);X(k,3)];
        Xp=[X(p,1);X(p,2);X(p,3)];
        Rkp=(Xk-Xp).^2;  Rkp=(Rkp(1)+Rkp(2)+Rkp(3))^0.5;
        Fkp=2*r0*(exp(r0*(1-Rkp))-exp(2*r0*(1-Rkp)));
        Pkp=2*(r0^2)*(2*exp(2*r0*(1-Rkp))-exp(r0*(1-Rkp)));
        if k==p && L==r
            for I=1:Ntop
                if I~=k
                    Xi=[X(I,1);X(I,2);X(I,3)];
                    Rki=(Xk-Xi).^2;  Rki=(Rki(1)+Rki(2)+Rki(3))^0.5;
                    Fki=2*r0*(exp(r0*(1-Rki))-exp(2*r0*(1-Rki)));
                    Pki=2*(r0^2)*(2*exp(2*r0*(1-Rki))-exp(r0*(1-Rki)));
                    A=(Xk(L)-Xi(L))/Rki;
                    B=1/Rki-((Xk(L)-Xi(L))^2)/Rki^3;
                    H(i1,j1)=H(i1,j1)+Pki*A^2+Fki*B;
                end
            end
        elseif k~=p && L==r
            A=(Xk(L)-Xp(L))/Rkp;
            B=1/Rkp-((Xk(L)-Xp(L))^2)/Rkp^3;
            H(i1,j1)=H(i1,j1)+Pkp*A^2+Fkp*B;
        elseif k==p && L~=r
            for I=1:Ntop
                if I~=k
                    Xi=[X(I,1);X(I,2);X(I,3)];
                    Rki=(Xk-Xi).^2;  Rki=(Rki(1)+Rki(2)+Rki(3))^0.5;
                    Fki=2*r0*(exp(r0*(1-Rki))-exp(2*r0*(1-Rki)));
                    Pki=2*(r0^2)*(2*exp(2*r0*(1-Rki))-exp(r0*(1-Rki)));
                    A=((Xk(L)-Xi(L))*(Xk(r)-Xi(r)))/Rki^2;
                    B=-((Xk(L)-Xi(L))*(Xk(r)-Xi(r)))/Rki^3;
                    H(i1,j1)=H(i1,j1)+Pki*A+Fki*B;
                end
            end
        elseif k~=p && L~=r
            A=((Xk(L)-Xp(L))*(Xk(r)-Xp(r)))/Rkp^2;
            B=-((Xk(L)-Xp(L))*(Xk(r)-Xp(r)))/Rkp^3;
            H(i1,j1)=H(i1,j1)+Pkp*A+Fkp*B;
        else
            error('Ошибка вычисления Гессиана');
        end
        if i1~=j1
            H(j1,i1)=H(i1,j1);
        end 
    end
    
end
return
