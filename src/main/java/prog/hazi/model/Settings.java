package prog.hazi.model;

public class Settings {
    private int boardSize;
    private int ballCount;

    public Settings(int bSize, int bCount) {
        boardSize = bSize;
        ballCount = bCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBoardSize(int bSize) {
        if (bSize > 0)
            boardSize = bSize;
    }

    public void setBallCount(int bCount) {
        if (bCount > 0)
            ballCount = bCount;
    }

    public void reset() {
        boardSize = 6;
        ballCount = 4;
    }
}
