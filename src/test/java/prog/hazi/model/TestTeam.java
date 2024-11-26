package prog.hazi.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.*;


public class TestTeam {
    private Team team;

    @Before
    public void setUp() {
        // Initialize the team with the NORTH team
        team = Team.NORTH;
    }

    @Test
    public void testTeam() {
        // Test getting the team
        assertEquals(Team.NORTH, team);
    }

    @Test
    public void testOppositeTeam() {
        // Test getting the opposite team
        assertEquals(Team.SOUTH, team.oponentTeam());
    }

    @Test
    public void testOppositeTeamSouth() {
        // Test getting the opposite team
        team = Team.SOUTH;
        assertEquals(Team.NORTH, team.oponentTeam());
    }

    @Test
    public void testGetName() {
        // Test getting the name of the team
        assertEquals("North", team.getName());
    }

    @Test
    public void testGetNameSouth() {
        // Test getting the name of the team
        team = Team.SOUTH;
        assertEquals("South", team.getName());
    }

    @Test
    public void testGetColor() {
        // Test getting the color of the team
        assertEquals(Color.RED, team.getColor());
    }

    @Test
    public void testGetBgColor() {
        // Test getting the background color of the team
        assertEquals(Color.RED, team.getBgColor());
    }

    @Test
    public void testSetColor() {
        // Test getting the ID of the team
        team.setColor(Color.BLACK, Color.WHITE);
        assertEquals(Color.WHITE, team.getBgColor());
        assertEquals(Color.BLACK, team.getColor());
    }

    @Test
    public void testGetTeam() {
        // Test getting the ID of the team
        assertEquals(Team.NORTH, Team.getTeam(0));
    }

    @Test
    public void testGetTeamSouth() {
        // Test getting the ID of the team
        assertEquals(Team.SOUTH, Team.getTeam(1));
    }

    @Test
    public void testSetName() {
        // Test setting the name of the team
        team.setName("Test");
        assertEquals("Test", team.getName());
    }
}
