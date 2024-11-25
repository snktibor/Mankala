package prog.hazi.ui;

import javax.swing.*;

import prog.hazi.model.Board;
import prog.hazi.model.Settings;
import prog.hazi.model.Team;
import prog.hazi.util.SettingsHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MancalaGUI extends JFrame {
    private JPanel gamePanel;
    //private JPanel menuPanel;

    private transient Settings st;

    public MancalaGUI() {
        st = new Settings(6, 4);
        SettingsHandler.readSettings(st, "settings.xml");
        // Team.NORTH.setName("Red");
        // Team.NORTH.setColor(new Color(255, 68, 51), new Color(184, 62, 51));
        // Team.SOUTH.setName("Blue");
        // Team.SOUTH.setColor(new Color(75, 127, 210), new Color(39, 84, 157));
        // SettingsHandler.writeSettings(st, "settings.xml");
        initializeUI();
    }
    

    private void initializeUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setTitle("Mancala - Sinka Tibor (M3OZV5)");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveBoard();
                saveCurrentPlayer();
                System.exit(0);
            }
        });

        JMenuBar menuBar = new JMenuBar();

        // Create the Game menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newGameItem = new JMenuItem("New Game");
        gameMenu.add(newGameItem);

        // Add action listener to the New Game menu item
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);
        JMenuItem setBoard = new JMenuItem("Set board");
        settingsMenu.add(setBoard);

        // Add action listener to the New Game menu item
        setBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoardDialog();
            }
        });

        JMenuItem setStyle = new JMenuItem("Set stlye");
        settingsMenu.add(setStyle);
        setStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStyleDialog();
                ((GameUI)gamePanel).updateBoard();
            }
        });


        JMenuItem saveSettings = new JMenuItem("Save settings to file");
        settingsMenu.add(saveSettings);
        saveSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsHandler.writeSettings(st, "settings.xml");
            }
        });

        JMenuItem resetSettings = new JMenuItem("Reset settings");
        settingsMenu.add(resetSettings);
        resetSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsHandler.resetSettings(st);
                ((GameUI)gamePanel).updateBoard();
            }
        });

        setJMenuBar(menuBar);


        gamePanel = new GameUI(st);
        ((GameUI)gamePanel).setBoard(loadBoard());
        ((GameUI)gamePanel).setCurrentPlayer(loadCurrentPlayer());
        ((GameUI)gamePanel).reinitialize();
        
        
        //menuPanel = new MenuPanel(st, this);

        add(gamePanel, "Game");
        //add(menuPanel, "Menu");

        //showMenu();
    }

private void gameStyleDialog() {
        // Create a dialog to get the team styles
        JDialog dialog = new JDialog(this, "Game Style Settings", true);
        JPanel panel = new JPanel(new GridLayout(Team.values().length + 1, 3, 10, 10));

        JTextField[] nameFields = new JTextField[Team.values().length];
        JButton[] colorButtons = new JButton[Team.values().length];
        JButton[] bgColorButtons = new JButton[Team.values().length];

        for (Team t : Team.values()) {
            JLabel nameLabel = new JLabel(t.name() + " Name:");
            JTextField nameField = new JTextField(t.getName());
            JButton colorButton = new JButton();
            colorButton.setBackground(t.getColor());
            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color newColor = JColorChooser.showDialog(dialog, "Choose Color", t.getColor());
                    if (newColor != null) {
                        colorButton.setBackground(newColor);
                    }
                }
            });

            JButton bgColorButton = new JButton();
            bgColorButton.setBackground(t.getBgColor());
            bgColorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color newBgColor = JColorChooser.showDialog(dialog, "Choose Background Color", t.getBgColor());
                    if (newBgColor != null) {
                        bgColorButton.setBackground(newBgColor);
                    }
                }
            });

            nameFields[t.id] = nameField;
            colorButtons[t.id] = colorButton;
            bgColorButtons[t.id] = bgColorButton;

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(colorButton);
            panel.add(bgColorButton);
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Team t : Team.values()) {
                    t.setName(nameFields[t.id].getText());
                    t.setColor(colorButtons[t.id].getBackground(), bgColorButtons[t.id].getBackground());
                }
                dialog.dispose();
            }
        });

        panel.add(new JLabel()); // Empty cell
        panel.add(new JLabel()); // Empty cell
        panel.add(saveButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void gameBoardDialog() {
        // Create a dialog to get the new game settings
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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                int rows = Integer.parseInt(rowsField.getText());
                int seeds = Integer.parseInt(seedsField.getText());
                st.setBoardSize(rows);
                st.setBallCount(seeds);
                SettingsHandler.writeSettings(st, "settings.xml");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        panel.add(new JLabel()); // Empty label for spacing
        panel.add(saveButton);

        dialog.add(panel);
        

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void startNewGame() {
        // Remove the old game panel
        remove(gamePanel);

        // Create a new game panel with the updated settings
        gamePanel = new GameUI(st);
        add(gamePanel, "Game");

        // Show the new game panel
        showGame();

        // Refresh the frame
        revalidate();
        repaint();
    }

    private void showGame() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Game");
    }

    private void saveBoard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("board.ser"))) {
            oos.writeObject(((GameUI) gamePanel).getBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Board loadBoard() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("board.ser"))) {
            return (Board) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Board(st.getBoardSize(), st.getBallCount()); // Return a new board if loading fails
        }
    }

    private void saveCurrentPlayer() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentPlayer.ser"))) {
            oos.writeObject(((GameUI) gamePanel).getCurrentPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Team loadCurrentPlayer() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("currentPlayer.ser"))) {
            return (Team) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Team.NORTH; // Return NORTH if loading fails
        }
    }

    // private void saveGameUI() {
    //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gameUI.ser"))) {
    //         oos.writeObject(gamePanel);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private GameUI loadGameUI() {
    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("board.ser"))) {
    //         Board boardSer = (Board)) ois.readObject();
    //         boardSer.reinitialize();
    //         return boardSer;
    //     } catch (IOException | ClassNotFoundException e) {
    //         e.printStackTrace();
    //         return new GameUI(st); // Return a new GameUI if loading fails
    //     }
    // }
    
    // private void showMenu() {
    //     CardLayout cl = (CardLayout) getContentPane().getLayout();
    //     cl.show(getContentPane(), "Menu");
    // }

    // public void startGame() {
    //     gamePanel = new GameUI(st);
    //     showGame();
    // }
}
