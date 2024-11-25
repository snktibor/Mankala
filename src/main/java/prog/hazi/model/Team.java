package prog.hazi.model;
import java.awt.Color;
/**
 * Enum representing the teams in the game.
 * Each team has an ID and a name.
 */
public enum Team {
    NORTH(0, "North", Color.RED),
    SOUTH(1, "South", Color.BLUE);

    public final int id;
    private String name;
    private Color color;
    private Color backGColor;

    /**
     * Constructs a new Team with the specified id and name.
     *
     * @param i the unique identifier for the team
     * @param n the name of the team
     */
    private Team(int i, String n, Color c) {
        this.id = i;
        this.name = n;
        this.color = c;
        this.backGColor = c;
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
     * Returns the color associated with the team.
     *
     * @return the color of the team
     */
    public Color getColor() {
        return color;
    }
    public Color getBgColor() {
        return backGColor;
    }

    /**
     * Sets the color of the team.
     *
     * @param c the color to set
     */
    public void setColor(Color c, Color bg) {
        color = c;
        backGColor = bg;
    }

    public static Team getTeam(int id){
        return id == 0 ? NORTH : SOUTH;
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
