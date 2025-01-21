package byow.Core;


import java.util.Random;

public class RoomGenerator {
    private static Random random;
    private static int mapWidth;
    private static int mapHeight;
    private static int mapLeftOffset;
    private static int mapTopOffset;
    private static final int ROOM_MAX_WIDTH = 6;
    private static final int ROOM_MAX_HEIGHT = 6;

    private static Position randomTopLeftPosition() {
        // 3 + 76
        int x = mapLeftOffset + random.nextInt(mapWidth);
        //1 + 0
        int y = mapTopOffset + random.nextInt(mapHeight);
        return new Position(x, y);
    }

    public static int getRoomMaxHeight() {
        return ROOM_MAX_HEIGHT;
    }

    public static int getRoomMaxWidth() {
        return ROOM_MAX_WIDTH;
    }

    public Room generate() {
        //mapLeftOffset = 9, mapTopOffset = 2, mapBottomOffset = 1, mapWidth = 53, mapHeight = 27
        // 0, 59 => 20, 79
        Position topLeft = randomTopLeftPosition();
        int randomWidth, randomHeight;
        //79-79
        if (mapLeftOffset + mapWidth - topLeft.getX() < ROOM_MAX_WIDTH) { //check other bound
            randomWidth = random.nextInt(mapLeftOffset + mapWidth - topLeft.getX()) + 1;
        } else {
            randomWidth = random.nextInt(ROOM_MAX_WIDTH) + 1;
        }
        //1-5
        if (topLeft.getY() < ROOM_MAX_HEIGHT) {
            randomHeight = random.nextInt(topLeft.getY()) + 1;
        } else { //1-6
            randomHeight = random.nextInt(ROOM_MAX_HEIGHT) + 1;
        }
        return new Room(topLeft, randomWidth, randomHeight);

    }

    public RoomGenerator(Random random, int mapWidth, int mapHeight, int mapLeftOffset, int mapTopOffset) {
        this.random = random;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapLeftOffset = mapLeftOffset;
        this.mapTopOffset = mapTopOffset;
    }

}