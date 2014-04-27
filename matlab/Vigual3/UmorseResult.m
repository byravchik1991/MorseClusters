function [ U ] = UmorseResult(X, Npoints, ro)
U=0.0;

for i=1:(Npoints-1)
    for j=(i+1):Npoints
        X1=[X(i, 1), X(i, 2), X(i, 3)];
        X2=[X(j, 1), X(j, 2), X(j, 3)];
        U=U+Umorse(X1, X2, ro);
    end
end

end

