package diplom.graph;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxBasicShape;
import com.mxgraph.view.mxCellState;
import diplom.Conformation;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: solovieva
 * Date: 16.07.13
 */
public class ConformationShape extends mxBasicShape {
    private Conformation conformation;
    public final String name;

    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 14);
    public static final Font BODY_FONT_BOLD = new Font("Arial", Font.BOLD, 11);

    public static final Color FONT_COLOR = Color.BLACK;

    public ConformationShape(Conformation conformation) {
        this.conformation = conformation;
        this.name = String.valueOf(conformation.hashCode());
        mxGraphics2DCanvas.putShape(name, this);
    }

    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {
        Rectangle rect = state.getRectangle();
        int x = rect.x;
        int y = rect.y;
        int width = rect.width;
        int height = rect.height;

        // Paints the background
        if (configureGraphics(canvas, state, true)) {
            // fill header
            canvas.getGraphics().setColor(Color.yellow.brighter());
            canvas.getGraphics().fillRect(x + 1, y + 1, width - 1, 19);

            // fill body
            canvas.getGraphics().setColor(getBodyColor());
            canvas.getGraphics().fillRect(x + 1, y + 21, width - 1, height - 20);
        }

        // Paints the foreground
        if (configureGraphics(canvas, state, false)) {

            //paint shape
            canvas.getGraphics().drawRect(x, y, width, height);
            canvas.getGraphics().drawLine(x, y + 20, x + width, y + 20);

            //paint strings
            canvas.getGraphics().setColor(FONT_COLOR);

            canvas.getGraphics().setFont(HEADER_FONT);
            String sizeString = "N = " + conformation.getSize();
            printCenteredString(canvas.getGraphics(), sizeString, width, x, y + 15);

            canvas.getGraphics().setFont(BODY_FONT_BOLD);
            String energyString = "E = " + conformation.getEnergy();
            printCenteredString(canvas.getGraphics(), energyString, width, x, y + 40);
        }
    }

    private Color getBodyColor() {
        return conformation.isBestConformation() ? Color.GREEN : Color.lightGray.brighter();
    }

    private void printCenteredString(Graphics2D g2d, String s, int width, int xPos, int yPos) {
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + xPos, yPos);
    }
}
