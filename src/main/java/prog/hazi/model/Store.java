package prog.hazi.model;

import java.util.List;

public class Store extends Hole {
    
    /**
     * Constructs a new Store object.
     *
     * @param t the team associated with the store
     */
    public Store(Team t){
        super(t, 0, 0);
    }
    
    /**
     * Adds a list of balls to the store.
     *
     * @param b the list of balls to be added
     */
    public void addBall(List<Ball> b){
        balls.addAll(b);
    }
}
