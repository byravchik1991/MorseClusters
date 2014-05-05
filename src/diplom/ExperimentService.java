package diplom;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 03.05.14
 */
public class ExperimentService {

    public static Experiment executeExperiment(Experiment experiment) throws MatlabConnectionException, MatlabInvocationException {
        MatlabService.getInstance().connect();
        MatlabService.getInstance().initGlobalVariables(experiment.getR0());

        Conformation initialConformation = MatlabService.getInstance().setInitialConformation(
                experiment.getFileName(), experiment.getInitialSize());

        ConformationTree conformationTree = new ConformationTree(initialConformation);
        conformationTree.buildTree(
                experiment.getFinalSize(),
                experiment.isBestBranchesOnly(),
                experiment.getOptimizationMode());

        experiment.setExperimentDate(new Date());
        experiment.setResultConformation(conformationTree);

        return experiment;
    }
}
