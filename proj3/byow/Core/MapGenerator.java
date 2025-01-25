package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;


public class MapGenerator {
    private static final List<Room> rooms = new ArrayList<>();
    private static final Set<Path> paths = new TreeSet<>();
    private static TETile[][] world;
    private static int width;
    private static int height;

    private void addPath(Room room) {
        for (Room r: rooms) {
            paths.add(new Path(room, r));

        }
    }

    private boolean xCoordCornerOverlap(int newRoomCornerX, int existingRoomLeftX, int existingRoomRightX) {
        return (newRoomCornerX >= existingRoomLeftX && newRoomCornerX <= existingRoomRightX)
                || existingRoomLeftX - newRoomCornerX == 1 || newRoomCornerX - existingRoomRightX == 1;
    }

    private boolean xCoordSideOverlap(int newRoomLeftSideX, int newRoomRightSideX, int existingRoomLeftSideX, int existingRoomRightSideX) {
        return newRoomLeftSideX <= existingRoomLeftSideX && newRoomRightSideX >= existingRoomRightSideX;
    }

    private boolean yCoordCornerOverlap(int newRoomCornerY, int existingRoomTopY, int existingRoomBottomY) {
        return (newRoomCornerY <= existingRoomTopY && newRoomCornerY >= existingRoomBottomY)
                || existingRoomBottomY - newRoomCornerY == 1 || newRoomCornerY - existingRoomTopY == 1;
    }

    private boolean yCoordSideOverlap(int newRoomTopSideY, int newRoomBottomSideY, int existingRoomTopSideY, int existingRoomBottomSideY) {
        return newRoomTopSideY >= existingRoomTopSideY && newRoomBottomSideY <= existingRoomBottomSideY;
    }

    public boolean isOverlap(Room room) {
        Position topLeft = room.getTopLeft();
        Position topRight = new Position(topLeft.getX() + room.getWidth() - 1, topLeft.getY());
        Position bottomLeft = new Position(topLeft.getX(), topLeft.getY() - room.getHeight() + 1);
        Position bottomRight = room.getBottomRight();
        for (Room r : rooms) {
            if(xCoordCornerOverlap(topLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                && yCoordCornerOverlap(topLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordCornerOverlap(topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(topRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordCornerOverlap(bottomLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordCornerOverlap(bottomRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordSideOverlap(topLeft.getX(), topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(topLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordSideOverlap(bottomLeft.getX(), bottomRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())
                    && xCoordCornerOverlap(topLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())) {
                return true;
            }
            if(yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())
                    && xCoordCornerOverlap(bottomRight.getX(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if(xCoordSideOverlap(topLeft.getX(), topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
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
        int n = (mapWidth / ((RoomGenerator.getRoomMaxWidth() + 1) / 2 + 1) *
                mapHeight / (RoomGenerator.getRoomMaxHeight() + 1) / 2 + 1);
        return random.nextInt((int) (0.6 * n)) + n;
    }

    public int randomMapWidth(Random random, int mapLeftOffset) {
        int mapWidth = random.nextInt((int) (0.7 * width)) + (int) (0.3 * width);
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
        int mapLeftOffset = randomMapLeftOffset(random);
        int mapTopOffset = randomMapTopOffset(random);
        int mapBottomOffset = randomMapBottomOffset(random);
        int mapWidth = randomMapWidth(random, mapLeftOffset);
        int mapHeight = height - mapTopOffset - mapBottomOffset;

        RoomGenerator rg = new RoomGenerator(random, mapWidth, mapHeight, mapLeftOffset, mapTopOffset);
        int numOfRooms = randomNumOfRooms(random, mapWidth, mapHeight);
        for (int i = 0; i < numOfRooms; i++) {
            while(true) {
                Room room = rg.generate();
                if (!isOverlap(room)) {
                    room.draw(world);
                    addPath(room);
                    rooms.add(room);
                    break;
                }
            }
        }
        HallWay.generate(world, paths, numOfRooms);

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);

        // initialize tiles
        MapGenerator mg = new MapGenerator(341, 80, 30);

        //Room r1 = new Room(new Position(0, 2), 4, 3);
        //r1.draw(world);
        // draws the world to the screen
        ter.renderFrame(world);
    }
}