package diplom;

import diplom.graph.ConformationBondGraphDialog;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 16.03.14
 */
public class Main {


    public static void main(String[] args) {
        try {
            MatlabService.getInstance().connect();
            MatlabService.getInstance().initGlobalVariables();
            Conformation initialConformation = MatlabService.getInstance().setInitialConformation();

            ConformationTree conformationTree = new ConformationTree(initialConformation);

            conformationTree.buildTree(Settings.FINAL_SIZE, Settings.BEST_BRANCHES_ONLY);

            for (int i = Settings.INITIAL_SIZE; i <= Settings.FINAL_SIZE; i++) {
                Conformation bestConformation = ConformationHelper.findBestConformation(
                        ConformationHelper.collectAllConformationsBySize(conformationTree, i));
                bestConformation.setBestConformation(true);
                if ((i == Settings.FINAL_SIZE)
                        && (Settings.OPTIMIZATION_TYPE == Settings.OPTIMIZATION_MODE.BEST_CONFORMATION_ON_LAST_STAGE)) {
                    MatlabService.getInstance().optimizeAtomPositions(bestConformation);
                }
            }

            if (Settings.OPTIMIZATION_TYPE == Settings.OPTIMIZATION_MODE.ALL_CONFORMATIONS_ON_LAST_STAGE) {
                for (Conformation conformation : ConformationHelper.collectAllConformationsBySize(
                        conformationTree, Settings.FINAL_SIZE)) {
                    MatlabService.getInstance().optimizeAtomPositions(conformation);
                }
            }

            ConformationBondGraphDialog dialog = new ConformationBondGraphDialog(conformationTree);
            dialog.pack();
            dialog.setVisible(true);
            System.out.println();

        } catch (MatlabConnectionException e) {
            e.printStackTrace();
        } catch (MatlabInvocationException e) {
            e.printStackTrace();
        } finally {
            try {
                MatlabService.getInstance().disconnect();

            } catch (MatlabConnectionException e) {
                e.printStackTrace();
            }
        }
    }
}
