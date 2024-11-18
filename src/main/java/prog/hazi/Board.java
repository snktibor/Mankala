package prog.hazi;

import java.util.ArrayList;
import java.util.List;

public class Board {
    protected static final List<Hole> holes = new ArrayList<>();
    int boardSize; //6*2 + 2
    int ballcount;

    /**
     * Constructs a Board object with the specified board size and ball count.
     *
     * @param bSize the number of pits per player on the board
     * @param bCount the initial number of balls in each pit
     */
    public Board(int bSize, int bCount){
        boardSize = bSize;
        ballcount = bCount;

        //StoreA, pitsA: (1,2,3,4,5,6), StoreB, pitsB: (1,2,3,4,5,6)
        for (Team t: Team.values()) {
            holes.add(new Store(t));
            for (int i = 1; i <= boardSize; i++) {
                holes.add(new Pit(t, ballcount, i));
            }
        }
    }

    /**
     * Performs the sowing (vetés) action starting from the specified pit for the given team.
     *
     * @param id the ID of the pit to start sowing from
     * @param t the team performing the action
     * @throws EmptyHole if the starting pit is empty
     * @return true if the player can repeat the turn, false otherwise
     */
    public boolean sowing(int id, Team t) throws EmptyHole {
        Pit h = (Pit)getHole(t, id);
        if (h.isPitEmpty()) 
            throw new EmptyHole();
        boolean canRepeate = false;
        List<Ball> temp = h.removeBalls();
        int i = calculateHoleIndex(t, id);
        for(; !temp.isEmpty(); i = (i + 1) % (boardSize * 2 + 2)){
            Hole h2 = holes.get(i);
            if(h2.getId() == 0 && h2.getTeam() != t)
                continue;
            else if (temp.size() == 1 && h2.getId() > 0 && ((Pit)h2).isPitEmpty())
                ((Store)getHole(t, 0)).addBall(((Pit)getHole(t.oponentTeam(), h2.getId())).removeBalls()); //Removes balls from the oponent's pit if landing on empty pit
            else if (temp.size() == 1 && h2.getId() == 0 /* && h2.getTeam() == t */)
                canRepeate = true;
            holes.get(i).addBall(temp.remove(0));
        }
        return canRepeate;
    }

    /**
     * Calculates the index of a hole on the board based on the team and hole ID.
     *
     * @param t the team for which the hole index is being calculated
     * @param tid the ID of the hole within the team's side of the board
     * @return the calculated index of the hole on the board
     */
    private int calculateHoleIndex(Team t, int tid){
        return tid + t.id*(boardSize + 1);
    }

    /**
     * Retrieves the hole for the specified team and hole index.
     *
     * @param t the team for which the hole is to be retrieved
     * @param tid the index of the hole within the team's side of the board
     * @return the Hole object corresponding to the specified team and hole index
     */
    private Hole getHole(Team t, int tid){
        return holes.get(calculateHoleIndex(t, tid));
    }


    /**
     * Determines which team's pits are empty.
     *
     * @return the team whose pits are empty, or null if neither team's pits are empty.
     */
    public Team whichPitsEmpty(){
        Team t = null;
        if (((Pit)getHole(Team.NORTH, 0)).isPitEmpty()){
            t = Team.NORTH;
        }
        else if (((Pit)getHole(Team.SOUTH, 0)).isPitEmpty()){
            t = Team.SOUTH;
        }
        return t;
    }

    /**
     * Cleans all pits of the specified team by transferring all balls from the pits to the team's store.
     *
     * @param t the team whose pits are to be cleaned
     */
    public void cleanPitsOfTeam(Team t) {
        Store s1 = (Store)getHole(t, 0);
        for (Hole h: holes) {
            if (h.getId() > 0 && h.getTeam() == t && h.getBallsCount() > 0)
                s1.addBall(((Pit)h).removeBalls());
        }
    }
}
