package prog.hazi;

import java.util.List;

public class Store extends Hole {
    public Store(Team t){
        super(t, 0, 0);
    }
    public void addBall(List<Ball> b){
        balls.addAll(b);
    }
}
