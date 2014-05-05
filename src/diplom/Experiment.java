package diplom;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 02.05.14
 */
public class Experiment {
    private double r0;
    private int initialSize;
    private int finalSize;
    private String fileName;
    private boolean bestBranchesOnly = false;
    private Settings.OPTIMIZATION_MODE optimizationMode = Settings.OPTIMIZATION_MODE.BEST_CONFORMATION_ON_LAST_STAGE;
    private double minEnergyDifference = 0.005;

    private ConformationTree resultConformationTree;

    private Date experimentDate = new Date();
    private String programRevision;


    public double getR0() {
        return r0;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getFinalSize() {
        return finalSize;
    }

    public void setFinalSize(int finalSize) {
        this.finalSize = finalSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isBestBranchesOnly() {
        return bestBranchesOnly;
    }

    public void setBestBranchesOnly(boolean bestBranchesOnly) {
        this.bestBranchesOnly = bestBranchesOnly;
    }

    public Settings.OPTIMIZATION_MODE getOptimizationMode() {
        return optimizationMode;
    }

    public void setOptimizationMode(Settings.OPTIMIZATION_MODE optimizationMode) {
        this.optimizationMode = optimizationMode;
    }

    public double getMinEnergyDifference() {
        return minEnergyDifference;
    }

    public void setMinEnergyDifference(double minEnergyDifference) {
        this.minEnergyDifference = minEnergyDifference;
    }

    public ConformationTree getResultConformationTree() {
        return resultConformationTree;
    }

    public void setResultConformation(ConformationTree resultConformationTree) {
        this.resultConformationTree = resultConformationTree;
    }

    public Date getExperimentDate() {
        return experimentDate;
    }

    public void setExperimentDate(Date experimentDate) {
        this.experimentDate = experimentDate;
    }

    public String getProgramRevision() {
        return programRevision;
    }

    public void setProgramRevision(String programRevision) {
        this.programRevision = programRevision;
    }
}
