package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Set;
import java.util.HashSet;


public class HallWay {
    private static Set<Room> connectedRooms = new HashSet<>();
    private static Set<Room> unconnectedRooms = new HashSet<>();
    private static final TETile TILE = Tileset.FLOOR;
    private static final TETile WALL = Tileset.WALL;
    private static boolean unconnected(Room room) {
        return unconnectedRooms.contains(room);
    }
    private static boolean isConnected(Room room) {
        return connectedRooms.contains(room);
    }

    private static void drawVerticalHallWay(WorldState ws, Position bottom, Position top) {
        int i = bottom.getX();
        for (int j = bottom.getY() + 1; j < top.getY(); j++) {
            if (!ws.isFloorTile(i, j)) {
                ws.setTile(i, j, TILE);;
            }
            if (!ws.isFloorTile(i - 1, j)) {
                ws.setTile(i - 1, j, WALL);
            }
            if (!ws.isFloorTile(i + 1, j)) {
                ws.setTile(i + 1, j, WALL);
            }
        }
        if (!ws.isFloorTile(i - 1, bottom.getY())) {
            ws.setTile(i - 1, bottom.getY(), WALL);
        }
        if (!ws.isFloorTile(i - 1, top.getY())) {
            ws.setTile(i - 1, top.getY(), WALL);
        }
        if (!ws.isFloorTile(i + 1, bottom.getY())) {
            ws.setTile(i + 1, bottom.getY(), WALL);
        }
        if (!ws.isFloorTile(i + 1, top.getY())) {
            ws.setTile(i + 1, top.getY(), WALL);
        }

    }

    private static void drawHorizontalHallWayLtoR(WorldState ws, Position left, Position right) {
        int j = left.getY();
        for (int i = left.getX() + 1; i < right.getX(); i++) {
            if (!ws.isFloorTile(i, j)) {
                ws.setTile(i, j, TILE);;
            }
            if (!ws.isFloorTile(i + 1, j - 1)) {
                ws.setTile(i + 1, j - 1, WALL);
            }
            if (!ws.isFloorTile(i + 1, j + 1)) {
                ws.setTile(i + 1, j + 1, WALL);
            }
        }
        if (!ws.isFloorTile(left.getX(), j - 1)) {
            ws.setTile(left.getX(), j - 1, WALL);
        }
        if (!ws.isFloorTile(right.getX(), j - 1)) {
            ws.setTile(right.getX(), j - 1, WALL);
        }
        if (!ws.isFloorTile(left.getX(), j + 1)) {
            ws.setTile(left.getX(), j + 1, WALL);
        }
        if (!ws.isFloorTile(right.getX(), j + 1)) {
            ws.setTile(right.getX(), j + 1, WALL);
        }

    }

    private static void drawHorizontalHallWayRtoL(WorldState ws, Position right, Position left) {
        int j = right.getY();
        for (int i = right.getX() - 1; i >= left.getX(); i--) {
            if (!ws.isFloorTile(i, j)) {
                ws.setTile(i, j, TILE);;
            }
            if (!ws.isFloorTile(i, j - 1)) {
                ws.setTile(i, j - 1, WALL);
            }
            if (!ws.isFloorTile(i, j + 1)) {
                ws.setTile(i, j + 1, WALL);
            }
        }
        if (!ws.isFloorTile(left.getX() - 1, j - 1)) {
            ws.setTile(left.getX() - 1, j - 1, WALL);
        }
        if (!ws.isFloorTile(right.getX(), j - 1)) {
            ws.setTile(right.getX(), j - 1, WALL);
        }
        if (!ws.isFloorTile(left.getX() - 1, j + 1)) {
            ws.setTile(left.getX() - 1, j + 1, WALL);
        }
        if (!ws.isFloorTile(right.getX(), j + 1)) {
            ws.setTile(right.getX(), j + 1, WALL);
        }
    }

    private static void drawHallWay(WorldState ws, Room start, Room end) {
        Position startTopRight = new Position(start.getTopLeft().getX() + start.getWidth() - 1, start.getTopLeft().getY());
        Position endBottomLeft = new Position(end.getTopLeft().getX(), end.getBottomRight().getY());
        if (start.getTopLeft().getX() == end.getTopLeft().getX()) {
            drawVerticalHallWay(ws, start.getTopLeft(), end.getBottomRight());
        } else if (start.getTopLeft().getY() == end.getTopLeft().getY()) {
            drawHorizontalHallWayLtoR(ws, startTopRight, end.getTopLeft());
        } else if (start.getTopLeft().getY() < end.getTopLeft().getY()) {
            if (end.getBottomRight().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(ws, new Position(end.getBottomRight().getX(), start.getTopLeft().getY()), end.getBottomRight());
            } else if (startTopRight.getX() >= end.getTopLeft().getX()) {
                drawVerticalHallWay(ws, startTopRight, new Position(startTopRight.getX(), end.getBottomRight().getY()));
            } else {
                drawHorizontalHallWayLtoR(ws, startTopRight, new Position(end.getTopLeft().getX() + 1, start.getTopLeft().getY()));
                drawVerticalHallWay(ws, new Position(end.getTopLeft().getX(), start.getTopLeft().getY()), endBottomLeft);
            }
        } else if (start.getTopLeft().getY() > end.getTopLeft().getY()) {
            if (end.getBottomRight().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(ws, end.getTopLeft(), new Position(end.getTopLeft().getX(), start.getBottomRight().getY()));
            } else if (end.getTopLeft().getX() <= start.getBottomRight().getX()) {
                drawVerticalHallWay(ws, end.getTopLeft(), new Position(end.getTopLeft().getX(), start.getBottomRight().getY()));
            } else {
                drawHorizontalHallWayRtoL(ws, end.getTopLeft(), new Position(start.getBottomRight().getX(), end.getTopLeft().getY()));
                drawVerticalHallWay(ws, new Position(start.getBottomRight().getX(), end.getTopLeft().getY()), start.getBottomRight());
            }
        }
    }

    private static void union(WorldState ws, Path path) {
        drawHallWay(ws, path.getStart(), path.getEnd());
    }

    private static void resetHallway() {
        connectedRooms.clear();
        unconnectedRooms.clear();
    }

    public static void generate(WorldState ws, Set<Path> paths, int numOfRooms) {
        resetHallway();
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
                union(ws, path);
                connectedRooms.add(start);
                connectedRooms.add(end);
                continue;
            }
            if (isConnected(start)) {
                union(ws, path);
                connectedRooms.add(end);
            } else if (isConnected(end)) {
                union(ws, path);
                connectedRooms.add(start);
            } else {
                if (unconnected(start) && unconnected(end)) {
                    continue;
                } else if (!unconnected(start) && !unconnected(end)) {
                    union(ws, path);
                    unconnectedRooms.add(start);
                    unconnectedRooms.add(end);
                } else if (!unconnected(start)) {
                    union(ws, path);
                    unconnectedRooms.add(start);
                } else {
                    union(ws, path);
                    unconnectedRooms.add(end);
                }
            }
        }
    }
}
