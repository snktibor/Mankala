# Model

## Class: `Board`

### Constructors

#### `Board(int bSize, int bCount)`
Constructs a Board object with the specified board size and ball count.
- **Parameters:**
  - `bSize`: the number of pits per player on the board
  - `bCount`: the initial number of balls in each pit

### Methods

#### `int getBoardSize()`
Returns the board size.
- **Returns:** the board size

#### `int getNumberOfBalls()`
Returns the initial number of balls the pits have.
- **Returns:** the ball count

#### `boolean sowing(int id, Team t) throws EmptyHole`
Performs the sowing (vetés) action starting from the specified pit for the given team.
- **Parameters:**
  - `id`: the ID of the pit to start sowing from
  - `t`: the team performing the action
- **Throws:** `EmptyHole` if the starting pit is empty
- **Returns:** `true` if the player can repeat the turn, `false` otherwise

#### `private int calculateHoleIndex(Team t, int tid)`
Calculates the index of a hole on the board based on the team and hole ID.
- **Parameters:**
  - `t`: the team for which the hole index is being calculated
  - `tid`: the ID of the hole within the team's side of the board
- **Returns:** the calculated index of the hole on the board

#### `public Hole getHole(Team t, int tid)`
Retrieves the hole for the specified team and hole index.
- **Parameters:**
  - `t`: the team for which the hole is to be retrieved
  - `tid`: the index of the hole within the team's side of the board
- **Returns:** the Hole object corresponding to the specified team and hole index

#### `public Team whichPitsEmpty()`
Determines which team's pits are empty.
- **Returns:** the team whose pits are empty, or null if neither team's pits are empty

#### `public void cleanPitsOfTeam(Team t)`
Cleans all pits of the specified team by transferring all balls from the pits to the team's store.
- **Parameters:**
  - `t`: the team whose pits are to be cleaned

#### `public Team getWinnerTeam()`
Determines the winning team based on the ball count in the store of each team.
- **Returns:** the winning team (Team.SOUTH or Team.NORTH) if the game is over, otherwise null

