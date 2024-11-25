package prog.hazi.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private Color fillColor;
    private Color outherFillColor;
    private Color borderColor;
    private int borderThickness;

    private Color shadowColor = new Color(0, 0, 0, 64); // Semi-transparent black for shadow
    private int shadowOffsetX = 5; // X offset for the shadow
    private int shadowOffsetY = -5; // Y offset for the shadow
    private int shadowThickness = 5; // Thickness for the shadow


    /**
     * Constructs a new RoundedButton with the specified text, fill color, outer color, border color, and border thickness.
     *
     * @param text the text to be displayed on the button
     * @param fillColor the color to fill the button
     * @param outherColor the color behind the button (To cover any overflow)
     * @param borderColor the color of the button's border
     * @param borderThickness the thickness of the button's border
     */
    public RoundedButton(String text, Color fillColor, Color outherColor, Color borderColor, int borderThickness) {
        super(text);
        outherFillColor = outherColor;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the button with the fill color
        g2.setColor(fillColor);
        g2.fillRoundRect(borderThickness / 2, borderThickness / 2, getWidth() - borderThickness, getHeight() - borderThickness, 20, 20);

        // Draw the inner shadow on the right and bottom edges
        g2.setColor(shadowColor);
        g2.setStroke(new BasicStroke((shadowThickness + 2)));
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2 + shadowOffsetX, getWidth() - borderThickness + shadowOffsetY + 1, getHeight() - borderThickness - shadowThickness + 1, 20, 20);

        // Draw the border
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2, getWidth() - borderThickness, getHeight() - borderThickness, 20, 20);

        // Draw the outer fill to cover any overflow
        g2.setColor(outherFillColor);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(borderThickness / 2 - 5, borderThickness / 2 - 4, getWidth()+4, getHeight()+4, 25, 25);
        
        // Draw the text
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        String text = getText();
        int x = (r.width - fm.stringWidth(text)) / 2;
        int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
        super.paintComponent(g);
    }
}