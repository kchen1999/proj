package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class WorldState {
    private static final int TILES_AHEAD = 5;
    private static int width;
    private static int height;
    private static TETile[][] world;
    private static Tile[][] worldTiles;

    public static void setTile(int x, int y, TETile tile) {
        world[x][y] = tile;
    }

    public static boolean isTile(int x, int y, TETile tile) {
        return world[x][y].equals(tile);
    }

    public static boolean isFloorTile(int x, int y) {
        return world[x][y].equals(Tileset.FLOOR)|| world[x][y].equals(Tileset.FLOOR0)
                || world[x][y].equals(Tileset.FLOOR1)|| world[x][y].equals(Tileset.FLOOR2)
                || world[x][y].equals(Tileset.FLOOR3)|| world[x][y].equals(Tileset.FLOOR4)
                || world[x][y].equals(Tileset.LIGHT);
    }

    public static boolean isWall(int x, int y) {
        return world[x][y].equals(Tileset.WALL);
    }

    public static boolean isBlank(int x, int y) {
        return worldTiles[x][y].getTile().equals(Tileset.NOTHING);
    }

    public int getTilesAhead() {
        return TILES_AHEAD;
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

    private static void showWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (isTile(x, y, Tileset.AVATAR)) {
                    continue;
                }
                world[x][y] = worldTiles[x][y].getTile();
            }
        }
    }

    private static void hideWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (isTile(x, y, Tileset.AVATAR)) {
                    continue;
                }
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public TETile[][] terrainGrid() {
        showWorld();
        return world;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height || isBlank(x, y);
    }

    private void generateLineOfSight(int x, int y, int tilesAhead) {
        if (isOutOfBounds(x, y) || tilesAhead < 0) {
            return;
        }
        if (!isTile(x, y, Tileset.AVATAR)) {
            setTile(x, y, worldTiles[x][y].getTile());
        }
        generateLineOfSight(x, y + 1, tilesAhead - 1);
        generateLineOfSight(x, y - 1, tilesAhead - 1);
        generateLineOfSight(x - 1, y, tilesAhead - 1);
        generateLineOfSight(x + 1, y, tilesAhead - 1);
    }

    public TETile[][] playerLineOfSight(Player user) {
        hideWorld();
        generateLineOfSight(user.getPosition().getX(), user.getPosition().getY(), TILES_AHEAD);
        return world;
    }

    public void setWorldTiles() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                worldTiles[x][y] = new Tile(world[x][y]);
            }
        }
    }

    private static void initializeWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
                worldTiles[x][y] = new Tile(Tileset.NOTHING);
            }
        }
    }

    public WorldState(int width, int height) {
        this.width = width;
        this.height = height;
        world = new TETile[width][height];
        worldTiles = new Tile[width][height];
        initializeWorld();
    }
}
