package prog.hazi.ui;

import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import prog.hazi.model.Settings;
import prog.hazi.model.Team;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

public class MenuPanel extends JPanel {
    private Settings st;
    private JTextField[] teamLabels;
    private JDialog parent;

    public MenuPanel(Settings setts, JDialog prnt) {
        st = setts;
        parent = prnt;
        initializeUI();
    }


    public void initializeUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(207, 175, 99));

        // Create and add the table panel
        TablePanel tablePanel = new TablePanel(new Color(105, 94, 66), 0.12);
        tablePanel.setOpaque(false);
        add(tablePanel, BorderLayout.CENTER);

        tablePanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center the button
        GridBagConstraints gbc = new GridBagConstraints();


        teamLabels = new JTextField[2];
        addTeamRows(tablePanel, gbc, 0);
        addBoardRow(tablePanel, gbc, 3);
        addPlayButton(tablePanel, gbc, 5);
    }


    private void addTeamRows(JPanel tablePanel, GridBagConstraints gbc, int gridY) {
        gbc.gridx = 0;
        gbc.gridy = gridY + 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Teams");
        setLabelStyles(title);
        gbc.insets = new Insets(0, 30, 0, 15);
        tablePanel.add(title, gbc);

        for (Team t : Team.values()) {
            JButton button = createButton(t.getColor());
            teamLabels[t.id] = createTextField(t.getName(), t.getColor());

            button.add(teamLabels[t.id], BorderLayout.CENTER);
            gbc.gridy = gridY + 1;
            gbc.gridx = t.id + 1;
            gbc.insets = new Insets(0, 0, 5, 30);
            tablePanel.add(button, gbc);

            JLabel teamType = createTeamTypeLabel(t);
            gbc.gridy = gridY;
            gbc.insets = new Insets(0, 0, 0, 30);
            tablePanel.add(teamType, gbc);
        }
    }
    private void setTextFieldStyles(JTextField label, Color bg) {
        label.setFont(new Font("Alexandria", Font.BOLD, 15));
        label.setBackground(bg);
        label.setOpaque(false);
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
    }

    private void setLabelStyles(JLabel label) {
        label.setFont(new Font("Alexandria", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
    }

    private JButton createButton(Color filColor) {
        JButton button = new RoundedButton("", filColor, new Color(105, 94, 66), Color.BLACK, 5);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50)); // Set preferred size for the button
        button.setLayout(new BorderLayout());
        return button;
    }

    private JTextField createTextField(String text, Color bg) {
        JTextField label = new JTextField(text);
        setTextFieldStyles(label, bg);
        return label;
    }

    private JLabel createTeamTypeLabel(Team t) {
        JLabel teamType = new JLabel(t.id == 0 ? "North" : "South");
        setLabelStyles(teamType);
        return teamType;
    }

    private void addPlayButton(JPanel tablePanel, GridBagConstraints gbc, int gridY) {
        JButton playButton = new RoundedButton("Play", new Color(87, 183, 81), new Color(105, 94, 66), Color.BLACK, 5);
        playButton.setPreferredSize(new Dimension((int)(parent.getWidth()/3.5), parent.getHeight()/12));
        playButton.setFont(new Font("Alexandria", Font.BOLD, 20));
        playButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        playButton.addActionListener(new ButtonListener());
        //playButton.addMouseListener(new ButtonMouseListener());
        tablePanel.add(playButton, gbc);
    }

    private void addBoardRow(JPanel tablePanel, GridBagConstraints gbc,  int gridY) {
        gbc.gridx = 0;
        gbc.gridy = gridY + 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Board");
        setLabelStyles(title);
        gbc.insets = new Insets(0, 30, 0, 15);
        tablePanel.add(title, gbc);

        Color clr = new Color(207, 175, 99);

        JButton button = createButton(clr);
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        JFormattedTextField balls = new JFormattedTextField(amountFormat);
        setTextFieldStyles(balls, clr);

        balls.setValue(st.getBoardSize());

        button.add(balls, BorderLayout.CENTER);
        gbc.gridy = gridY + 1;
        gbc.gridx = 0 + 1;
        gbc.insets = new Insets(0, 0, 5, 30);
        tablePanel.add(button, gbc);

        JLabel teamType = new JLabel("Pits");
        setLabelStyles(teamType);
        gbc.gridy = gridY;
        gbc.insets = new Insets(0, 0, 0, 30);
        tablePanel.add(teamType, gbc);
    }

    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < teamLabels.length; i++) {
                Team.getTeam(i).setName(teamLabels[i].getText());
            }
            parent.dispose();
        }
    }

    // //mouse listener
    // public class ButtonMouseListener implements MouseListener {

    //     @Override
    //     public void mouseClicked(java.awt.event.MouseEvent e) {
    //         //Semmi
    //     }
    //     @Override
    //     public void mousePressed(java.awt.event.MouseEvent e) {
    //         //Semmi
    //     }
    //     @Override
    //     public void mouseReleased(java.awt.event.MouseEvent e) {
    //         //Semmi
    //     }
    //     @Override
    //     public void mouseEntered(java.awt.event.MouseEvent e) {
    //         //play button szine legyen piros
    //         setBackground(new Color(255, 0, 0));
    //     }
    //     @Override
    //     public void mouseExited(java.awt.event.MouseEvent e) {
    //         setBackground(new Color(0, 94, 66));
    //     }
    // }
}