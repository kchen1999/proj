package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;


public class Engine {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int KEYBOARD = 0;
    private static final int STRING = 1;
    private static final int HUD_TOP_OFFSET = 10;
    private String seed;
    private WorldState ws;
    private Player user;
    private Enemy enemy1;
    private Enemy enemy2;
    private TERenderer ter;
    private Display display;
    private boolean lineOfSight = false;
    private int moves = 0;

    public static int getHudTopOffset() {
        return HUD_TOP_OFFSET;
    }

    private void refreshMap() {
        if (lineOfSight) {
            ter.renderFrame(ws.playerLineOfSight(user));
        } else {
            ter.renderFrame(ws.terrainGrid());
        }
        display.showHeadsUpDisplay(ws, HUD_TOP_OFFSET);
    }

    private void generateMap() {
        MapGenerator mg = new MapGenerator(Long.parseLong(seed), WIDTH, HEIGHT);
        ws = mg.generate();
        ws.setWorldTiles();
        user = new Player(ws, mg.getPlayerPosition());
        enemy1 = new Enemy(ws, mg.getEnemyPositions().get(0));
        enemy2 = new Enemy(ws, mg.getEnemyPositions().get(1));
        ter.renderFrame(ws.terrainGrid());
    }

    private void playGame(char c) {
        boolean validMove = user.move(ws, c);
        if (validMove) {
            enemy1.move(ws, user.getPosition());
            enemy2.move(ws, user.getPosition());
            moves += 1;
        }
        if (c == 'E') {
            ter.renderFrame(ws.showEnemyPaths(enemy1.getPath(), enemy2.getPath(), user, lineOfSight));
            StdDraw.pause(500);
        }
    }

    private void loadWorldState() {
        seed = GameSaver.readSeed();
        Position savedPlayerPosition = GameSaver.readPlayerPosition();
        Position savedEnemy1Position = GameSaver.readEnemy1Position();
        Position savedEnemy2Position = GameSaver.readEnemy2Position();
        generateMap();
        user.updatePosition(ws, savedPlayerPosition);
        enemy1.updatePosition(ws, savedEnemy1Position);
        enemy2.updatePosition(ws, savedEnemy2Position);
    }

    private void initializeGame() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + HUD_TOP_OFFSET, 0, HUD_TOP_OFFSET / 2);
        display = new Display(WIDTH, HEIGHT);
        display.loadMainMenu();
    }

    private void toggleLineOfSight() {
        lineOfSight = !lineOfSight;
        moves = 0;
    }


    private void startGame(int inputType, String input) {
        InputSource inputSource;
        if (inputType == KEYBOARD) {
            inputSource = new KeyboardInputSource();
        } else {
            inputSource = new StringInputDevice(input);
        }
        initializeGame();
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
                    seed = seedBuilder.toString();
                    generateMap();
                    isLoaded = true;
                    GameSaver.resetSavedState();
                    break;
                }
            } else if (c == 'N') {
                if (!isLoaded) {
                    isSeedMode = true;
                    seedBuilder.setLength(0);
                    display.loadEnterRandomSeed(seedBuilder);
                }
            } else if (c == 'Q' && prevChar == ':') {
                GameSaver.save(seed, user.getPosition(), enemy1.getPosition(), enemy2.getPosition());
                if (inputType == KEYBOARD) {
                    System.exit(0);
                }
            } else if (c == 'L') {
                loadWorldState();
                isLoaded = true;
                break;
            }
            prevChar = c;
        }

        while (isLoaded) {
            refreshMap();
            StdDraw.pause(50);

            if (inputSource.possibleNextInput()) {
                char c = inputSource.getNextKey();
                if (c == 'Q' && prevChar == ':') {
                    GameSaver.save(seed, user.getPosition(), enemy1.getPosition(), enemy2.getPosition());
                    if (inputType == KEYBOARD) {
                        System.exit(0);
                    }
                } else {
                    if (c == 'Q') {
                        if (lineOfSight) {
                            if (moves > ws.getTilesAhead() * ws.getTilesAhead()) {
                                toggleLineOfSight();
                            }
                        } else {
                            toggleLineOfSight();
                        }
                    }
                    if (!lineOfSight && moves > ws.getTilesAhead()) {
                        toggleLineOfSight();
                    }
                    playGame(c);
                }
                prevChar = c;
            }
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

