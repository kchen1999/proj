package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class WorldState {
    private static TETile[][] world;
    private int leftOffset;
    private int topOffset;

    public static void setTile(int x, int y, TETile tile) {
        world[x][y] = tile;
    }

    public static boolean isTile(int x, int y, TETile tile) {
        return world[x][y].equals(tile);
    }

    public static boolean isWall(int x, int y) {
        return world[x][y].equals(Tileset.WALL);
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public static String getTile(int x, int y) {
        if (isWall(x, y)) {
            return "wall";
        } else if (isTile(x, y, Tileset.FLOOR)) {
            return "floor";
        } else if (isTile(x, y, Tileset.AVATAR)) {
            return "player";
        } else {
            return "blank";
        }
    }

    public TETile[][] terrainGrid() {
        return world;
    }

    private static void initializeWorld(int width, int height) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public WorldState(int width, int height) {
        world = new TETile[width][height];
        initializeWorld(width, height);
    }
}
