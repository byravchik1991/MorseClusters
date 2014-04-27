function [ U ] = Umorse( X, Y, ro )

r=R(X, Y);
U=exp(ro*(1.0-r))*(exp(ro*(1.0-r))-2.0);

end

