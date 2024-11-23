package prog.hazi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JPanel {
    private transient Board board;
    private Team currentPlayer;

    private JPanel boardPanel;
    private JPanel labelPanel;
    private JLabel statusLabel;

    private ImageIcon ballIcon;

    

    public GameUI(int boardSize, int ballCount) {
        board = new Board(boardSize, ballCount); 
        currentPlayer = Team.NORTH;

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/ball.png")); // Adja meg a golyó képének elérési útját
        Image scaledImage = originalIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH); // Resize to tiny size
        ballIcon = new ImageIcon(scaledImage);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(currentPlayer.getBgColor());

        // Create and add the table panel
        TablePanel tablePanel = new TablePanel();
        tablePanel.setOpaque(false);
        add(tablePanel, BorderLayout.CENTER);

        boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Welcome to Mancala!");

        labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        labelPanel.add(statusLabel);
        labelPanel.setOpaque(false);

        add(labelPanel, BorderLayout.NORTH);

        updateBoard();
    }

    private void updateBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
    
        // South store button
        JButton storeButtonSouth = createHoleButton(board.getHole(Team.SOUTH, 0).getBallCount(), Team.SOUTH, 12, 3);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.insets = new Insets(0, 5, 0, 5);
        boardPanel.add(storeButtonSouth, gbc);
    
        // South pits
        gbc.gridheight = 1;
        for (int i = board.getBoardSize(); i > 0; i--) {
            //JButton pitButton = new JButton(String.valueOf(board.getHole(Team.SOUTH, i).getBallCount()));
            JButton pitButton = createHoleButton(board.getHole(Team.SOUTH, i).getBallCount(), Team.SOUTH, 5, 3);
            pitButton.addActionListener(new ButtonListener(i, Team.SOUTH));
            gbc.gridx = board.getBoardSize() -i + 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 5, 0, 5);
            pitButton.setBorder(new RoundedBorder(20));
            pitButton.setForeground(Color.BLUE);
            boardPanel.add(pitButton, gbc);

            JLabel pitCountLabel = new JLabel(String.valueOf(board.getHole(Team.SOUTH, i).getBallCount()));
            pitCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pitCountLabel.setVerticalAlignment(SwingConstants.TOP);
            pitCountLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 20, 0);
            boardPanel.add(pitCountLabel, gbc);
        }
    
        // North pits
        for (int i = 0; i < board.getBoardSize(); i++) {
            //JButton pitButton = new JButton(String.valueOf(board.getHole(Team.NORTH, i + 1).getBallCount()));
            JButton pitButton = createHoleButton(board.getHole(Team.NORTH, i + 1).getBallCount(), Team.NORTH, 5, 3);
            pitButton.addActionListener(new ButtonListener(i + 1, Team.NORTH));
            gbc.gridx =  i + 1;
            gbc.gridy = 3;
            gbc.insets = new Insets(0, 5, 0, 5);
            pitButton.setBorder(new RoundedBorder(20));
            pitButton.setForeground(Color.RED);
            boardPanel.add(pitButton, gbc);

            
            JLabel pitCountLabel = new JLabel(String.valueOf(board.getHole(Team.NORTH, i+1).getBallCount()));
            pitCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pitCountLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            pitCountLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            gbc.gridy = 2;
            gbc.insets = new Insets(20, 5, 0, 5);
            boardPanel.add(pitCountLabel, gbc);
        }

        // North store button
        JButton storeButtonNorth = createHoleButton(board.getHole(Team.NORTH, 0).getBallCount(), Team.NORTH, 12, 3);
        gbc.gridx = board.getBoardSize() + 1;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.insets = new Insets(0, 5, 0, 5);
        boardPanel.add(storeButtonNorth, gbc);
        //disable the store buttons
        storeButtonNorth.setEnabled(false);
        storeButtonSouth.setEnabled(false);
        // storeButtonNorth.setBorder(new RoundedBorder(20));
        // storeButtonSouth.setBorder(new RoundedBorder(20));
        // storeButtonNorth.setForeground(Color.RED);
        // storeButtonSouth.setForeground(Color.BLUE);
    
        setBackground(currentPlayer.getBgColor());

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private JButton createHoleButton(int ballCount, Team t, int cols, int rows) {
        JButton button = new RoundedButton("", t.getColor(), Color.BLACK, 5);
        button.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        button.setPreferredSize(new Dimension(50, 60)); // Set fixed size for the button
        button.setMargin(new Insets(0, 0, 0, 0)); // Reduce padding inside the button

        button.setContentAreaFilled(false); // Remove default button content area
        //button.setBorderPainted(false); // Remove default button border
        button.setFocusPainted(false); // Remove default focus painting
    
        JPanel ballPanel = new JPanel(new GridBagLayout());
        ballPanel.setOpaque(false); // Make the panel transparent
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0); // Add some space between balls
        gbc.anchor = GridBagConstraints.CENTER;
    
        //int cols = 5; // Number of columns for balls
        //int rows = (int) Math.ceil((double) ballCount / cols); // Calculate the number of rows needed
    

        //rows 

        for (int i = 0; i < ballCount; i++) {
            gbc.gridx = i % rows;
            gbc.gridy = i / rows;
            ballPanel.add(new JLabel(ballIcon), gbc);
        }
    
        // Create a wrapper panel with GridBagLayout to center the ballPanel
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        wrapperGbc.gridx = 0;
        wrapperGbc.gridy = 0;
        wrapperGbc.weightx = 1.0;
        wrapperGbc.weighty = 1.0;
        wrapperGbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(ballPanel, wrapperGbc);
    
        button.add(wrapperPanel);
        return button;
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
            if (currentPlayer == team){
                try {
                    if (board.sowing(holeId, team)) {
                        statusLabel.setText(currentPlayer.getName() + " can repeat the turn.");
                    } else {
                        currentPlayer = team.oponentTeam();
                        statusLabel.setText(currentPlayer.getName() + "'s turn.");
                    }
                } catch (EmptyHole emptyHole) {
                    statusLabel.setText("The hole is empty.");
                }
                updateBoard();
            }
        }
    }

}
