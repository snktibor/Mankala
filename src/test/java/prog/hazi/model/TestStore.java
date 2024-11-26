package prog.hazi.model;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.List;

public class TestStore {
    private Store store;

    @Before
    public void setUp() {
        // Initialize the store with the NORTH team
        store = new Store(Team.NORTH);
        store.addBall(List.of(new Ball(), new Ball(), new Ball()));
    }

    @Test
    public void testAddBall() {
        // Test adding balls to the store
        store.addBall(List.of(new Ball(), new Ball(), new Ball()));
        assertEquals(6, store.getBallCount());
    }
}
