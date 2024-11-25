package prog.hazi.ui;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    private double paddingPercentage;
    private int borderThickness = 5; // Thickness of the border
    private Color shadowColor = new Color(0, 0, 0, 64); // 25% black for shadow
    private int shadowOffsetX = -5; // Offset for the shadow
    private int shadowOffsetY = 5; // Offset for the shadow
    private int shadowSpread = 10; // Offset for the shadow

    private Color fillColor;

    /**
     * Constructs a TablePanel with the specified fill color and padding.
     *
     * @param fillColor the color used to fill the panel
     * @param padding the padding percentage to be applied to the panel
     */
    public TablePanel(Color fillColor, double padding) {
        this.fillColor = fillColor;
        this.paddingPercentage = padding;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the preferred size
        Dimension preferredSize = getPreferredSize();
        int width = preferredSize.width;
        int height = preferredSize.height;

        // Calculate padding based on percentage
        int padding = (int) (Math.min(width, height) * paddingPercentage);
        int arcSize = 30;

        // Calculate the position to center the table
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;

        // Draw the shadow
        g2.setColor(shadowColor);
        g2.fillRoundRect(x - padding + shadowOffsetX - shadowSpread/2, y - padding + shadowOffsetY - shadowSpread/2, width + 2 * padding + shadowSpread, height + 2 * padding + shadowSpread, arcSize, arcSize);

        // Draw the table
        g2.setColor(fillColor);
        g2.fillRoundRect(x - padding, y - padding, width + 2 * padding, height + 2 * padding, arcSize, arcSize);

        // Draw the outer border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(x - padding, y - padding, width + 2 * padding, height + 2 * padding, arcSize, arcSize);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Component comp : getComponents()) {
            Dimension compSize = comp.getPreferredSize();
            maxX = Math.max(maxX, comp.getX() + compSize.width/2);
            minX = Math.min(minX, comp.getX() - compSize.width/2);
            minY = Math.min(minY, comp.getY() - compSize.height/2);
            maxY = Math.max(maxY, comp.getY() + compSize.height/2);
        }

        return new Dimension((int)((maxX-minX) * (1+paddingPercentage)), (int)((maxY-minY) * (1+paddingPercentage)));
    }
}