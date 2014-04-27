package diplom.graph;

import javax.swing.*;
import java.awt.*;


/**
 * User: Babanin
 * Date: 18.06.2010
 * Time: 11:36:58
 */
public final class UIHelper {
    public static final int MIN_GAP = 5;
    public static final int MAX_GAP = 17;

    public static JPanel packInPanel(JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel packInShiftedPanel(JComponent component, int top, int bottom, int right, int left) {
        JPanel panel = packInPanel(component);
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return panel;
    }

    public static JPanel packInShiftedPanelWithMinGap(JComponent component) {
        return packInShiftedPanel(component, MIN_GAP);
    }

    public static JPanel packInShiftedPanel(JComponent component, int gap) {
        return packInShiftedPanel(component, gap, gap, gap, gap);
    }
}