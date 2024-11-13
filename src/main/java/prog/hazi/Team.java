package prog.hazi;

public enum Team {
    North(0),
    South(1);

    public final int id;

    private Team(int i) {
        this.id = i;
    }
}
