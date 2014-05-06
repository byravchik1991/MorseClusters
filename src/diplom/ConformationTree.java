package diplom;

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

    public void buildTree(
            int desiredSize,
            boolean bestBranchesOnly,
            OptimizationMode optimizationMode)
            throws MatlabInvocationException, MatlabConnectionException {

        buildNextStage(root, desiredSize, bestBranchesOnly, optimizationMode);

        for (int i = root.getSize(); i <= desiredSize; i++) {
            Conformation bestConformation = ConformationHelper.findBestConformation(
                    ConformationHelper.collectAllConformationsBySize(this, i));
            if (bestConformation != null) {
                bestConformation.setBestConformation(true);
            }
        }

        if (optimizationMode == OptimizationMode.BEST_CONFORMATION_ON_LAST_STAGE) {
            Conformation bestConformation = ConformationHelper.findBestConformation(
                    ConformationHelper.collectAllConformationsBySize(this, desiredSize));
            if (bestConformation != null) {
                MatlabService.getInstance().optimizeAtomPositions(bestConformation);
            }
        }

        if (optimizationMode == OptimizationMode.ALL_CONFORMATIONS_ON_LAST_STAGE) {
            for (Conformation conformation : ConformationHelper.collectAllConformationsBySize(
                    this, desiredSize)) {
                MatlabService.getInstance().optimizeAtomPositions(conformation);
            }
        }
    }

    private void buildNextStage(Conformation parentConformation, int desiredSize, boolean bestBranchesOnly,
                                OptimizationMode optimizationMode) throws MatlabConnectionException, MatlabInvocationException {

        List<Conformation> conformations = MatlabService.getInstance().getNextConformations(parentConformation);

        for (Conformation conformation : conformations) {
            if (isConformationUnique(conformation)) {
                if (optimizationMode == OptimizationMode.ALL_CONFORMATIONS) {
                    MatlabService.getInstance().optimizeAtomPositions(conformation);
                }
                ConformationHelper.link(parentConformation, conformation);
            }
        }
        size = parentConformation.getSize() + 1;

/*        Conformation bestConformation = ConformationHelper.findBestConformation(
                ConformationHelper.collectAllConformationsBySize(this, size));*/

        // bestConformation.setBestConformation(true);
/*        if (bestConformation.getSize() == desiredSize) {
            MatlabService.getInstance().optimizeAtomPositions(bestConformation);
        }*/

        if (size < desiredSize) {
            if (bestBranchesOnly) {
                Conformation bestConformation = ConformationHelper.findBestConformation(
                        parentConformation.getSuccessors());
                if (bestConformation != null) {
                    buildNextStage(bestConformation, desiredSize, bestBranchesOnly, optimizationMode);
                }

            } else {
                for (Conformation conformation : conformations) {
                    buildNextStage(conformation, desiredSize, bestBranchesOnly, optimizationMode);
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
            if (Math.abs(existingConformation.getEnergy() - newConformation.getEnergy()) % 1
                    < Settings.MIN_ENERGY_DIFFERENCE) {
                return false;
            }
        }

        return true;
    }
}
