function MAIN_FORM_AVTO
global r0;
global Ntop;
global X;
r0=6;
Ntop=17;

fread = fopen('X16-6.txt', 'r');
Xsimp=fscanf(fread,'%g',[3,16]);
Xsimp=Xsimp';
%fprintf(fread, '%6.2f ', Xsimp);

    %[X1,Y,Z]=cylinder([2 0],5);
    %surf(X1,Y,Z);
    [TRf,Xres]=DIS_KLAST(Xsimp);
    %TRn=RANG_KLAST_M2(Xsimp,TR);
    %[Xsimp,TR]=SORT_MIN_TR(TR,TRf,Xsimp);
    %U=UmorseResult(Xsimp, 8, 6);
    %plot_FIG(TR,Xsimp); 
   
%  X1=zeros(1, Ntop);
%  X2=zeros(1, Ntop);
%  X3=zeros(1, Ntop);
 [m,n]=size(TRf);
 for j=1:m 
     %XR=Xres((j-1)*Ntop+1:(j-1)*Ntop+Ntop,:);
     %[XR,FR2,exitflag,output] = fminunc(@(x) UmorseResult(x, Ntop, r0),XR,optimset('TolX',1e-12,'MaxFunEvals',2600,'MaxIter',2600));
     TRf(j,4)
     %FR2
%   for i=1:Ntop
%       X1(i)=Xres((j-1)*Ntop+i,1);
%       X2(i)=Xres((j-1)*Ntop+i,2);
%       X3(i)=Xres((j-1)*Ntop+i,3);
%   end
% 
%     dt = delaunayTriangulation(X1',X2',X3'); 
%     [tri, Xb]= freeBoundary(dt); 
%     figure 
%     trisurf(tri,Xb(:,1),Xb(:,2),Xb(:,3));    
 end

return