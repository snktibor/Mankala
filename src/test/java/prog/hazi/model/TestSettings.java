package prog.hazi.model;

import static org.junit.Assert.*;
import org.junit.*;

public class TestSettings {
    private Settings settings;

    @Before
    public void setUp() {
        // Initialize the settings with a board size of 6 and 4 balls
        settings = new Settings(6, 4);
    }

    @Test
    public void testGetBoardSize() {
        // Test getting the board size
        assertEquals(6, settings.getBoardSize());
    }

    @Test
    public void testGetNumberOfBalls() {
        // Test getting the number of balls in the pits
        assertEquals(4, settings.getBallCount());
    }

    @Test
    public void testSetBoardSize() {
        // Test setting the board size
        settings.setBoardSize(8);
        assertEquals(8, settings.getBoardSize());
    }

    @Test
    public void testSetNumberOfBalls() {
        // Test setting the number of balls
        settings.setBallCount(5);
        assertEquals(5, settings.getBallCount());
    }

    @Test
    public void testSetBoardSizeInvalid() {
        // Test setting the board size to an invalid value
        settings.setBoardSize(0);
        assertEquals(6, settings.getBoardSize());
    }

    @Test
    public void testSetNumberOfBallsInvalid() {
        // Test setting the number
        settings.setBallCount(0);
        assertEquals(4, settings.getBallCount());
    }

    @Test 
    public void testReset() {
        // Test resetting the settings
        settings.setBoardSize(8);
        settings.setBallCount(5);
        settings.reset();
        assertEquals(6, settings.getBoardSize());
        assertEquals(4, settings.getBallCount());
    }


}
