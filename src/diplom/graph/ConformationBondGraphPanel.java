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
    private ConformationTree conformationTree;
    private ConformationBondGraph bondGraph;

    public ConformationBondGraphPanel(ConformationTree conformationTree) {
        this.conformationTree = conformationTree;

        initializeControls();
        initializeView();
    }

    protected void initializeControls() {
        bondGraph = new ConformationBondGraph(conformationTree);
    }

    protected void initializeView() {
        add(new JScrollPane(bondGraph));
        setBackground(Color.WHITE);
    }
}
