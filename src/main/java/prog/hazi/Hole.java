package prog.hazi;

import java.util.LinkedList;
import java.util.List;

public class Hole {
    protected final List<Ball> balls = new LinkedList<>();
    private final Team team;
    private final int teamId;

    /**
     * Returns the number of balls in the hole.
     *
     * @return the count of balls in the hole
     */
    public int getBallCount(){
        return balls.size();
    }

    /**
     * Constructs a Hole object.
     *
     * @param t the team associated with the hole
     * @param ballsCount the initial number of balls in the hole
     * @param tid the team ID
     */
    public Hole(Team t, int ballsCount, int tid){
        team = t;
        teamId = tid;
        for (int i = 0; i < ballsCount; i++) {
            balls.add(new Ball());
        }
    }

    /**
     * Adds a ball to the hole.
     *
     * @param b the ball to be added
     * @throws EmptyHole if the ball is null
     */
    public void addBall(Ball b) throws EmptyHole {
        if (b == null) throw new EmptyHole("null ball"); 
        balls.add(b);
    }

    /**
     * Returns the team associated with this hole.
     *
     * @return the team associated with this hole
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Returns the ID of the team associated with this hole.
     *
     * @return the team ID
     */
    public int getId() {
        return teamId;
    }
}
