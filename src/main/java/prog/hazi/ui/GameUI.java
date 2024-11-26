package prog.hazi.ui;

import javax.swing.*;

import prog.hazi.model.Board;
import prog.hazi.model.EmptyHole;
import prog.hazi.model.Settings;
import prog.hazi.model.Team;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JPanel {
    private Board board;
    private Team currentPlayer;

    private JPanel boardPanel;
    private JLabel statusLabel;

    private ImageIcon ballIcon;
    private Color tableColor = new Color(207, 175, 99);
    
    /**
     * Constructs a new GameUI instance with the specified settings.
     *
     * @param st the settings object containing the board size and ball count
     */
    public GameUI(Settings st) {
        this(st.getBoardSize(), st.getBallCount());
    }

    /**
     * Constructs a new GameUI with the specified board size and ball count.
     *
     * @param boardSize the size of the board
     * @param ballCount the number of balls
     */
    public GameUI(int boardSize, int ballCount) {
        board = new Board(boardSize, ballCount); 
        currentPlayer = Team.NORTH;

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/ball.png")); // Adja meg a golyó képének elérési útját
        Image scaledImage = originalIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH); // Resize to tiny size
        ballIcon = new ImageIcon(scaledImage);

        initializeUI();
    }

    /**
     * Reinitializes the game UI by calling the methods to initialize the UI components
     * and update the game board.
     */
    public void reinitialize() {
        initializeUI();
        updateBoard();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(currentPlayer.getBgColor());

        TablePanel tablePanel = new TablePanel(tableColor, 0.03);
        tablePanel.setOpaque(false);
        add(tablePanel, BorderLayout.CENTER);

        boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(currentPlayer.getName() + " can start the game!");
        setLabelStyle(statusLabel);
        JPanel labelPanel = new GameLabelPanel(statusLabel);
        add(labelPanel, BorderLayout.NORTH);

        updateBoard();
    }

    /**
     * Updates the game board UI by removing all existing components and 
     * re-adding them in the correct layout. This method sets up the board 
     * with the current state of the game, including the store buttons and pits 
     * for both teams. It also updates the background color based on the current player.
     */
    public void updateBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        addStoreButton(gbc, Team.SOUTH, 0, 0, 4);
        addPits(gbc, Team.SOUTH, 0, 1, true);
        addPits(gbc, Team.NORTH, 3, 2, false);
        addStoreButton(gbc, Team.NORTH, board.getBoardSize() + 1, 0, 4);

        setBackground(currentPlayer.getBgColor());

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Adds a store button to the game UI.
     *
     * @param gbc         the GridBagConstraints used to layout the button
     * @param team        the team to which the store belongs
     * @param gridx       the x-coordinate of the button in the grid
     * @param gridy       the y-coordinate of the button in the grid
     * @param gridheight  the height of the button in grid cells
     */
    private void addStoreButton(GridBagConstraints gbc, Team team, int gridx, int gridy, int gridheight) {
        JButton storeButton = createHoleButton(board.getHole(team, 0).getBallCount(), team, 3);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridheight = gridheight;
        gbc.insets = new Insets(0, 5, 0, 5);
        boardPanel.add(storeButton, gbc);
    }

    /**
     * Adds pits to the game board UI for a specified team.
     *
     * @param gbc       the GridBagConstraints used for layout management
     * @param team      the team for which the pits are being added (either SOUTH or NORTH)
     * @param pitsY     the y-coordinate for the pits in the grid layout
     * @param labelY    the y-coordinate for the labels in the grid layout
     * @param reverse   if true, pits are added in reverse order; otherwise, they are added in normal order
     */
    private void addPits(GridBagConstraints gbc, Team team, int pitsY, int labelY, boolean reverse) {
        gbc.gridheight = 1;
        int start = reverse ? board.getBoardSize() : 1;
        int end = reverse ? 0 : board.getBoardSize() + 1;
        int step = reverse ? -1 : 1;

        for (int i = start; i != end; i += step) {
            JButton pitButton = createHoleButton(board.getHole(team, i).getBallCount(), team, 3);
            pitButton.addActionListener(new ButtonListener(i, team));
            gbc.gridx = reverse ? board.getBoardSize() - i + 1 : i;
            gbc.gridy = pitsY;
            gbc.insets = new Insets(0, 5, 0, 5);
            pitButton.setForeground(team == Team.SOUTH ? Color.BLUE : Color.RED);
            boardPanel.add(pitButton, gbc);

            JLabel pitCountLabel = new JLabel(String.valueOf(board.getHole(team, i).getBallCount()));
            pitCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            setLabelStyle(pitCountLabel);
            gbc.gridy = labelY;
            gbc.insets = new Insets(0, 0, reverse ? 30 : 0, 0);
            boardPanel.add(pitCountLabel, gbc);
        }
    }

    /**
     * Sets the style for the given JLabel.
     * This includes setting the vertical alignment to center,
     * the font to "Alexandria" with bold style and size 15,
     * and the foreground (Text) color to black.
     *
     * @param label the JLabel to style
     */
    private void setLabelStyle(JLabel label) {
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Alexandria", Font.BOLD, 15));
        label.setForeground(Color.BLACK);
    }

    private JButton createHoleButton(int ballCount, Team t, int rows) {
        JButton button = new RoundedButton("", t.getColor(), tableColor,  Color.BLACK, 5);
        button.setLayout(new GridBagLayout());
        button.setPreferredSize(new Dimension(50, 60));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    
        JPanel ballPanel = new JPanel(new GridBagLayout());
        ballPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        
        for (int i = 0; i < ballCount; i++) {
            gbc.gridx = i % rows;
            gbc.gridy = i / rows;
            ballPanel.add(new JLabel(ballIcon), gbc);
        }
    
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


    /**
     * Sets the board for the game.
     *
     * @param board the {@link Board} object to be set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the current player to the specified team.
     *
     * @param t the {@link Team} to set as the current player
     */
    public void setCurrentPlayer(Team t) {
        currentPlayer = t;
    }

    /**
     * Retrieves the current game board.
     *
     * @return the current instance of the {@link Board}.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the current player.
     *
     * @return the current player as a {@link Team} object.
     */
    public Team getCurrentPlayer() {
        return currentPlayer;
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
