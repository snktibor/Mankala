package prog.hazi;

import java.util.LinkedList;
import java.util.List;

public class Hole {
    protected final List<Ball> balls = new LinkedList<>();
    private final Team team;
    private final int teamId;

    public int getBallCount(){
        return balls.size();
    }

    public Hole(Team t, int ballsCount, int tid){
        team = t;
        teamId = tid;
        for (int i = 0; i < ballsCount; i++) {
            balls.add(new Ball());
        }
    }
    public void addBall(Ball b) throws EmptyHole {
        if (b == null) throw new EmptyHole("null ball"); 
        balls.add(b);
    }
    public Team getTeam() {
        return team;
    }
    public int getId() {
        return teamId;
    }
}
