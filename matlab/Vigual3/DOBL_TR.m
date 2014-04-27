function FL=DOBL_TR(TRs,SNN,NT1)
[NT,M]=size(TRs);
FL=1;
for I=NT1:NT
    if TRs(I,1)==SNN(1) && TRs(I,2)==SNN(2) && TRs(I,3)==SNN(3)
        FL=0;
        break
    end
end
return