package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class WorldState {
    private static final int TILES_AHEAD = 5;
    private static final TETile PLAYER = Tileset.AVATAR;
    private static final TETile BLANK = Tileset.NOTHING;
    private static final TETile ENEMY = Tileset.SAND;
    private static final TETile ENEMY_PATH = Tileset.ENEMY_PATH;
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
        return worldTiles[x][y].getTile().equals(BLANK);
    }

    public int getTilesAhead() {
        return TILES_AHEAD;
    }

    public static Tile[][] getWorldTiles() {
        return worldTiles;
    }

    public static String getTile(int x, int y) {
        if (isWall(x, y)) {
            return "wall";
        } else if (isTile(x, y, Tileset.FLOOR)) {
            return "floor";
        } else if (isTile(x, y, PLAYER)) {
            return "player";
        } else {
            return "blank";
        }
    }

    private static void showEnemyPath(List<Tile> path) {
        int i = 0;
        for (Tile tile : path) {
            if (i == 0 || i == 1) {
                i++;
                continue;
            }
            if (!isTile(tile.getX(), tile.getY(), PLAYER) && !isTile(tile.getX(), tile.getY(), ENEMY)) {
                setTile(tile.getX(), tile.getY(), ENEMY_PATH);
            }
        }
    }

    private static void showWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (isTile(x, y, PLAYER) || isTile(x, y, ENEMY)) {
                    continue;
                }
                world[x][y] = worldTiles[x][y].getTile();
            }
        }
    }

    private static void hideWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (isTile(x, y, PLAYER) || isTile(x, y, ENEMY)) {
                    continue;
                }
                world[x][y] = BLANK;
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
        if (!isTile(x, y, PLAYER)) {
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

    public TETile[][] showEnemyPaths(List<Tile> path1, List<Tile> path2, Player user, boolean lineOfSight) {
        if (lineOfSight) {
            playerLineOfSight(user);
        } else {
            showWorld();
        }
        showEnemyPath(path1);
        showEnemyPath(path2);
        return world;
    }

    public List<Tile> getAdjTiles(int x, int y) {
        List<Tile> list = new ArrayList<>();
        if (!isOutOfBounds(x - 1, y) && isFloorTile(x - 1, y)) {
            list.add(worldTiles[x - 1][y]);
        }
        if (!isOutOfBounds(x + 1, y) && isFloorTile(x + 1, y)) {
            list.add(worldTiles[x + 1][y]);
        }
        if (!isOutOfBounds(x, y - 1) && isFloorTile(x, y - 1)) {
            list.add(worldTiles[x][y - 1]);
        }
        if (!isOutOfBounds(x, y + 1) && isFloorTile(x, y + 1)) {
            list.add(worldTiles[x][y + 1]);
        }
        return list;
    }

    public void setWorldTiles() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                worldTiles[x][y] = new Tile(world[x][y], x, y);
            }
        }
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                worldTiles[x][y].setAdj(getAdjTiles(x, y));
            }
        }
    }

    private static void initializeWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = BLANK;
                worldTiles[x][y] = new Tile(BLANK, x, y);
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
