package prog.hazi.ui;

import javax.swing.*;

import prog.hazi.model.Settings;
import prog.hazi.util.SettingsHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MancalaGUI extends JFrame {
    private JPanel gamePanel;
    private JPanel menuPanel;

    private Settings st;


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
        setTitle("Mancala");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        gamePanel = new GameUI(st.getBoardSize(), st.getBallCount());


        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        JLabel menuLabel = new JLabel("Menu");

        menuPanel.add(menuLabel, BorderLayout.CENTER);

        add(gamePanel, "Game");
        add(menuPanel, "Menu");

        showMenu();
        showGame();
    }
    
    private void showMenu() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Menu");
    }

    private void showGame() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Game");
    }
}
