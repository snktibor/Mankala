package prog.hazi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameLabelPanel extends JPanel {

    private JLabel statusLabel;

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
