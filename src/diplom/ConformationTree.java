package diplom;

import diplom.Conformation;
import diplom.ConformationHelper;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 22.03.14
 */
public class ConformationTree {
    private int size = 0;
    private Conformation root;

    public ConformationTree(Conformation root) {
        this.size = root.getSize();
        this.root = root;
    }

    public void buildTree(int desiredSize, boolean bestBranchesOnly)
            throws MatlabInvocationException, MatlabConnectionException {
        buildNextStage(root, desiredSize, bestBranchesOnly);
    }

    private void buildNextStage(Conformation parentConformation, int desiredSize, boolean bestBranchesOnly)
            throws MatlabConnectionException, MatlabInvocationException {
        List<Conformation> conformations = MatlabService.getInstance().getNextConformations(parentConformation);

        for (Conformation conformation : conformations) {
            if (isConformationUnique(conformation)) {
                ConformationHelper.link(parentConformation, conformation);
            }
        }
        size = parentConformation.getSize() + 1;

/*        Conformation bestConformation = ConformationHelper.findBestConformation(
                ConformationHelper.collectAllConformationsBySize(this, size));*/
        Conformation bestConformation = ConformationHelper.findBestConformation(parentConformation.getSuccessors());
       // bestConformation.setBestConformation(true);
/*        if (bestConformation.getSize() == desiredSize) {
            MatlabService.getInstance().optimizeAtomPositions(bestConformation);
        }*/

        if (size < desiredSize) {
            if (bestBranchesOnly) {
                buildNextStage(bestConformation, desiredSize, bestBranchesOnly);
            } else {
                for (Conformation conformation : conformations) {
                    buildNextStage(conformation, desiredSize, bestBranchesOnly);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Conformation getRoot() {
        return root;
    }

    public void setRoot(Conformation root) {
        this.root = root;
    }

    private boolean isConformationUnique(Conformation newConformation) {
        Set<Conformation> existingConformations = ConformationHelper.collectAllConformationsBySize(
                this, newConformation.getSize());

        for (Conformation existingConformation : existingConformations) {
            if (Math.abs(existingConformation.getEnergy()-newConformation.getEnergy())%1 < 0.005) {
                return false;
            }
        }

        return true;
    }
}
