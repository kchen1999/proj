package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;


public class Engine {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int KEYBOARD = 0;
    private static final int STRING = 1;
    private String seed;
    private WorldState ws;
    private Player user;
    private TERenderer ter;
    private Display display;

    private void generateMap() {
        MapGenerator mg = new MapGenerator(Long.parseLong(seed), WIDTH, HEIGHT);
        ws = mg.generate();
        user = new Player(ws, mg.getPlayerPosition());
    }

    private void playGame(char c) {
        user.move(ws, c);
    }

    private void loadWorldState() {
        seed = GameSaver.readSeed();
        Position savedPlayerPosition = GameSaver.readPlayerPosition();
        generateMap();
        user.updatePosition(ws, savedPlayerPosition);
    }

    private void initializeGame() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + 10, 0, 5);
        display = new Display(WIDTH, HEIGHT + 10);
        display.loadMainMenu();
    }


    private void startGame(int inputType, String input) {
        InputSource inputSource;
        if (inputType == KEYBOARD) {
            inputSource = new KeyboardInputSource();
            initializeGame();
        } else {
            inputSource = new StringInputDevice(input);
        }
        StringBuilder seedBuilder = new StringBuilder();
        boolean isSeedMode = false;
        boolean isLoaded = false;
        char prevChar = 0;

        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (isSeedMode) {
                if (Character.isDigit(c)) {
                    seedBuilder.append(c);
                    display.loadEnterRandomSeed(seedBuilder);
                }
                if (c == 'S') {
                    isSeedMode = false;
                    seed = seedBuilder.toString();
                    generateMap();
                    isLoaded = true;
                    GameSaver.resetSavedState();
                }
            } else if (c == 'N') {
                if (!isLoaded) {
                    isSeedMode = true;
                    seedBuilder.setLength(0);
                    display.loadEnterRandomSeed(seedBuilder);
                }
            } else if (c == 'Q' && prevChar == ':') {
                GameSaver.save(seed, user.getPosition());
                break;
            } else if (c == 'L') {
                loadWorldState();
                isLoaded = true;
            } else {
                if (isLoaded) {
                    playGame(c);
                }
            }
            prevChar = c;
        }
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard()  {
        startGame(KEYBOARD, null);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        startGame(STRING, input);
        return ws.terrainGrid();
    }
}

