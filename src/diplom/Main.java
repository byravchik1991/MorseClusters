package diplom;

import diplom.graph.MorseFrame;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 16.03.14
 */
public class Main {


    public static void main(String[] args) {
/*        try {*/
        MorseFrame morseFrame = new MorseFrame();
        morseFrame.setVisible(true);
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = null;
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/morse_clusters", "root", "12011986");
//            conn.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }




/*
            ConformationBondGraphPanel bondGraphPanel = new ConformationBondGraphPanel(conformationTree);
            morseFrame.getContentPane().add(bondGraphPanel, BorderLayout.CENTER);
            morseFrame.invalidate();
            morseFrame.repaint();

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
        }*/
    }
}
