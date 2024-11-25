package prog.hazi.ui;

import javax.swing.*;
import java.awt.*;

public class GameLabelPanel extends JPanel {

    private JLabel statusLabel;

    /**
     * Constructs a new GameLabelPanel with the specified status label.
     *
     * @param sLabel the JLabel to be used as the status label
     */
    public GameLabelPanel(JLabel sLabel) {
        statusLabel = sLabel;
        initializeUI();
    }

    public void initializeUI() {
        setLayout(new FlowLayout());
        add(statusLabel);
        setOpaque(false);
    }
}
