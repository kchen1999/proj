package byow.Core;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

public class MapGenerator {
    private static final TreeSet<Room> ROOMS = new TreeSet<>();
    private static final Set<Path> PATHS = new TreeSet<>();
    private static final List<Position> enemyPositions = new ArrayList<>();
    private static final int numOfEnemies = 2;
    private static int width;
    private static int height;
    private static Position playerPosition;
    private static Position flowerPosition;
    private static Random random;

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Position getFlowerPosition() {
        return flowerPosition;
    }

    public List<Position> getEnemyPositions() {
        return enemyPositions;
    }

    private void addPath(Room room) {
        for (Room r: ROOMS) {
            PATHS.add(new Path(room, r));
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
        for (Room r : ROOMS) {
            if (xCoordCornerOverlap(topLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                && yCoordCornerOverlap(topLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordCornerOverlap(topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(topRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordCornerOverlap(bottomLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordCornerOverlap(bottomRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordSideOverlap(topLeft.getX(), topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(topLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordSideOverlap(bottomLeft.getX(), bottomRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordCornerOverlap(bottomRight.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())
                    && xCoordCornerOverlap(topLeft.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())) {
                return true;
            }
            if (yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())
                    && xCoordCornerOverlap(bottomRight.getX(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
            if (xCoordSideOverlap(topLeft.getX(), topRight.getX(), r.getTopLeft().getX(), r.getBottomRight().getX())
                    && yCoordSideOverlap(topLeft.getY(), bottomLeft.getY(), r.getTopLeft().getY(), r.getBottomRight().getY())) {
                return true;
            }
        }
        return false;
    }

    private void resetMap() {
        ROOMS.clear();
        PATHS.clear();
        enemyPositions.clear();
    }

    public int randomMapLeftOffset() {
        return random.nextInt((int) (0.25 * width) - 1) + 1;
    }

    public int randomMapTopOffset() {
        return random.nextInt((int) (0.2 * height) - 1) + 1;
    }

    public int randomMapBottomOffset() {
        return random.nextInt((int) (0.1 * height) - 1) + 1;
    }

    public int randomNumOfRooms(int mapWidth, int mapHeight) {
        int n = (mapWidth / ((RoomGenerator.getRoomMaxWidth() + 1) / 2 + 1)
                * mapHeight / (RoomGenerator.getRoomMaxHeight() + 1) / 2 + 1);
        return random.nextInt((int) (0.5 * n)) + n + 1;
    }

    public int randomMapWidth(int mapLeftOffset) {
        int mapWidth = random.nextInt((int) (0.65 * width)) + (int) (0.35 * width);
        if (mapWidth + mapLeftOffset > width) {
            mapWidth = width - mapLeftOffset;
        }
        return mapWidth;
    }

    private void generatePlayerPosition() {
        for (Room room : ROOMS) {
            playerPosition = room.generateRandomPosition(random);
            break;
        }
    }

    private void generateEnemyPositions(int j) {
        int i = 0;
        for (Room room : ROOMS.descendingSet()) {
            if (i == ROOMS.size() / 2 + 1) {
                break;
            }
            if (i == 0 || i == ROOMS.size() / 2) {
                enemyPositions.add(room.generateRandomPosition(random));
            }
            i += 1;
        }
    }

    private void generateFlowerPosition() {
        for (Room room : ROOMS.descendingSet()) {
            flowerPosition = room.generateRandomPosition(random);
            break;
        }
    }

    public WorldState generate() {
        int mapLeftOffset = randomMapLeftOffset();
        int mapTopOffset = randomMapTopOffset();
        int mapBottomOffset = randomMapBottomOffset();
        int mapWidth = randomMapWidth(mapLeftOffset);
        int mapHeight = height - mapTopOffset - mapBottomOffset;
        WorldState ws = new WorldState(width, height);
        RoomGenerator rg = new RoomGenerator(random, mapWidth, mapHeight, mapLeftOffset, mapTopOffset);
        int numOfRooms = randomNumOfRooms(mapWidth, mapHeight);
        for (int i = 0; i < numOfRooms; i++) {
            while (true) {
                Room room = rg.generate();
                if (!isOverlap(room)) {
                    room.draw(ws);
                    addPath(room);
                    ROOMS.add(room);
                    break;
                }
            }
        }
        generateFlowerPosition();
        generatePlayerPosition();
        generateEnemyPositions(numOfEnemies);
        HallWay.generate(ws, PATHS, numOfRooms);
        return ws;
    }

    public MapGenerator(long seed, int width, int height) {
        this.random = new Random(seed);
        this.width = width;
        this.height = height;
        resetMap();
    }
}
