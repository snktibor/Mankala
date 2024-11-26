package prog.hazi.model;

import static org.junit.Assert.*;
import org.junit.*;

public class TestBoard {
    private Board board;

    @Before
    public void setUp() {
        // Initialize the board with a board size of 6 and 4 balls per pit
        board = new Board(6, 4);
    }

    @Test
    public void testInitialBoardSetup() {
        // Test the initial setup of the board
        for (int i = 0+1; i < 6+1; i++) {
            assertEquals(4, board.getHole(Team.NORTH, i).getBallCount());
            assertEquals(4, board.getHole(Team.SOUTH, i).getBallCount());
        }
    }

    @Test
    public void testGetBoardSize() {
        // Test getting the board size
        assertEquals(6, board.getBoardSize());
    }

    @Test
    public void testGetNumberOfBalls() {
        // Test getting the number of balls in the pits
        assertEquals(4, board.getNumberOfBalls());
    }

    @Test
    public void testIsGameOver() {
        // Test if the game is over
        board.cleanPitsOfTeam(Team.NORTH);
        assertTrue(board.isGameOver());
    }

    @Test
    public void testGetWinner() {
        // Test getting the winner
        board.cleanPitsOfTeam(Team.NORTH);
        assertEquals(Team.NORTH, board.getWinnerTeam());
    }

    @Test
    public void testSoving() throws EmptyHole {
        // Test moving balls
        board.sowing(5, Team.NORTH);
        assertEquals(0, board.getHole(Team.NORTH, 5).getBallCount());
        assertEquals(5, board.getHole(Team.NORTH, 6).getBallCount());
        assertEquals(1, board.getHole(Team.NORTH, 0).getBallCount());
        assertEquals(5, board.getHole(Team.SOUTH, 1).getBallCount());
        assertEquals(5, board.getHole(Team.SOUTH, 2).getBallCount());
        assertEquals(4, board.getHole(Team.SOUTH, 3).getBallCount());
    }

    @Test(expected = EmptyHole.class)
    public void testEmptyHole() throws EmptyHole {
        // Test moving balls from an empty pit
        board.sowing(1, Team.NORTH);
        board.sowing(1, Team.NORTH);
    }

    @Test
    public void testRepeatTurn() throws EmptyHole {
        // Test if the player can repeat the turn
        assertTrue(board.sowing(3, Team.NORTH));
    }

    @Test
    public void testGameNotOver() {
        // Test if the game is not over
        assertFalse(board.isGameOver());
        assertFalse(board.isDraw());
    }

    @Test
    public void testWinnerRun() throws EmptyHole {
        board = new Board(6, 1);
        for (int i = 0+1; i < 6+1; i++) {
            board.sowing(i, Team.NORTH);
        }
        assertTrue(board.isGameOver());
    }

    @Test
    public void testDraw() {
        board.cleanPitsOfTeam(Team.NORTH);
        board.cleanPitsOfTeam(Team.SOUTH);
        assertTrue(board.isDraw());
    }

    @Test
    public void testDrawNotOver() throws EmptyHole {
        board.cleanPitsOfTeam(Team.NORTH);
        board.sowing(6, Team.SOUTH);
        assertEquals(0, board.getHole(Team.SOUTH, 4).getBallCount());
        assertEquals(0, board.getHole(Team.SOUTH, 6).getBallCount());
        assertEquals(1, board.getHole(Team.NORTH, 2).getBallCount());
        assertEquals(0, board.getHole(Team.NORTH, 3).getBallCount());
    }

    @Test
    public void testDrawBigBoard() throws EmptyHole {
        board = new Board(3, 6);
        board.sowing(2, Team.SOUTH);
        assertEquals(0, board.getHole(Team.SOUTH, 2).getBallCount());
        assertEquals(1, board.getHole(Team.SOUTH, 0).getBallCount());
        assertEquals(7, board.getHole(Team.NORTH, 1).getBallCount());
        assertEquals(0, board.getHole(Team.NORTH, 0).getBallCount());
        assertEquals(7, board.getHole(Team.SOUTH, 1).getBallCount());
    }
}