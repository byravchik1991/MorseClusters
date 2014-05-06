package diplom;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 06.05.14
 */
public class MySqlService {
    private static final String LOGIN = "root";
    private static final String PASSWORD = "12011986";
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DB_NAME = "morse_clusters";

    public static void insertExperiment(Experiment newExperiment) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        String sql = String.format(
                "INSERT INTO experiment(" +
                        "r0," +
                        "initial_size," +
                        "final_size," +
                        "optimization_mode," +
                        "best_branches_only," +
                        "experiment_date," +
                        "revision) " +
                        "VALUES (%s,%s,%s,%s,%s,%s,%s)",
                newExperiment.getR0(),
                newExperiment.getInitialSize(),
                newExperiment.getFinalSize(),
                newExperiment.getOptimizationMode().ordinal(),
                newExperiment.isBestBranchesOnly() ? 1 : 0,
                newExperiment.getExperimentDate().getTime(),
                newExperiment.getProgramRevision());

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int experimentId = statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            experimentId = rs.getInt(1);
            newExperiment.setId(experimentId);
        }

        ConformationTree conformationTree = newExperiment.getResultConformationTree();
        for (int i = newExperiment.getInitialSize(); i <= newExperiment.getFinalSize(); i++) {
            for (Conformation conformation : ConformationHelper.collectAllConformationsBySize(conformationTree, i)) {
                insertConformation(conformation, experimentId, connection);
            }
        }

        connection.close();
    }

    private static void insertConformation(Conformation conformation, int experimentId, Connection connection) throws SQLException {
        String sql = String.format(
                "INSERT INTO conformation(" +
                        "energy, " +
                        "size, " +
                        "parent_id, " +
                        "experiment_id) " +
                        "VALUES (%s,%s,%s,%s)",
                conformation.getEnergy(),
                conformation.getSize(),
                conformation.getPredecessors().isEmpty() ? "NULL" : conformation.getPredecessors().get(0).getId(),
                experimentId);

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int conformationId = statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            conformationId = rs.getInt(1);
            conformation.setId(conformationId);
        }

        for (int i = 0; i < conformation.getPositions().length; i++) {
            insertAtom(
                    conformation.getPositions()[i][0],
                    conformation.getPositions()[i][1],
                    conformation.getPositions()[i][2],
                    conformationId,
                    connection);
        }
    }

    private static void insertAtom(double x, double y, double z, int confId, Connection connection) throws SQLException {
        String sql = String.format("INSERT INTO atom(x,y,z,conformation_id) VALUES (%s,%s,%s,%s)", x, y, z, confId);
        connection.createStatement().executeUpdate(sql);
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, DB_NAME), LOGIN, PASSWORD);
    }

/*    private Conformation readConformation(int parentId) {

    }*/
}

