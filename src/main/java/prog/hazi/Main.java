package prog.hazi;


public class Main {
    public static void main(String[] args) {
        Board b = new Board(6, 5);
        try {
            b.sowing(1, Team.NORTH);
        } catch (EmptyHole e) {
            e.printStackTrace();
        }

    }
}