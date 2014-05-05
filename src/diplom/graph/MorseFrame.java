package diplom.graph;

import diplom.Experiment;
import diplom.ExperimentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
    public static final String OPEN_EXPERIMENT_TITLE = "Открыть эксперимент";

    public static final ImageIcon PROJECT_ICON = new ImageIcon("resources\\molecule.jpg.png");
    public static final ImageIcon NEW_EXPERIMENT_ICON = new ImageIcon("resources\\add1_48.png");
    public static final ImageIcon SAVE_EXPERIMENT_ICON = new ImageIcon("resources\\save_48.png");
    public static final ImageIcon OPEN_EXPERIMENT_ICON = new ImageIcon("resources\\save_to_db.png");


    private JToolBar toolBar;
    private JProgressBar newExperimentProgressBar;

    private ConformationBondGraphPanel conformationBondGraphPanel;

    public MorseFrame() throws HeadlessException {
        initializeControls();
        initializeView();

        JFrame.setDefaultLookAndFeelDecorated(true);
        pack();
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setIconImage(PROJECT_ICON.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
    }

    private void initializeView() {
        add(toolBar, BorderLayout.WEST);
        add(newExperimentProgressBar, BorderLayout.SOUTH);
        add(new JScrollPane(conformationBondGraphPanel), BorderLayout.CENTER);
    }

    private void initializeControls() {
        toolBar = new JToolBar(TOOLBAR_TITLE, JToolBar.VERTICAL);

        JButton newExperimentButton = new JButton();
        newExperimentButton.setIcon(NEW_EXPERIMENT_ICON);
        newExperimentButton.setToolTipText(NEW_EXPERIMENT_TITLE);
        newExperimentButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewExperimentDialog newExperimentDialog = new NewExperimentDialog();
                final Experiment newExperiment = newExperimentDialog.showDialog();

                if (newExperiment != null) {
                    newExperimentProgressBar.setVisible(true);
                    conformationBondGraphPanel.reset();

                    new SwingWorker<Void, Void>() {

                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                ExperimentService.executeExperiment(newExperiment);
                            } catch (/*final MatlabConnectionException*/final Throwable e1) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(MorseFrame.this, e1.toString());
                                        e1.printStackTrace();
                                    }
                                });
                            }/* catch (final MatlabInvocationException e1) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(MorseFrame.this, e1.toString());
                                        e1.printStackTrace();
                                    }
                                });
                            }*/
                            return null;
                        }

                        @Override
                        protected void done() {
                            conformationBondGraphPanel.update(newExperiment.getResultConformationTree());
                            MorseFrame.this.revalidate();
                            MorseFrame.this.repaint();
                            newExperimentProgressBar.setVisible(false);

                            super.done();
                        }
                    }.execute();
                    // SwingUtilities.invokeLater(new Runnable() {
                }

            }
        });

        JButton saveExperimentButton = new JButton();
        saveExperimentButton.setIcon(SAVE_EXPERIMENT_ICON);
        saveExperimentButton.setToolTipText(SAVE_EXPERIMENT_TITLE);

        JButton openExperimentButton = new JButton();
        openExperimentButton.setIcon(OPEN_EXPERIMENT_ICON);
        openExperimentButton.setActionCommand(OPEN_EXPERIMENT_TITLE);

        newExperimentProgressBar = new JProgressBar();
        //newExperimentProgressBar.setString("Начат новый эксперимент...");
        newExperimentProgressBar.setIndeterminate(true);
        newExperimentProgressBar.setVisible(false);
        newExperimentProgressBar.setAlignmentX(RIGHT_ALIGNMENT);

        toolBar.add(newExperimentButton);
        toolBar.add(saveExperimentButton);
        toolBar.add(openExperimentButton);

        conformationBondGraphPanel = new ConformationBondGraphPanel();
        // toolBar.addSeparator();
        //toolBar.add(UIHelper.packInShiftedPanel(newExperimentProgressBar, UIHelper.MAX_GAP));
        // toolBar.addSeparator();
    }
}
