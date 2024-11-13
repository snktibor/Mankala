package prog.hazi;

public class EmptyHole extends Exception{
    public EmptyHole() {
        super();
    }
    public EmptyHole(String message) {
        super(message);
    }
    public EmptyHole(String message, Throwable cause) {
        super(message, cause);
    }
}


