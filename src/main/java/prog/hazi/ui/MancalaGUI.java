package prog.hazi.ui;

import javax.swing.*;

import prog.hazi.model.Board;
import prog.hazi.model.Settings;
import prog.hazi.model.Team;
import prog.hazi.util.SettingsHandler;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MancalaGUI extends JFrame {
    private JPanel gamePanel;
    private transient Settings st;

    public MancalaGUI() {
        st = new Settings(6, 4);
        SettingsHandler.readSettings(st, "settings.xml");
        initializeUI();
    }

    /**
     * Initializes the user interface for the Mancala game application.
     * This method sets the look and feel, window title, size, and default close operation.
     * It also adds a window listener to handle the window closing event, creates the menu bar,
     * loads the game panel, and adds it to the main frame.
     */
    private void initializeUI() {
        setLookAndFeel();
        setTitle("Mancala - Sinka Tibor (M3OZV5)");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new CardLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveGame();
                System.exit(0);
            }
        });

        createMenuBar();

        gamePanel = loadGame();
        
        add(gamePanel, "Game");
    }

    /**
     * Creates the menu bar for the Mancala GUI.
     * The menu bar contains two main menus: "Game" and "Settings".
     * 
     * The "Game" menu includes:
     * - "New Game": Starts a new game.
     * 
     * The "Settings" menu includes:
     * - "Set board": Opens a dialog to set the game board.
     * - "Set style": Opens a dialog to set the game style.
     * - "Save settings to file": Saves the current settings to a file named "settings.xml".
     * - "Reset settings": Resets the settings to their default values.
     * 
     * This method sets the created menu bar to the JFrame.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create the Game menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newGameItem = new JMenuItem("New Game");
        gameMenu.add(newGameItem);
        newGameItem.addActionListener(e -> startNewGame());

        // Create the Settings menu
        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);

        JMenuItem setBoard = new JMenuItem("Set board");
        settingsMenu.add(setBoard);
        setBoard.addActionListener(e -> gameBoardDialog());

        JMenuItem setStyle = new JMenuItem("Set stlye");
        settingsMenu.add(setStyle);
        setStyle.addActionListener(e -> gameStyleDialog());

        JMenuItem saveSettings = new JMenuItem("Save settings to file");
        settingsMenu.add(saveSettings);
        saveSettings.addActionListener(e ->  SettingsHandler.writeSettings(st, "settings.xml"));

        JMenuItem resetSettings = new JMenuItem("Reset settings");
        settingsMenu.add(resetSettings);
        resetSettings.addActionListener(e -> SettingsHandler.resetSettings(st));

        setJMenuBar(menuBar);
    }

    /**
     * Sets the look and feel of the application to the system's default.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Opens a dialog to configure team names and colors.
     */
    private void gameStyleDialog() {
        JDialog dialog = new JDialog(this, "Game Style Settings", true);
        JPanel panel = new JPanel(new GridLayout(Team.values().length + 1, 3, 10, 10));

        JTextField[] nameFields = new JTextField[Team.values().length];
        JButton[] colorButtons = new JButton[Team.values().length];
        JButton[] bgColorButtons = new JButton[Team.values().length];

        for (Team t : Team.values()) {
            JLabel nameLabel = new JLabel(t.name() + " Name:");
            nameFields[t.id] = new JTextField(t.getName());
            colorButtons[t.id] = new JButton();
            colorButtons[t.id].setBackground(t.getColor());
            colorButtons[t.id].addActionListener(e -> {
                    Color newColor = JColorChooser.showDialog(dialog, "Choose Color", t.getColor());
                    if (newColor != null)
                        colorButtons[t.id].setBackground(newColor);
            });

            bgColorButtons[t.id] = new JButton();
            bgColorButtons[t.id].setBackground(t.getBgColor());
            bgColorButtons[t.id].addActionListener(e -> {
                    Color newBgColor = JColorChooser.showDialog(dialog, "Choose Background Color", t.getBgColor());
                    if (newBgColor != null)
                        bgColorButtons[t.id].setBackground(newBgColor);
            });

            panel.add(nameLabel);
            panel.add(nameFields[t.id]);
            panel.add(colorButtons[t.id]);
            panel.add(bgColorButtons[t.id]);
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
                for (Team t : Team.values()) {
                    t.setName(nameFields[t.id].getText());
                    t.setColor(colorButtons[t.id].getBackground(), bgColorButtons[t.id].getBackground());
                }
                ((GameUI)gamePanel).updateBoard();
                dialog.dispose();
        });

        panel.add(new JLabel()); // Empty cell
        panel.add(new JLabel()); // Empty cell
        panel.add(saveButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Opens a dialog to set the board size and ball count.
     */
    private void gameBoardDialog() {
        JDialog dialog = new JDialog(this, "New Game Settings", true);
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel rowsLabel = new JLabel("Pits:");
        JTextField rowsField = new JTextField(String.valueOf(st.getBoardSize()));
        JLabel seedsLabel = new JLabel("Seeds:");
        JTextField seedsField = new JTextField(String.valueOf(st.getBallCount()));

        panel.add(rowsLabel);
        panel.add(rowsField);
        panel.add(seedsLabel);
        panel.add(seedsField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                st.setBoardSize(Integer.parseInt(rowsField.getText()));
                st.setBallCount(Integer.parseInt(seedsField.getText()));
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        panel.add(new JLabel()); // Empty label for spacing
        panel.add(saveButton);

        dialog.add(panel);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Starts a new game by resetting the game panel.
     */
    private void startNewGame() {
        // Remove the old game panel
        remove(gamePanel);

        gamePanel = new GameUI(st);
        add(gamePanel, "Game");

        revalidate();
        repaint();
    }

    /**
     * Saves the current state of the game.
     * This method saves the current board configuration and the current player's turn.
     */
    private void saveGame() {
        saveBoard();
        saveCurrentPlayer();
    }

    /**
     * Loads the game state by setting the board and current player from saved data.
     * It then reinitializes the game panel to reflect the loaded state.
     */
    private GameUI loadGame() {
        GameUI gameUI = new GameUI(st);
        gameUI.setBoard(loadBoard());
        gameUI.setCurrentPlayer(loadCurrentPlayer());
        gameUI.reinitialize();
        return gameUI;
    }

    /**
     * Saves the current state of the game board to a file named "board.ser".
     */
    private void saveBoard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("board.ser"))) {
            oos.writeObject(((GameUI)gamePanel).getBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game board from a file named "board.ser".
     *
     * @return the loaded Board object, or a new Board if loading fails
     */
    private Board loadBoard() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("board.ser"))) {
            return (Board)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Board(st.getBoardSize(), st.getBallCount()); // Return a new board if loading fails
        }
    }

    /**
     * Saves the current player to a file named "currentPlayer.ser".
     */
    private void saveCurrentPlayer() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentPlayer.ser"))) {
            oos.writeObject(((GameUI)gamePanel).getCurrentPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the current player from a file named "currentPlayer.ser".
     * 
     * @return the current player as a Team object, or Team.NORTH if loading fails.
     */
    private Team loadCurrentPlayer() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("currentPlayer.ser"))) {
            return (Team)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Team.NORTH; // Return NORTH if loading fails
        }
    }
}
