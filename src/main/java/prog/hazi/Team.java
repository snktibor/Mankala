package prog.hazi;

public enum Team {
    NORTH(0),
    SOUTH(1);

    public final int id;

    private Team(int i) {
        this.id = i;
    }
    public Team oponentTeam(){
        return this == NORTH ? SOUTH : NORTH;
    }
}
