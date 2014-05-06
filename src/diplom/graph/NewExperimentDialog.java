package diplom.graph;

import diplom.Experiment;
import diplom.OptimizationMode;

import javax.swing.*;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Иришка
 * Date: 02.05.14
 */
public class NewExperimentDialog extends JDialog {
    private JFormattedTextField r0TextField;

    private JFormattedTextField initialSizeTextField;
    private JFormattedTextField desiredSizeTextField;

    private JFileChooser initialConfFileChooser;
    private JButton selectInitialConfButton;
    private JTextField fileNameTextField;

    private JCheckBox bestBranchesOnlyCheckBox;

    private ButtonGroup optimizationModeGroup;
    private JRadioButton noOptimizationModeRadioButton;
    private JRadioButton bestConfOnLasStageRadioButton;
    private JRadioButton allConfOnLastStageRadioButton;
    private JRadioButton allConfRadioButton;

    private JSpinner minEnergyDifferenceSpinner = new JSpinner();

    private JButton okButton;
    private JButton cancelButton;

    private Experiment newExperiment;

    public NewExperimentDialog() {
        setIconImage(MorseFrame.NEW_EXPERIMENT_ICON.getImage());
        setTitle("Новый эксперимент");
        setModal(true);
        initializeControls();
        initializeView();

        pack();
        setLocationRelativeTo(null);
    }

    protected void initializeControls() {
        r0TextField = new JFormattedTextField();
        r0TextField.setValue(new Double(6.0));
        r0TextField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {

            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.0);
                formatter.setMaximum(20.00);
                return formatter;
            }
        });

        NumberFormat numberFormat = NumberFormat.getInstance();
        NumberFormatter integerFormatter = new NumberFormatter(numberFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setMinimum(0);
        integerFormatter.setMaximum(Integer.MAX_VALUE);
        initialSizeTextField = new JFormattedTextField(integerFormatter);
        desiredSizeTextField = new JFormattedTextField(integerFormatter);

        fileNameTextField = new JTextField();
        initialConfFileChooser = new JFileChooser();
        selectInitialConfButton = new JButton("Выбрать");
        selectInitialConfButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = initialConfFileChooser.showOpenDialog(NewExperimentDialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = initialConfFileChooser.getSelectedFile();
                    fileNameTextField.setText(file.getAbsolutePath());
                }
            }
        });

        bestBranchesOnlyCheckBox = new JCheckBox();
        bestBranchesOnlyCheckBox.setText("Формировать только главные ветви");

        okButton = new JButton("Начать эксперимент");
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double r0 = (Double) r0TextField.getValue();
                if (r0 == null || r0 <= 0) {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, "Задайте параметр r0!");
                    return;
                }

                Integer initialSize = (Integer) initialSizeTextField.getValue();
                if (initialSize == null || initialSize <= 0) {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, "Задайте начальный размер конформации!");
                    return;
                }

                Integer desiredSize = (Integer) desiredSizeTextField.getValue();
                if (desiredSize == null || desiredSize <= 0) {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, "Задайте конечный размер конформации!");
                    return;
                }

                String fileName = fileNameTextField.getText();
                if (fileName == null || fileName.isEmpty()) {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, "Выберите файл начальной конформации!");
                    return;
                }

                OptimizationMode optimizationMode = null;
                if (noOptimizationModeRadioButton.isSelected()) {
                    optimizationMode = OptimizationMode.NO_OPTIMIZATION;
                } else if (bestConfOnLasStageRadioButton.isSelected()) {
                    optimizationMode = OptimizationMode.BEST_CONFORMATION_ON_LAST_STAGE;
                } else if (allConfOnLastStageRadioButton.isSelected()) {
                    optimizationMode = OptimizationMode.ALL_CONFORMATIONS_ON_LAST_STAGE;
                } else if (allConfRadioButton.isSelected()) {
                    optimizationMode = OptimizationMode.ALL_CONFORMATIONS;
                }
                if (optimizationMode == null) {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, "Выберите параметры оптимизации!");
                    return;
                }

                newExperiment = new Experiment();
                newExperiment.setR0(r0);
                newExperiment.setInitialSize(initialSize);
                newExperiment.setFinalSize(desiredSize);
                newExperiment.setFileName(fileName);
                newExperiment.setOptimizationMode(optimizationMode);

                NewExperimentDialog.this.setVisible(false);
                NewExperimentDialog.this.dispose();
            }
        });

        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewExperimentDialog.this.setVisible(false);
                NewExperimentDialog.this.dispose();
            }
        });

        noOptimizationModeRadioButton = new JRadioButton("Без оптимизации");
        bestConfOnLasStageRadioButton = new JRadioButton("Лучшая конформация на верхнем ярусе");
        allConfOnLastStageRadioButton = new JRadioButton("Все конформации на верхнем ярусе");
        allConfRadioButton = new JRadioButton("Все конформации");
        optimizationModeGroup = new ButtonGroup();
        optimizationModeGroup.add(noOptimizationModeRadioButton);
        optimizationModeGroup.add(bestConfOnLasStageRadioButton);
        optimizationModeGroup.add(allConfOnLastStageRadioButton);
        optimizationModeGroup.add(allConfRadioButton);

    }

    protected void initializeView() {
        JPanel mainParamsPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3, 2);
        gridLayout.setHgap(UIHelper.MIN_GAP);
        gridLayout.setVgap(UIHelper.MIN_GAP);
        mainParamsPanel.setLayout(gridLayout);
        mainParamsPanel.add(new JLabel("Ro"));
        mainParamsPanel.add(r0TextField);
        mainParamsPanel.add(new JLabel("Начальный размер кластера"));
        mainParamsPanel.add(initialSizeTextField);
        mainParamsPanel.add(new JLabel("Конечный размер кластера"));
        mainParamsPanel.add(desiredSizeTextField);
        mainParamsPanel.setBorder(BorderFactory.createEmptyBorder());

        JPanel fileChooserPanel = new JPanel();
        fileChooserPanel.add(fileNameTextField);
        fileChooserPanel.add(selectInitialConfButton);
        fileChooserPanel.setLayout(new BoxLayout(fileChooserPanel, BoxLayout.X_AXIS));
        fileChooserPanel.setBorder(BorderFactory.createTitledBorder("Файл начальной конформации"));

        // mainParamsPanel.add(new JLabel("Формировать только главные ветви"));
        //  mainParamsPanel.add(bestBranchesOnlyCheckBox);

        JPanel optimizationModePanel = new JPanel(new GridLayout(4, 1));
        optimizationModePanel.setBorder(BorderFactory.createTitledBorder("Параметры оптимизации"));
        optimizationModePanel.add(noOptimizationModeRadioButton);
        optimizationModePanel.add(bestConfOnLasStageRadioButton);
        optimizationModePanel.add(allConfOnLastStageRadioButton);
        optimizationModePanel.add(allConfRadioButton);

        JPanel finalPanel = new JPanel();
        finalPanel.add(UIHelper.packInShiftedPanelWithMinGap(mainParamsPanel));
        finalPanel.add(UIHelper.packInShiftedPanelWithMinGap(fileChooserPanel));
        finalPanel.add(UIHelper.packInShiftedPanelWithMinGap(optimizationModePanel));
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
        add(finalPanel, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getRootPane().setDefaultButton(okButton);
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    public Experiment showDialog() {
        setVisible(true);
        return newExperiment;
    }
}
