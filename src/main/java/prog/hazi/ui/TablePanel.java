package prog.hazi.ui;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    private double paddingPercentage = 0.03; // 10% padding
    private int borderThickness = 5; // Thickness of the border
    private Color shadowColor = new Color(0, 0, 0, 64); // 25% black for shadow
    private int shadowOffsetX = -5; // Offset for the shadow
    private int shadowOffsetY = 5; // Offset for the shadow
    private int shadowSpread = 10; // Offset for the shadow

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

        // Draw the table (white rounded rectangle)
        g2.setColor(new Color(207, 175, 99));
        g2.fillRoundRect(x - padding, y - padding, width + 2 * padding, height + 2 * padding, arcSize, arcSize);

        // Draw the outer border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(x - padding, y - padding, width + 2 * padding, height + 2 * padding, arcSize, arcSize);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate the preferred size based on the size of the components inside
        int maxWidth = 0;
        int maxHeight = 0;

        for (Component comp : getComponents()) {
            Dimension compSize = comp.getPreferredSize();
            maxWidth = Math.max(maxWidth, compSize.width);
            maxHeight = Math.max(maxHeight, compSize.height);
        }

        int padding = (int) (Math.min(maxWidth, maxHeight) * paddingPercentage);
        return new Dimension(maxWidth + 2 * padding, maxHeight + 2 * padding);
    }
}