package diplom.graph;

import diplom.ConformationTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: solovieva
 * Date: 15.07.13
 */
public class ConformationBondGraphPanel extends JPanel {

    public ConformationBondGraphPanel() {
        setBackground(Color.WHITE);
    }

    public void update(ConformationTree conformationTree) {
        if (conformationTree != null) {
            removeAll();
            ConformationBondGraph bondGraph = new ConformationBondGraph(conformationTree);
            add(new JScrollPane(bondGraph));
            revalidate();
            repaint();
        }
    }

    public void reset() {
        removeAll();
        revalidate();
        repaint();
    }
}
