package prog.hazi;

public enum Team {
    NORTH(0, "North"),
    SOUTH(1, "South");

    public final int id;
    private String name;

    private Team(int i, String n) {
        this.id = i;
        this.name = n;
    }
    public Team oponentTeam(){
        return this == NORTH ? SOUTH : NORTH;
    }
    // name get and set
    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }
}
