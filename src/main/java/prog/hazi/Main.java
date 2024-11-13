package prog.hazi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public final static List<Hole> holes = new ArrayList<>();
    static int boardSize = 6; //6*2 + 2
    static int ballcount = 5;
    public static void main(String[] args) {

        //StoreA, pitsA: (1,2,3,4,5,6), StoreB, pitsB: (1,2,3,4,5,6)
        for (Team t: Team.values()) {
            holes.add(new Store(t));
            for (int i = 1; i <= boardSize; i++) {
                holes.add(new Pit(t, ballcount, i));
            }
        }




        //rakkatintas
        int rakattintottId = 3;
        Team rakattintottTeam = Team.North;
        Pit h = (Pit)getHole(rakattintottTeam, rakattintottId);
        if (h.isPitEmpty()) throw new Exception();
        List<Ball> temp = h.removeBalls();
        for(int i = rakattintottId + rakattintottTeam.id*(boardSize + 1); i <= boardSize; i++)

    }
    public static Hole getHole(Team t, int tid){
        return holes.get(tid + t.id*(boardSize + 1));
    }
}