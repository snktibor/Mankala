package prog.hazi.model;

import static org.junit.Assert.*;
import org.junit.*;

public class TestHole {
    private Hole hole;
    
    @Before
    public void setUp() {
        // Initialize the store with the NORTH team
        hole = new Hole(Team.NORTH, 4, 1);
    }

    @Test
    public void testGetBallCount() {
        // Test getting the number of balls in the store
        assertEquals(4, hole.getBallCount());
    }

    @Test
    public void testTeam() {
        // Test getting the team of the store
        assertEquals(Team.NORTH, hole.getTeam());
    }

    @Test
    public void testId() {
        // Test getting the team ID of the store
        assertEquals(1, hole.getId());
    }

    @Test
    public void testAddBall() throws EmptyHole {
        // Test adding balls to the store
        hole.addBall(new Ball());
        assertEquals(5, hole.getBallCount());
    }

    @Test(expected = EmptyHole.class)
    public void testAddNullBall() throws EmptyHole {
        // Test adding null ball to the store
        hole.addBall(null);
    }
}
