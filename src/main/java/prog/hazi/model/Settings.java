package prog.hazi.model;

public class Settings {
    private static final int MAX_BOARD_SIZE = 10;
    private static final int MAX_BALL_COUNT = 10;

    private int boardSize;
    private int ballCount;

    /**
     * Constructs a new Settings object with the specified board size and ball count.
     *
     * @param bSize the size of the board
     * @param bCount the number of balls
     */
    public Settings(int bSize, int bCount) {
        boardSize = bSize;
        ballCount = bCount;
    }

    /**
     * Returns the size of the board.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Returns the current number of balls.
     *
     * @return the number of balls
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * Sets the size of the board.
     * 
     * @param bSize the size of the board to be set. Must be greater than 0.
     */
    public void setBoardSize(int bSize) {
        if (bSize > 0 && bSize <= MAX_BOARD_SIZE)
            boardSize = bSize;
    }

    /**
     * Sets the number of balls if the provided count is greater than zero.
     *
     * @param bCount the number of balls to set
     *               must be greater than zero
     */
    public void setBallCount(int bCount) {
        if (bCount > 0 && bCount <= MAX_BALL_COUNT)
            ballCount = bCount;
    }

    /**
     * Resets the game settings to default values.
     * Sets the board size to 6 and the ball count to 4.
     */
    public void reset() {
        boardSize = 6;
        ballCount = 4;
    }
}
