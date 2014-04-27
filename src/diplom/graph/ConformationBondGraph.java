package diplom.graph;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import diplom.Conformation;
import diplom.ConformationHelper;
import diplom.ConformationTree;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: solovieva
 * Date: 15.07.13
 */
public class ConformationBondGraph extends JPanel {
    public static final int CONFORMATION_HEIGHT = 55;
    public static final int CONFORMATION_WIDTH = 150;
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    private ConformationTree conformationTree;

    private Map<Conformation, Object> vertexMap;

    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private Map<Object, String> tooltipText;

    public ConformationBondGraph(ConformationTree conformationTree) {
        this.conformationTree = conformationTree;

        initializeControls();
        initializeView();
    }

    private void createGraph() {
        graph = new mxGraph() {
            public String convertValueToString(Object cell) {
                return "";
            }

            @Override
            public String getToolTipForCell(Object o) {
                if (tooltipText.containsKey(o)) {
                    return tooltipText.get(o);
                }

                return "";
            }
        };

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setToolTips(true);

        graph.getModel().beginUpdate();
        try {
            createVertexes();
            createEdges();
            setGraphLayout();
            setGraphSettings();
            generateToolTips();
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private void generateToolTips() {
        tooltipText = new HashMap<Object, String>();

        for (Object value :  vertexMap.values()) {
            tooltipText.put(value, ((mxCell) value).getValue().toString());
        }
    }

    private void setGraphSettings() {
        graph.setEdgeLabelsMovable(false);
        graph.setAllowDanglingEdges(false);
        graph.setAllowNegativeCoordinates(false);
        graph.setCellsCloneable(false);
        graph.setCellsDeletable(false);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
        graph.setCellsMovable(false);
        graphComponent.setConnectable(false);
        graph.setLabelsClipped(true);
        graph.setHtmlLabels(true);
        graph.setAutoSizeCells(true);
    }

    private void createVertexes() {
        vertexMap = new HashMap<Conformation, Object>();

            for (Conformation conformation : ConformationHelper.collectAllConformations(conformationTree)) {
                ConformationShape shape = new ConformationShape(conformation);
                mxGraphics2DCanvas.putShape(shape.name, shape);

                Object vertex = graph.insertVertex(
                        graph.getDefaultParent(),
                        null,
                        conformation,
                        0,
                        0,
                        CONFORMATION_WIDTH,
                        CONFORMATION_HEIGHT,
                        "shape=" + shape.name);

                vertexMap.put(conformation, vertex);
            }
    }

    private void createEdges() {
        for (Conformation conformation : vertexMap.keySet()) {
            for (Conformation successor : conformation.getSuccessors()) {
                graph.insertEdge(
                        graph.getDefaultParent(),
                        null,
                        "",
                        vertexMap.get(conformation),
                        vertexMap.get(successor));
            }
        }
    }

    private void setGraphLayout() {
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
        layout.execute(graph.getDefaultParent());
    }

    protected void initializeControls() {
        createGraph();
    }

    protected void initializeView() {
        setBackground(BACKGROUND_COLOR);

        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(BACKGROUND_COLOR);
        graphComponent.setBorder(BorderFactory.createEmptyBorder());

        JPanel graphPanel = UIHelper.packInShiftedPanelWithMinGap(graphComponent);
        graphPanel.setBackground(BACKGROUND_COLOR);

        add(graphPanel, BorderLayout.CENTER);
    }
}
