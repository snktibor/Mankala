package prog.hazi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MancalaGUI extends JFrame {
    private Board board;
    private JPanel boardPanel;
    private JLabel statusLabel;

    public MancalaGUI() {
        board = new Board(6, 5); 
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Mancala");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Welcome to Mancala!");
        add(statusLabel, BorderLayout.SOUTH);

        updateBoard();
    }

    private void updateBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
    
        // South store button
        JButton storeButtonSouth = new JButton(String.valueOf(board.getHole(Team.SOUTH, 0).getBallCount()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        boardPanel.add(storeButtonSouth, gbc);
    
        // South pits
        gbc.gridheight = 1;
        for (int i = board.boardSize; i > 0; i--) {
            JButton pitButton = new JButton(String.valueOf(board.getHole(Team.SOUTH, i).getBallCount()));
            pitButton.addActionListener(new ButtonListener(i, Team.SOUTH));
            gbc.gridx = board.boardSize -i + 1;
            gbc.gridy = 0;
            pitButton.setBorder(new RoundedBorder(20));
            pitButton.setForeground(Color.BLUE);
            boardPanel.add(pitButton, gbc);
        }
    
        // North pits
        for (int i = 0; i < board.boardSize; i++) {
            JButton pitButton = new JButton(String.valueOf(board.getHole(Team.NORTH, i + 1).getBallCount()));
            pitButton.addActionListener(new ButtonListener(i + 1, Team.NORTH));
            gbc.gridx =  i + 1;
            gbc.gridy = 1;
            pitButton.setBorder(new RoundedBorder(20));
            pitButton.setForeground(Color.RED);
            boardPanel.add(pitButton, gbc);
        }

        // North store button
        JButton storeButtonNorth = new JButton(String.valueOf(board.getHole(Team.NORTH, 0).getBallCount()));
        gbc.gridx = board.boardSize + 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        boardPanel.add(storeButtonNorth, gbc);
        //disable the store buttons
        storeButtonNorth.setEnabled(false);
        storeButtonSouth.setEnabled(false);
        storeButtonNorth.setBorder(new RoundedBorder(20));
        storeButtonSouth.setBorder(new RoundedBorder(20));
        storeButtonNorth.setForeground(Color.RED);
        storeButtonSouth.setForeground(Color.BLUE);
    
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private class ButtonListener implements ActionListener {
        private int holeId;
        private Team team;

        public ButtonListener(int holeId, Team team) {
            this.holeId = holeId;
            this.team = team;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (board.sowing(holeId, team)) {
                    statusLabel.setText("Player " + team + " can repeat the turn.");
                } else {
                    statusLabel.setText("Player " + (team == Team.NORTH ? "SOUTH" : "NORTH") + "'s turn.");
                }
            } catch (EmptyHole emptyHole) {
                statusLabel.setText("The hole is empty.");
            }
            updateBoard();
        }
    }
}
