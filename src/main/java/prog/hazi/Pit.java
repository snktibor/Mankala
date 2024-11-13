package prog.hazi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Pit extends Hole {

    public Pit(Team t, int ballsCount, int tid){
        super(t, ballsCount, tid);
    }
    public Boolean isPitEmpty(){
        return balls.isEmpty();
    }
    public List<Ball> removeBalls() throws EmptyHole {
        if (isPitEmpty()) throw new EmptyHole();
        List<Ball> temp = new LinkedList<>(balls);
        if(!balls.removeAll(balls)) throw new EmptyHole("Failed remove");
        return temp;
    }


}
