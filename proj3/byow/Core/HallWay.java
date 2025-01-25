package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Set;
import java.util.HashSet;

public class HallWay {
    private static Set<Room> connectedRooms = new HashSet<>();
    private static Set<Room> unconnectedRooms = new HashSet<>();
    private static final TETile tile = Tileset.FLOOR;
    private static final TETile wall = Tileset.WALL;

    private static boolean unconnected(Room room) {
        return unconnectedRooms.contains(room);
    }

    private static boolean isConnected(Room room) {
        return connectedRooms.contains(room);
    }

    private static void drawVerticalHallWay(TETile[][] world, Position bottom, Position top) {
        int i = bottom.getX();
        for (int j = bottom.getY() + 1; j < top.getY(); j++) {
            world[i][j] = tile;
            if (!world[i - 1][j].equals(tile)) {
                world[i - 1][j] = wall;
            }
            if (!world[i + 1][j].equals(tile)) {
                world[i + 1][j] = wall;
            }
        }
        if (!world[i - 1][bottom.getY()].equals(tile)) {
            world[i - 1][bottom.getY()] = wall;
        }
        if (!world[i - 1][top.getY()].equals(tile)) {
            world[i - 1][top.getY()] = wall;
        }
        if (!world[i + 1][bottom.getY()].equals(tile)) {
            world[i + 1][bottom.getY()] = wall;
        }
        if (!world[i + 1][top.getY()].equals(tile)) {
            world[i + 1][top.getY()] = wall;
        }

    }

    private static void drawHorizontalHallWayLtoR(TETile[][] world, Position left, Position right) {
        int j = left.getY();
        for (int i = left.getX() + 1; i < right.getX(); i++) {
            world[i][j] = tile;
            if (!world[i][j - 1].equals(tile)) {
                world[i][j - 1] = wall;
            }
            if (!world[i][j + 1].equals(tile)) {
                world[i][j + 1] = wall;
            }
        }
        if (!world[left.getX()][j - 1].equals(tile)) {
            world[left.getX()][j - 1] = wall;
        }
        if (!world[right.getX()][j - 1].equals(tile)) {
            world[right.getX()][j - 1] = wall;
        }
        if (!world[left.getX()][j + 1].equals(tile)) {
            world[left.getX()][j + 1] = wall;
        }
        if (!world[right.getX()][j + 1].equals(tile)) {
            world[right.getX()][j + 1] = wall;
        }

    }

    private static void drawHorizontalHallWayRtoL(TETile[][] world, Position right, Position left) {
        int j = right.getY();
        for (int i = right.getX() - 1; i >= left.getX(); i--) {
            world[i][j] = tile;
            if (!world[i][j - 1].equals(Tileset.FLOOR)) {
                world[i][j - 1] = wall;
            }
            if (!world[i][j + 1].equals(Tileset.FLOOR)) {
                world[i][j + 1] = wall;
            }
        }
        if (!world[left.getX() - 1][j - 1].equals(Tileset.FLOOR)) {
            world[left.getX() - 1][j - 1] = wall;
        }
        if (!world[right.getX()][j - 1].equals(Tileset.FLOOR)) {
            world[right.getX()][j - 1] = wall;
        }
        if (!world[left.getX() - 1][j + 1].equals(Tileset.FLOOR)) {
            world[left.getX() - 1][j + 1] = wall;
        }
        if (!world[right.getX()][j + 1].equals(Tileset.FLOOR)) {
            world[right.getX()][j + 1] = wall;
        }
    }

    private static void drawHallWay(TETile[][] world, Room start, Room end) {
        Position startTopRight = new Position(start.getTopLeft().getX() + start.getWidth() - 1, start.getTopLeft().getY());
        Position endBottomLeft = new Position(end.getTopLeft().getX(), end.getBottomRight().getY());
        if (start.getTopLeft().getX() == end.getTopLeft().getX()) {
            drawVerticalHallWay(world, start.getTopLeft(), end.getBottomRight());
        } else if (start.getTopLeft().getY() == end.getTopLeft().getY()) {
            drawHorizontalHallWayLtoR(world, startTopRight, end.getTopLeft());
        } else if (start.getTopLeft().getY() < end.getTopLeft().getY()) {
            if (end.getBottomRight().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(world, new Position(end.getBottomRight().getX(), start.getTopLeft().getY()), end.getBottomRight());
            } else if (startTopRight.getX() >= end.getTopLeft().getX()) {
                drawVerticalHallWay(world, startTopRight, new Position(startTopRight.getX(), end.getBottomRight().getY()));
            } else {
                drawHorizontalHallWayLtoR(world, startTopRight, new Position(end.getTopLeft().getX() + 1, start.getTopLeft().getY()));
                drawVerticalHallWay(world, new Position(end.getTopLeft().getX(), start.getTopLeft().getY()), endBottomLeft);
            }
        } else if(start.getTopLeft().getY() > end.getTopLeft().getY()) {
            if (end.getBottomRight().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(world, end.getTopLeft(), new Position(end.getTopLeft().getX(), start.getBottomRight().getY()));
            } else if (end.getTopLeft().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(world, end.getTopLeft(), new Position(end.getTopLeft().getX(), start.getBottomRight().getY()));
            } else {
                drawHorizontalHallWayRtoL(world, end.getTopLeft(), new Position(start.getBottomRight().getX(), end.getTopLeft().getY()));
                drawVerticalHallWay(world, new Position(start.getBottomRight().getX(), end.getTopLeft().getY()), start.getBottomRight());
            }
        }
    }

    private static void union(TETile[][] world, Path path) {
        drawHallWay(world, path.getStart(), path.getEnd());
    }

    public static void generate(TETile[][] world, Set<Path> paths, int numOfRooms) {
        for (Path path : paths) {
            Room start = path.getStart();
            Room end = path.getEnd();
            if (connectedRooms.size() == numOfRooms) {
                return;
            }
            if (isConnected(start) && isConnected(end)) {
                continue;
            }
            if (connectedRooms.size() == 0) {
                union(world, path);
                connectedRooms.add(start);
                connectedRooms.add(end);
                continue;
            }
            if (isConnected(start)) {
                union(world, path);
                connectedRooms.add(end);
            } else if (isConnected(end)) {
                union(world, path);
                connectedRooms.add(start);
            } else {
                if (unconnected(start) && unconnected(end)) {
                    continue;
                } else if (!unconnected(start) && !unconnected(end)) {
                    union(world, path);
                    unconnectedRooms.add(start);
                    unconnectedRooms.add(end);
                } else if (!unconnected(start)) {
                    union(world, path);
                    unconnectedRooms.add(start);
                } else {
                    union(world, path);
                    unconnectedRooms.add(end);
                }
            }


        }

    }

}