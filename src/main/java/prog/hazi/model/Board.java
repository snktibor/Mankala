package prog.hazi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Hole> holes = new ArrayList<>();
    private final int boardSize; //6*2 + 2
    private final int ballcount;

    /**
     * Constructs a Board object with the specified board size and ball count.
     *
     * @param bSize the number of pits per player on the board
     * @param bCount the initial number of balls in each pit
     */
    public Board(int bSize, int bCount){
        boardSize = bSize;
        ballcount = bCount;

        //pitsA: (1,2,3,4,5,6), StoreA, pitsB: (1,2,3,4,5,6), StoreB
        for (Team t: Team.values()) {
            for (int i = 1; i <= boardSize; i++) {
                holes.add(new Pit(t, ballcount, i));
            }
            holes.add(new Store(t));
        }
    }
    
    /**
     * Returns the board size.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Returns the inital number of balls the pits have.
     *
     * @return the ball count
     */
    public int getNumberOfBalls() {
        return ballcount;
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
        int i = calculateHoleIndex(t, id) + 1;
        for(; !temp.isEmpty(); i = (i + 1) % (boardSize * 2 + 2)){
            Hole h2 = holes.get(i);
            if(h2.getId() == 0 && h2.getTeam() != t)
                continue;
            else if (temp.size() == 1 && h2.getId() > 0 && ((Pit)h2).isPitEmpty() && !((Pit)getHole(h2.getTeam().oponentTeam(), boardSize + 1 - h2.getId())).isPitEmpty()){
                ((Store)getHole(t, 0)).addBall(((Pit)getHole(h2.getTeam().oponentTeam(), boardSize + 1 - h2.getId())).removeBalls()); //Removes balls from the oponent's pit if landing on empty pit
                ((Store)getHole(t, 0)).addBall(temp.remove(0));
                }
            else if (temp.size() == 1 && h2.getId() == 0 /* && h2.getTeam() == t */){
                h2.addBall(temp.remove(0));
                canRepeate = true;
            }
            else
                h2.addBall(temp.remove(0));
        }
        Team emptyPits = whichPitsEmpty();
        if (emptyPits != null) {
            //cleanPitsOfTeam(emptyPits) it is not necessary, but it is more readable
            cleanPitsOfTeam(emptyPits.oponentTeam());
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
        return t.id*(boardSize + 1) + (tid == 0 ? boardSize : tid - 1);
    }

    /**
     * Retrieves the hole for the specified team and hole index.
     *
     * @param t the team for which the hole is to be retrieved
     * @param tid the index of the hole within the team's side of the board
     * @return the Hole object corresponding to the specified team and hole index
     */
    public Hole getHole(Team t, int tid){
        return holes.get(calculateHoleIndex(t, tid));
    }

    /**
     * Determines which team's pits are empty.
     *
     * @return the team whose pits are empty, or null if neither team's pits are empty.
     */
    public Team whichPitsEmpty(){

        for (Team t: Team.values()) {
            boolean empty = true;
            for (int i = 1; i <= boardSize; i++) {
                if (!((Pit)getHole(t, i)).isPitEmpty()) {
                    empty = false;
                    break;
                }
            }
            if (empty) return t;
        }
        return null;
    }

    //! simplify
    /**
     * Cleans all pits of the specified team by transferring all balls from the pits to the team's store.
     *
     * @param t the team whose pits are to be cleaned
     */
    public void cleanPitsOfTeam(Team t) {
        Store s1 = (Store)getHole(t, 0);
        for (Hole h: holes) {
            if (h.getId() > 0 && h.getTeam() == t && h.getBallCount() > 0)
                s1.addBall(((Pit)h).removeBalls());
        }
    }

    public Team getWinnerTeam(){
        if (!isGameOver()) return null;
        return getHole(Team.SOUTH, 0).getBallCount() > getHole(Team.NORTH, 0).getBallCount() ? Team.SOUTH : Team.NORTH;
    }

    public boolean isGameOver(){
        return whichPitsEmpty() != null;
    }

    public boolean isDraw(){
        return isGameOver() && getHole(Team.SOUTH, 0).getBallCount() == getHole(Team.NORTH, 0).getBallCount();
    }
}
