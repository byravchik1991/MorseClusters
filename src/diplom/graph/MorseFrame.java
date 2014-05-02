package diplom.graph;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 28.04.14
 */
public class MorseFrame extends JFrame {
    public static final int MIN_SIZE = 300;

    public static final String TITLE = "Кластеры Морса";
    public static final String TOOLBAR_TITLE = "Панель инструментов";
    public static final String NEW_EXPERIMENT_TITLE = "Новый эксперимент";
    public static final String SAVE_EXPERIMENT_TITLE = "Сохраниить эксперимент";

    public static final ImageIcon PROJECT_ICON = new ImageIcon("resources\\molecule.jpg.png");
    public static final ImageIcon NEW_EXPERIMENT_ICON = new ImageIcon("resources\\add1_48.png");
    public static final ImageIcon SAVE_EXPERIMENT_ICON = new ImageIcon("resources\\save_48.png");


    private JToolBar toolBar;

    public MorseFrame() throws HeadlessException {
        initializeControls();
        initializeView();

        pack();
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setIconImage(PROJECT_ICON.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
    }

    private void initializeView() {
        add(toolBar, BorderLayout.PAGE_START);
    }

    private void initializeControls() {
        toolBar = new JToolBar(TOOLBAR_TITLE);

        JButton newExperimentButton = new JButton();
        newExperimentButton.setIcon(NEW_EXPERIMENT_ICON);
        newExperimentButton.setToolTipText(NEW_EXPERIMENT_TITLE);

        JButton saveExperimentButton = new JButton();
        saveExperimentButton.setIcon(SAVE_EXPERIMENT_ICON);
        saveExperimentButton.setToolTipText(SAVE_EXPERIMENT_TITLE);

        toolBar.add(newExperimentButton);
        toolBar.add(saveExperimentButton);
    }
}
