package diplom.graph;

import diplom.ConformationTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: solovieva
 * Date: 15.07.13
 */
public class ConformationBondGraphDialog extends JDialog {
    public static final int ADDITIONAL_HEIGHT = 20;
    public static final int ADDITIONAL_WIDTH = 20;

    public static final double SCREEN_SIZE_FACTOR = 0.9;

    private final int maxWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * SCREEN_SIZE_FACTOR);
    private final int maxHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * SCREEN_SIZE_FACTOR);

    private ConformationTree conformationTree;
    private ConformationBondGraph bondGraph;

    public ConformationBondGraphDialog(ConformationTree conformationTree) {
        this.conformationTree = conformationTree;

        initializeControls();
        initializeView();

        pack();
        setLocationRelativeTo(null);
        setTitle("Граф возможных конформаций");
    }

    protected void initializeControls() {
        bondGraph = new ConformationBondGraph(conformationTree);
    }

    protected void initializeView() {
        setContentPane(new JScrollPane(bondGraph));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        dimension.height += ADDITIONAL_HEIGHT;
        dimension.width += ADDITIONAL_WIDTH;

        if (dimension.width > maxWidth) dimension.width = maxWidth;
        if (dimension.height > maxWidth) dimension.height = maxHeight;

        return dimension;
    }
}