#### `public boolean isGameOver()`
Checks if the game is over.
- **Returns:** `true` if the game is over (i.e., one of the players' pits are empty), false otherwise

#### `public boolean isDraw()`
Determines if the game has ended in a draw.
- **Returns:** `true` if the game is over and both teams have the same number of balls in their respective starting holes, false otherwise

## Class: `Hole`

### Constructors

#### `Hole(Team t, int ballsCount, int tid)`
Constructs a `Hole` object.
- **Parameters:**
  - `t`: the team associated with the hole
  - `ballsCount`: the initial number of balls in the hole
  - `tid`: the team ID

### Methods

#### `int getBallCount()`
Returns the number of balls in the hole.
- **Returns:** the count of balls in the hole

#### `void addBall(Ball b) throws EmptyHole`
Adds a ball to the hole.
- **Parameters:**
  - `b`: the ball to be added
- **Throws:** `EmptyHole` if the ball is null

#### `Team getTeam()`
Returns the team associated with this hole.
- **Returns:** the team associated with this hole

#### `int getId()`
Returns the ID of the team associated with this hole.
- **Returns:** the team ID

## Class: `Pit`

### Constructors

#### `Pit(Team t, int ballsCount, int tid)`
Constructs a new `Pit` object.
- **Parameters:**
  - `t`: the team associated with this pit
  - `ballsCount`: the initial number of balls in the pit
  - `tid`: the team ID

### Methods

#### `Boolean isPitEmpty()`
Checks if the pit is empty.
- **Returns:** `true` if the pit is empty, `false` otherwise

#### `List<Ball> removeBalls()`
Removes all balls from the pit and returns them as a list.
- **Returns:** a list of balls that were in the pit before removal

## Class: `Store`

### Constructors

#### `Store(Team t)`
Constructs a new `Store` object.
- **Parameters:**
  - `t`: the team associated with the store

### Methods

#### `void addBall(List<Ball> b)`
Adds a list of balls to the store.
- **Parameters:**
  - `b`: the list of balls to be added


## Class: `Settings`

### Constructors

#### `Settings(int bSize, int bCount)`
Constructs a new `Settings` object with the specified board size and ball count.
- **Parameters:**
  - `bSize`: the size of the board
  - `bCount`: the number of balls

### Methods

#### `int getBoardSize()`
Returns the size of the board.
- **Returns:** the board size

#### `int getBallCount()`
Returns the current number of balls.
- **Returns:** the number of balls

#### `void setBoardSize(int bSize)`
Sets the size of the board.
- **Parameters:**
  - `bSize`: the size of the board to be set. Must be greater than 0.

#### `void setBallCount(int bCount)`
Sets the number of balls if the provided count is greater than zero.
- **Parameters:**
  - `bCount`: the number of balls to set, must be greater than zero

#### `void reset()`
Resets the game settings to default values.
- **Details:** Sets the board size to 6 and the ball count to 4.


## Enum: `Team`

### Enum Constants

#### `NORTH`
Represents the North team with ID 0 and color RED.

#### `SOUTH`
Represents the South team with ID 1 and color BLUE.

### Fields

- `public final int id`: The unique identifier for the team.
- `private String name`: The name of the team.
- `private Color color`: The color associated with the team.
- `private Color backGColor`: The background color associated with the team.

### Constructors

#### `private Team(int i, String n, Color c)`
Constructs a new `Team` with the specified id, name, and color.
- **Parameters:**
  - `i`: the unique identifier for the team
  - `n`: the name of the team
  - `c`: the color of the team

### Methods

#### `public Team oponentTeam()`
Returns the opponent team.
- **Returns:** the opponent team (`SOUTH` if the current team is `NORTH`, and `NORTH` if the current team is `SOUTH`)

#### `public String getName()`
Returns the name of the team.
- **Returns:** the name of the team

#### `public Color getColor()`
Returns the color associated with the team.
- **Returns:** the color of the team

#### `public Color getBgColor()`
Returns the background color associated with the team.
- **Returns:** the background color of the team

#### `public void setColor(Color c, Color bg)`
Sets the color of the team.
- **Parameters:**
  - `c`: the color to set
  - `bg`: the background color to set

#### `public static Team getTeam(int id)`
Returns the team corresponding to the given ID.
- **Parameters:**
  - `id`: the ID of the team
- **Returns:** the team (`NORTH` if the ID is 0, `SOUTH` if the ID is 1)

#### `public void setName(String n)`
Sets the name of the team.
- **Parameters:**
  - `n`: the new name of the team

# UI


## Class: `GameLabelPanel`

### Constructors

#### `GameLabelPanel(JLabel sLabel)`
Constructs a new `GameLabelPanel` with the specified status label.
- **Parameters:**
  - `sLabel`: the `JLabel` to be used as the status label

### Methods

#### `private void initializeUI()`
Initializes the UI components of the panel.
- **Details:**
  - Sets the layout to `FlowLayout`
  - Adds the status label to the panel
  - Sets the panel to be non-opaque


## Class: `GameUI`

### Constructors

#### `GameUI(Settings st)`
Constructs a new `GameUI` instance with the specified settings.
- **Parameters:**
  - `st`: the settings object containing the board size and ball count

#### `GameUI(int boardSize, int ballCount)`
Constructs a new `GameUI` with the specified board size and ball count.
- **Parameters:**
  - `boardSize`: the size of the board
  - `ballCount`: the number of balls

### Methods

#### `public void reinitialize()`
Reinitializes the game UI by calling the methods to initialize the UI components and update the game board.

#### `private void initializeUI()`
Initializes the UI components of the panel.
- **Details:**
  - Sets the layout to `BorderLayout`
  - Sets the background color based on the current player
  - Adds the table panel and board panel
  - Adds the status label and label panel
  - Updates the game board

#### `public void updateBoard()`
Updates the game board UI by removing all existing components and re-adding them in the correct layout. This method sets up the board with the current state of the game, including the store buttons and pits for both teams. It also updates the background color based on the current player.

#### `private void writeWinnerIfGameIsOver()`
Updates the status label to display the winner of the game if the game is over.
- **Details:**
  - If the game is a draw, sets the status label to "It's a draw!"
  - Otherwise, sets the status label to the name of the winning team followed by " wins!"

#### `private void addStoreButton(GridBagConstraints gbc, Team team, int gridx, int gridy, int gridheight)`
Adds a store button to the game UI.
- **Parameters:**
  - `gbc`: the GridBagConstraints used to layout the button
  - `team`: the team to which the store belongs
  - `gridx`: the x-coordinate of the button in the grid
  - `gridy`: the y-coordinate of the button in the grid
  - `gridheight`: the height of the button in grid cells

#### `private void addPits(GridBagConstraints gbc, Team team, int pitsY, int labelY, boolean reverse)`
Adds pits to the game board UI for a specified team.
- **Parameters:**
  - `gbc`: the GridBagConstraints used for layout management
  - `team`: the team for which the pits are being added (either SOUTH or NORTH)
  - `pitsY`: the y-coordinate for the pits in the grid layout
  - `labelY`: the y-coordinate for the labels in the grid layout
  - `reverse`: if true, pits are added in reverse order; otherwise, they are added in normal order

#### `private void setLabelStyle(JLabel label)`
Sets the style for the given JLabel.
- **Details:**
  - Sets the vertical alignment to center
  - Sets the font to "Alexandria" with bold style and size 15
  - Sets the foreground (text) color to black

#### `private JButton createHoleButton(int ballCount, Team t, int rows)`
Creates a button representing a hole with the specified number of balls.
- **Parameters:**
  - `ballCount`: the number of balls in the hole
  - `t`: the team associated with the hole
  - `rows`: the number of rows for arranging the balls
- **Returns:** the created JButton

#### `public void setBoard(Board board)`
Sets the board for the game.
- **Parameters:**
  - `board`: the `Board` object to be set

#### `public void setCurrentPlayer(Team t)`
Sets the current player to the specified team.
- **Parameters:**
  - `t`: the `Team` to set as the current player

#### `public Board getBoard()`
Retrieves the current game board.
- **Returns:** the current instance of the `Board`

#### `public Team getCurrentPlayer()`
Returns the current player.
- **Returns:** the current player as a `Team` object

### Inner Classes

#### `private class ButtonListener implements ActionListener`
Handles button click events for the game UI.
- **Constructor:**
  - `ButtonListener(int holeId, Team team)`: Constructs a new ButtonListener with the specified hole ID and team.
    - **Parameters:**
      - `holeId`: the ID of the hole
      - `team`: the team associated with the hole
- **Methods:**
  - `public void actionPerformed(ActionEvent e)`: Handles the button click event.
    - **Parameters:**
      - `e`: the ActionEvent object


## Class: `MancalaGUI`

### Constructors

#### `MancalaGUI()`
Constructs a new `MancalaGUI` instance and initializes the user interface.
- **Details:**
  - Initializes settings with default values
  - Reads settings from "settings.xml"
  - Calls `initializeUI()` to set up the user interface

### Methods

#### `private void initializeUI()`
Initializes the user interface for the Mancala game application.
- **Details:**
  - Sets the look and feel
  - Sets the window title, size, and default close operation
  - Adds a window listener to handle the window closing event
  - Creates the menu bar
  - Loads the game panel and adds it to the main frame

#### `private void createMenuBar()`
Creates the menu bar for the Mancala GUI.
- **Details:**
  - The "Game" menu includes:
    - "New Game": Starts a new game
  - The "Settings" menu includes:
    - "Set board": Opens a dialog to set the game board
    - "Set style": Opens a dialog to set the game style
    - "Save settings to file": Saves the current settings to a file named "settings.xml"
    - "Reset settings": Resets the settings to their default values
  - Sets the created menu bar to the JFrame

#### `private void setLookAndFeel()`
Sets the look and feel of the application to the system's default.

#### `private void gameStyleDialog()`
Opens a dialog to configure team names and colors.
- **Details:**
  - Allows the user to set the name, color, and background color for each team
  - Updates the game board with the new settings

#### `private void gameBoardDialog()`
Opens a dialog to set the board size and ball count.
- **Details:**
  - Allows the user to input the number of pits and seeds
  - Updates the settings with the new values

#### `private void startNewGame()`
Starts a new game by resetting the game panel.
- **Details:**
  - Removes the old game panel
  - Creates a new `GameUI` instance with the current settings
  - Adds the new game panel to the main frame
  - Revalidates and repaints the frame

#### `private void saveGame()`
Saves the current state of the game.
- **Details:**
  - Saves the current board configuration and the current player's turn

#### `private GameUI loadGame()`
Loads the game state by setting the board and current player from saved data.
- **Returns:** the loaded `GameUI` instance

#### `private void saveBoard()`
Saves the current state of the game board to a file named "board.ser".

#### `private Board loadBoard()`
Loads the game board from a file named "board.ser".
- **Returns:** the loaded `Board` object, or a new `Board` if loading fails

#### `private void saveCurrentPlayer()`
Saves the current player to a file named "currentPlayer.ser".

#### `private Team loadCurrentPlayer()`
Loads the current player from a file named "currentPlayer.ser".
- **Returns:** the current player as a `Team` object, or `Team.NORTH` if loading fails


## Class: `RoundedButton`

### Constructors

#### `RoundedButton(String text, Color fillColor, Color outherColor, Color borderColor, int borderThickness)`
Constructs a new `RoundedButton` with the specified text, fill color, outer color, border color, and border thickness.
- **Parameters:**
  - `text`: the text to be displayed on the button
  - `fillColor`: the color to fill the button
  - `outherColor`: the color behind the button (to cover any overflow)
  - `borderColor`: the color of the button's border
  - `borderThickness`: the thickness of the button's border

### Methods

#### `protected void paintComponent(Graphics g)`
Overrides the `paintComponent` method to customize the button's appearance.


## Class: `TablePanel`

### Constructors

#### `TablePanel(Color fillColor, double padding)`
Constructs a `TablePanel` with the specified fill color and padding.
- **Parameters:**
  - `fillColor`: the color used to fill the panel
  - `padding`: the padding percentage to be applied to the panel

### Methods

#### `protected void paintComponent(Graphics g)`
Overrides the `paintComponent` method to customize the panel's appearance.

#### `public Dimension getPreferredSize()`
Overrides the `getPreferredSize` method to calculate the preferred size of the panel based on its components.
- **Returns:** the preferred size of the panel


# Util


## Class: `SettingsHandler`

### Methods

#### `public static void writeSettings(Settings st, String filePath)`
Writes the settings to an XML file at the specified file path.
- **Parameters:**
  - `st`: the Settings object containing the settings to be written
  - `filePath`: the path of the file where the settings will be saved

#### `public static void readSettings(Settings st, String filePath)`
Reads settings from an XML file and applies them to the provided Settings object.
- **Parameters:**
  - `st`: the Settings object to apply the read settings to
  - `filePath`: the path to the XML file containing the settings

#### `public static void resetSettings(Settings st)`
Resets the settings to their default values.
- **Parameters:**
  - `st`: the Settings object to be reset
- **Details:**
  - This method resets the provided Settings object and sets the default names and colors for the NORTH and SOUTH teams. The NORTH team is set to "Red" with primary color (255, 68, 51) and secondary color (184, 62, 51). The SOUTH team is set to "Blue" with primary color (75, 127, 210) and secondary color (39, 84, 157).

#### `private static String colorToHex(Color color)`
Converts a Color object to its hexadecimal string representation.
- **Parameters:**
  - `color`: the Color object to be converted
- **Returns:** the hexadecimal string representation of the color in the format "#FFFFFF"

#### `private static Color hexToColor(String hex) throws NumberFormatException`
Converts a hexadecimal color string to a Color object.
- **Parameters:**
  - `hex`: the hexadecimal color string in the format "#FFFFFF"
- **Returns:** the Color object representing the specified color
- **Throws:** `NumberFormatException` if the hex string is not a valid hexadecimal color code