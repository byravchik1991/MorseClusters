function [X0,VRn,DRs]=GEN_RAND_POIK(Xsimp,VR,DR,FRbas,FRM,FLAG)
[M,NX]=size(Xsimp);
Sig=0.015;
Del=0.0000001;
Fsup=FRbas*(1+Del);Finf=FRbas*(1-Del);
%DR=zeros(NX,3);
if FLAG==1
    DRs=[];
    for I=1:NX
        GS=VR(I,1)+VR(I,2)+VR(I,3);
        G=[VR(I,1)/GS,(VR(I,1)+VR(I,2))/GS];
        r=unifrnd(0,1);
        if r>=0 && r<=G(1)
             X0(I)=Xsimp(I)-unifrnd(0,Sig);
             DRs(I)=1;
             continue
        end
        if r>G(1) && r<=G(2)
             X0(I)=Xsimp(I);
             DRs(I)=2;
             continue
        end
        if r>G(2) && r<=1
             X0(I)=Xsimp(I)+unifrnd(0,Sig);
             DRs(I)=3;
             continue
        end
    end
VRn=VR;   
else
    if FRM <= Fsup
        for I=1:NX
            if DR(I)==1
                if VR(I,2)>=1
                    VR(I,1)=VR(I,1)+1;
                    VR(I,2)=VR(I,2)-1;
                end
                if VR(I,3)>=1
                    VR(I,1)=VR(I,1)+1;
                    VR(I,3)=VR(I,3)-1;
                end
            continue    
            end
            
            if DR(I)==2
                if VR(I,1)>=1
                    VR(I,2)=VR(I,2)+1;
                    VR(I,1)=VR(I,1)-1;
                end
                if VR(I,3)>=1
                    VR(I,2)=VR(I,2)+1;
                    VR(I,3)=VR(I,3)-1;
                end
            continue    
            end
            
            if DR(I)==3
                if VR(I,1)>=1
                    VR(I,3)=VR(I,3)+1;
                    VR(I,1)=VR(I,1)-1;
                end
                if VR(I,2)>=1
                    VR(I,3)=VR(I,3)+1;
                    VR(I,2)=VR(I,2)-1;
                end
            continue    
            end
            
        end
    end
    if FRM >= Finf
        for I=1:NX
            if DR(I)==1
                if VR(I,1)>=4
                    VR(I,1)=VR(I,1)-1;
                    VR(I,2)=VR(I,2)+0.5;
                    VR(I,3)=VR(I,3)+0.5;
                end
                if VR(I,1)==3
                    VR(I,1)=VR(I,1)-0.5;
                    VR(I,3)=VR(I,3)+0.5;
                end
            continue    
            end
            
            if DR(I)==2
                if VR(I,2)>=4
                    VR(I,2)=VR(I,2)-1;
                    VR(I,1)=VR(I,1)+0.5;
                    VR(I,3)=VR(I,3)+0.5;
                end
                if VR(I,2) ==3
                    VR(I,2)=VR(I,2)-0.5;
                    VR(I,3)=VR(I,3)+0.5;
                end
            continue    
            end
            
            if DR(I)==3
                if VR(I,3)>=4
                    VR(I,3)=VR(I,3)-1;
                    VR(I,1)=VR(I,1)+0.5;
                    VR(I,2)=VR(I,2)+0.5;
                end
                if VR(I,3) ==3
                    VR(I,3)=VR(I,3)-0.5;
                    VR(I,3)=VR(I,3)+0.5;
                end
            continue    
            end
            
        end
    end
DRs=DR;X0=Xsimp;VRn=VR;
end

return
