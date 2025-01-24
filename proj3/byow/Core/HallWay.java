package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Set;
import java.util.HashSet;

public class HallWay {
    private static Set<Room> connectedRooms = new HashSet<>();
    private static final TETile tile = Tileset.WATER;

    private static boolean isConnected(Room room) {
        return connectedRooms.contains(room);
    }

    private static void drawVerticalHallWay(TETile[][] world, Position bottom, Position top) {
        int i = bottom.getX();
        for (int j = bottom.getY() + 1; j < top.getY(); j++) {
            world[i][j] = tile;
        }
    }

    private static void drawHorizontalHallWayLtoR(TETile[][] world, Position left, Position right) {
        int j = left.getY();
        for (int i = left.getX() + 1; i < right.getX(); i++) {
            world[i][j] = tile;
        }
    }

    private static void drawHorizontalHallWayRtoL(TETile[][] world, Position right, Position left) {
        int j = right.getY();
        for (int i = right.getX() - 1; i >= left.getX(); i--) {
            world[i][j] = tile;
        }
    }

    private static void drawHallWay(TETile[][] world, Room start, Room end) {
        if (start.getTopLeft().getX() == end.getTopLeft().getX()) {
            drawVerticalHallWay(world, start.getTopLeft(), end.getBottomRight());
        } else if (start.getTopLeft().getY() == end.getTopLeft().getY()) {
            drawHorizontalHallWayLtoR(world, new Position(start.getTopLeft().getX() + start.getWidth() - 1, start.getTopLeft().getY()),
                    end.getTopLeft());
        }  else if (start.getTopLeft().getY() < end.getTopLeft().getY()) {
            drawHorizontalHallWayLtoR(world, new Position(start.getTopLeft().getX() + start.getWidth() - 1, start.getTopLeft().getY()),
                    new Position(end.getTopLeft().getX() + 1, start.getTopLeft().getY()));
            drawVerticalHallWay(world, new Position(end.getTopLeft().getX(), start.getTopLeft().getY()),
                    new Position(end.getTopLeft().getX(), end.getBottomRight().getY()));
        }   else if(start.getTopLeft().getY() > end.getTopLeft().getY()) {
            drawHorizontalHallWayRtoL(world, new Position(end.getTopLeft().getX(), end.getTopLeft().getY()),
                    new Position(start.getBottomRight().getX(), end.getTopLeft().getY()));
            drawVerticalHallWay(world, new Position(start.getBottomRight().getX(), end.getTopLeft().getY()),
                    start.getBottomRight());
        }

    }

    private static void union(TETile[][] world, Path path) {
        drawHallWay(world, path.getStart(), path.getEnd());
    }

    public static void generate(TETile[][] world, Set<Path> paths, int numOfRooms) {
        for (Path path : paths) {
            if (connectedRooms.size() == numOfRooms) {
                return;
            }
            if (connectedRooms.size() == 0) {
                union(world, path);
                connectedRooms.add(path.getStart());
                connectedRooms.add(path.getEnd());
            }
            if (!isConnected(path.getStart()) || !isConnected(path.getEnd())) {
                union(world, path);
                if (isConnected(path.getStart())) {
                    connectedRooms.add(path.getEnd());
                } else if (isConnected(path.getEnd())) {
                    connectedRooms.add(path.getStart());
                }
            }

        }

    }

}