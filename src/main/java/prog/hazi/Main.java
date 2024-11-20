package prog.hazi;

import javax.swing.SwingUtilities;
import javax.swing.*;

public class Main {
    // public static void main(String[] args) {
    //     Board b = new Board(6, 5);
    //     try {
    //         b.sowing(1, Team.NORTH);
    //     } catch (EmptyHole e) {
    //         e.printStackTrace();
    //     }

    // }

    public static void main(String[] args) {
        JFrame mancala = new MancalaGUI();
        mancala.setVisible(true);

    }
}