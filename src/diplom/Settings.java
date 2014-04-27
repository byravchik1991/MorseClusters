package diplom;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 27.04.14
 */
public class Settings {
    public static final double R0 = 6.0;
    public static final int INITIAL_SIZE = 16;
    public static final int FINAL_SIZE = 18;

    public static final String FILE_NAME = "C:\\Users\\Иришка\\Documents\\MATLAB\\Vigual3\\X16-6.txt";

    public static final boolean BEST_BRANCHES_ONLY = false;

    public static final OPTIMIZATION_MODE OPTIMIZATION_TYPE = OPTIMIZATION_MODE.BEST_CONFORMATION_ON_LAST_STAGE;

    public static final double MIN_ENERGY_DIFFERENCE = 0.005;

    public enum OPTIMIZATION_MODE {
        BEST_CONFORMATION_ON_LAST_STAGE,
        ALL_CONFORMATIONS_ON_LAST_STAGE,
        ALL_CONFORMATIONS
    }
}
