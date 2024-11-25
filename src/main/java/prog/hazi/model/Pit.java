package prog.hazi.model;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pit extends Hole {

    /**
     * Constructs a new Pit object.
     *
     * @param t the team associated with this pit
     * @param ballsCount the initial number of balls in the pit
     * @param tid the team ID
     */
    public Pit(Team t, int ballsCount, int tid){
        super(t, ballsCount, tid);
    }

    /**
     * Checks if the pit is empty.
     *
     * @return true if the pit is empty, false otherwise.
     */
    public Boolean isPitEmpty(){
        return balls.isEmpty();
    }

    /**
     * Removes all balls from the pit and returns them as a list.
     *
     * @return a list of balls that were in the pit before removal.
     */
    public List<Ball> removeBalls() {
        List<Ball> temp = new LinkedList<>(balls);
        balls.clear();
        return temp;
    }
}
