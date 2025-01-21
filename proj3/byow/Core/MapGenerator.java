package byow.Core;

/*
How it works in detail:
Step 1: Room Placement

Randomly place a set of rooms (rectangular or other shapes) on the grid.
Ensure that the rooms don’t overlap by checking positions or using a grid-based allocation strategy.
Assign random widths and heights to the rooms.

Step 2: Random Walk for Hallways

Start from one room (or multiple rooms if preferred).
Move in a random direction (up, down, left, right), and carve out the hallway tiles. At each step, you can
randomly change direction to create turns.
The random walk can be terminated after a certain length or when it reaches another room. Ensure that the path
ends in an intersection if needed (this can be enforced programmatically).

Step 3: Ensure Connectivity

If the random walk does not connect some rooms (due to the random nature of the path), additional random walks
or post-processing can be used to fill in the gaps, ensuring that every room is reachable.

Step 4: Randomization of Hallways

Vary the width of hallways (1 or 2 tiles).
Include intersections or multiple paths that branch off the main hallways to enhance the world’s complexity.

Step 5: Tile Representation

Convert the grid into a tile-based system with walls, floors, and unused spaces.
Use different tiles for walls and floors to ensure visual distinction.
The unused spaces (e.g., areas not connected to hallways) can be represented with a unique tile type.

Step 6: Post-Processing (Optional)

Use Cellular Automata for further refinement of the world layout (e.g., smoothing out walls, adding caves, etc.).
Introduce optional outdoor spaces or thematic features that fit within the generated grid.

 */

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MapGenerator {
    private static final List<Room> rooms = new ArrayList<>();
    private static TETile[][] world;
    private static int width;
    private static int height;

    public boolean isOverlap(Room r) {
        Position topLeft = r.getTopLeft();
        Position topRight = new Position(topLeft.getX() + r.getWidth(), topLeft.getY());
        Position bottomLeft = new Position(topLeft.getX(), topLeft.getY() - r.getHeight());
        Position bottomRight = r.getBottomRight();
        for (Room room : rooms) {
            if(topLeft.getX() >= room.getTopLeft().getX() && topLeft.getX() <= room.getBottomRight().getX()
                    && topLeft.getY() <= room.getTopLeft().getY() && topLeft.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(topRight.getX() >= room.getTopLeft().getX() && topRight.getX() <= room.getBottomRight().getX()
                    && topRight.getY() <= room.getTopLeft().getY() && topRight.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(bottomLeft.getX() >= room.getTopLeft().getX() && bottomLeft.getX() <= room.getBottomRight().getX()
                    && bottomLeft.getY() <= room.getTopLeft().getY() && bottomLeft.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(bottomRight.getX() >= room.getTopLeft().getX() && bottomRight.getX() <= room.getBottomRight().getX()
                    && bottomRight.getY() <= room.getTopLeft().getY() && bottomRight.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(topLeft.getX() < room.getTopLeft().getX() && topRight.getX() > room.getBottomRight().getX()
                    && topLeft.getY() == room.getBottomRight().getY()) {
                return true;
            }
            if(topLeft.getX() == room.getBottomRight().getX() && topLeft.getY() > room.getTopLeft().getY()
                    && bottomLeft.getY() < room.getBottomRight().getY()) {
                return true;
            }
            if(bottomLeft.getX() < room.getTopLeft().getX() && bottomRight.getX() > room.getBottomRight().getX()
                    && bottomLeft.getY() == room.getTopLeft().getY()) {
                return true;
            }
            if(bottomRight.getX() == room.getTopLeft().getX() && topRight.getY() > room.getTopLeft().getY()
                    && bottomRight.getY() < room.getBottomRight().getY()) {
                return true;
            }
        }
        return false;
    }

    private void initializeWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public int randomMapLeftOffset(Random random) {
        return random.nextInt((int) (0.25 * width) - 1) + 1;
    }

    public int randomMapTopOffset(Random random) {
        return random.nextInt((int) (0.2 * height) - 1) + 1;
    }

    public int randomMapBottomOffset(Random random) {
        return random.nextInt((int) (0.1 * height) - 1) + 1;
    }

    public int randomNumOfRooms(Random random, int mapWidth, int mapHeight) {
        int n = (int) (mapWidth / ((RoomGenerator.getRoomMaxWidth() + 1) / 2 + 3) *
                mapHeight / (RoomGenerator.getRoomMaxHeight() + 1) / 2 + 3);
        return random.nextInt((int) (0.2 * n)) + (int) (0.6 * n);
    }

    public int randomMapWidth(Random random, int mapLeftOffset) {
        // 60 20
        int mapWidth = random.nextInt((int) (0.75 * width)) + (int) (0.25 * width);
        // 79 + 3 > 80
        if (mapWidth + mapLeftOffset > width) {
            mapWidth = width - mapLeftOffset;
        }
        return mapWidth;
    }

    public MapGenerator(int seed, int width, int height) {
        Random random = new Random(seed);
        this.width = width;
        this.height = height;
        this.world = new TETile[width][height];
        initializeWorld();
        //1 - 19
        int mapLeftOffset = randomMapLeftOffset(random);
        //1 - 5
        int mapTopOffset = randomMapTopOffset(random);
        //1 - 2
        int mapbottomOffset = randomMapBottomOffset(random);
        int mapWidth = randomMapWidth(random, mapLeftOffset);
        //30 - 1 - 1
        int mapHeight = height - mapTopOffset - mapbottomOffset;

        RoomGenerator rg = new RoomGenerator(random, mapWidth, mapHeight, mapLeftOffset, mapTopOffset);
        int numOfRooms = randomNumOfRooms(random, mapWidth, mapHeight);
        for (int i = 0; i < numOfRooms; i++) {
            while(true) {
                Room room = rg.generate();
                if (!isOverlap(room)) {
                    room.draw(world);
                    rooms.add(room);
                    break;
                }
            }
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);

        // initialize tiles
        MapGenerator mg = new MapGenerator(314, 80, 30);

        //Room r1 = new Room(new Position(0, 2), 4, 3);
        //r1.draw(world);
        // draws the world to the screen
        ter.renderFrame(world);
    }
}