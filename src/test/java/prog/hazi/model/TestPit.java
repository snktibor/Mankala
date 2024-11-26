package prog.hazi.model;

import static org.junit.Assert.*;
import org.junit.*;

public class TestPit {
    private Pit pit;

    @Before
    public void setUp() {
        // Initialize the pit with the NORTH team
        pit = new Pit(Team.NORTH, 4, 1);
    }

    @Test
    public void testPitNotEmpty() {
        // Test getting the number of balls in the pit
        assertEquals(false, pit.isPitEmpty());
    }

    @Test
    public void testRemoveBalls() {
        // Test removing balls from the pit
        assertEquals(4, pit.removeBalls().size());
    }

    @Test
    public void testPitEmpty() {
        // Test if the pit is empty
        pit.removeBalls();
        assertEquals(true, pit.isPitEmpty());
    }
}
