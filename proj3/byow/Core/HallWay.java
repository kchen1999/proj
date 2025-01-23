package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Set;
import java.util.HashSet;

public class HallWay {
    private static Set<Room> connectedRooms = new HashSet<>();
    private static final TETile tile = Tileset.WATER;

    private boolean isConnected(Room room) {
        return connectedRooms.contains(room);
    }

    private static void drawVerticalHallWay(TETile[][] world, Position bottom, Position top) {
        int i = bottom.getX();
        for (int j = bottom.getY() + 1; j < top.getY(); j++) {
            world[i][j] = tile;
        }
    }

    private static void drawHorizontalHallWay(TETile[][] world, Position left, Position right) {
        int j = left.getY();
        for (int i = left.getX() + 1; i < right.getX(); i++) {
            world[i][j] = tile;
        }
    }

    private static void drawHallWay(TETile[][] world, Room start, Room end) {
        if (start.getTopLeft().getX() == end.getTopLeft().getX()) {
            drawVerticalHallWay(world, start.getTopLeft(), end.getBottomRight());
            connectedRooms.add(start);
            connectedRooms.add(end);
        } else if (start.getTopLeft().getY() == end.getTopLeft().getY()) {
              drawHorizontalHallWay(world, new Position(start.getTopLeft().getX() + start.getWidth() - 1,
                start.getTopLeft().getY()), end.getTopLeft());
           // connectedRooms.add(start);
           // connectedRooms.add(end);
        }

    }

    private static void union(TETile[][] world, Path path) {
        drawHallWay(world, path.getStart(), path.getEnd());
        //connectedRooms.add(path.getStart());
        //connectedRooms.add(path.getEnd());
    }

    public static void generate(TETile[][] world, Set<Path> paths, int numOfRooms) {
        for (Path path : paths) {
            if (connectedRooms.size() == numOfRooms) {
                return;
            }
            if (!connectedRooms.contains(path.getStart()) || !connectedRooms.contains(path.getEnd())) {
                union(world, path);
            }

        }

    }

}