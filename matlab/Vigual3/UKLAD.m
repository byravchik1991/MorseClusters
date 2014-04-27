function [Xdat2,Ak2,FR]=UKLAD(Xdat,TRIAD,Ak)
global r0;
[NX,M]=size(Xdat);
R=0.5;
P1=(Xdat(TRIAD(1),:))';
P2=(Xdat(TRIAD(2),:))';
P3=(Xdat(TRIAD(3),:))';
r00=P1;
x1=P1(1);y1=P1(2);z1=P1(3);
x2=P2(1);y2=P2(2);z2=P2(3);
x3=P3(1);y3=P3(2);z3=P3(3);
nx=y1*z2 - y2*z1 - y1*z3 + y3*z1 + y2*z3 - y3*z2;
ny=-(x1*z2 - x2*z1 - x1*z3 + x3*z1 + x2*z3 - x3*z2);
nz=x1*y2 - x2*y1 - x1*y3 + x3*y1 + x2*y3 - x3*y2;
n=[nx;ny;nz]/(nx^2+ny^2+nz^2)^0.5;
e1=[(x2-x1);(y2-y1);(z2-z1)]/((x2-x1)^2+(y2-y1)^2+(z2-z1)^2)^0.5;
e2x=e1(2)*n(3)-e1(3)*n(2);
e2y=-(e1(1)*n(3)-e1(3)*n(1));
e2z=e1(1)*n(2)-e1(2)*n(1);
e2=[e2x;e2y;e2z];
S=[e1(1), e2(1),n(1);e1(2), e2(2),n(2);e1(3), e2(3),n(3)];
S1=inv(S);
X1=S1*(P1-r00);
X2=S1*(P2-r00);
X3=S1*(P3-r00);
a=X2(1);b=X2(2);
c=X3(1);d=X3(2);
D=a*d-b*c;
if abs(D) > 1.0e-15
    x=-(b*(c^2+d^2)-d*(a^2+b^2))/(2*D);
    y=(a*(c^2+d^2)-c*(a^2+b^2))/(2*D);
end
r=(((x-c)^2+(y-d)^2)^0.5)/2;
z1=2*((R^2-r^2)^0.5);
z2=-2*((R^2-r^2)^0.5);
Xn=[x;y;z1];
X4=S*Xn+r00;
Xn=[x;y;z2];
X5=S*Xn+r00;
FR1=0;
%добавление нового атома в кластер
Y1=Xdat;Y1=[Y1;X4'];Ak1=[Ak;NX+1];
%вычисление потенциала
for J=1:NX+1
    XJ=[Y1(J,1);Y1(J,2);Y1(J,3)];
    for I=J+1:NX+1
        XI=[Y1(I,1);Y1(I,2);Y1(I,3)];
        R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
        F=exp(r0*(1-R))*(exp(r0*(1-R))-2);
        FR1=FR1+F;
    end
end
%Ris_top2(Y,3,Ak1);
FR2=0;
%добавление нового атома в кластер
Y2=Xdat;Y2=[Y2;X5'];
%вычисление потенциала
for J=1:NX+1
    XJ=[Y2(J,1);Y2(J,2);Y2(J,3)];
    for I=J+1:NX+1
        XI=[Y2(I,1);Y2(I,2);Y2(I,3)];
        R=(XJ-XI).^2;  R=(R(1)+R(2)+R(3))^0.5;
        F=exp(r0*(1-R))*(exp(r0*(1-R))-2);
        FR2=FR2+F;
    end
end
%Ris_top2(Y,3,Ak1);
if FR1<FR2
    Xdat2=Y1;Ak2=Ak1;FR=FR1;
else
    Xdat2=Y2;Ak2=Ak1;FR=FR2;
end
return