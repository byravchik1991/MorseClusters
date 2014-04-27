function plot_FIG(TR,Vert)
[NT,M]=size(TR);
ax=-2;ay=-2; az=-2;
bx=2; by=2; bz=2;
Faces=[];
for I=1:NT
    Faces=[Faces;[TR(I,1),TR(I,2),TR(I,3)]];
end
%kvert=convhulln(F0);
figure('Color','w');
h=trisurf(Faces,Vert(:,1),Vert(:,2),Vert(:,3));
% находим номера точек, являющихся вершинами выпуклой оболочки, и записываем их в kv
kv=union(Faces(:,1),Faces(:,2));
kv=union(kv,Faces(:,3));
% подписываем номера вершин выпуклой оболочки
x=Vert(:,1);y=Vert(:,2);z=Vert(:,3);
ht=text(x(kv), y(kv), z(kv),num2str(kv));
set(ht,'BackgroundColor','r','VerticalAlignment','Top','FontWeight','bold');
%axis([ax bx ay by az bz]);
hold on;
%p=patch('Vertices',Vert,'Faces',Faces,'FaceVertexCData',hsv(6),'FaceColor','flat');
%set(p,'Marker','.','MarkerEdgeColor','r','MarkerSize',30,'FaceAlpha',0.6)
%delete(p);
return