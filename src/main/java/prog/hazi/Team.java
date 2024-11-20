package prog.hazi;

/**
 * Enum representing the teams in the game.
 * Each team has an ID and a name.
 */
public enum Team {
    NORTH(0, "North"),
    SOUTH(1, "South");

    public final int id;
    private String name;

    /**
     * Constructs a new Team with the specified id and name.
     *
     * @param i the unique identifier for the team
     * @param n the name of the team
     */
    private Team(int i, String n) {
        this.id = i;
        this.name = n;
    }

    /**
     * Returns the opponent team.
     * If the current team is NORTH, it returns SOUTH.
     * If the current team is SOUTH, it returns NORTH.
     *
     * @return the opponent team
     */
    public Team oponentTeam(){
        return this == NORTH ? SOUTH : NORTH;
    }
    
    /**
     * Returns the name of the team.
     *
     * @return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param n the new name of the team
     */
    public void setName(String n) {
        name = n;
    }
}
