package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Room implements Comparable<Room> {
    private static final TETile TILE = Tileset.FLOOR;
    private static final TETile WALL = Tileset.WALL;
    private Position topLeft;
    private Position bottomRight;
    private Position lightSource;
    private int width;
    private int height;
    private boolean isLightSource = false;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public void printRoom() {
        System.out.println("Room's top left is at x:" + topLeft.getX() + " y:" + topLeft.getY() + ".");
        System.out.println("With width " + width + " height " + height);
        System.out.println("Room's bottom right is at x:" + bottomRight.getX() + " y:" + bottomRight.getY() + ".");
        System.out.println("");
    }

    private void drawWalls(WorldState ws) {
        for (int j = -1; j < height + 1; j++) {
            ws.setTile(topLeft.getX() - 1, topLeft.getY() - j, WALL);
        }
        for (int j = -1; j < height + 1; j++) {
            ws.setTile(topLeft.getX() + width, topLeft.getY() - j, WALL);
        }
        for (int i = -1; i < width + 1; i++) {
            ws.setTile(topLeft.getX() + i, topLeft.getY() + 1, WALL);
        }
        for (int i = -1; i < width + 1; i++) {
            ws.setTile(topLeft.getX() + i, topLeft.getY() - height, WALL);
        }
    }

    public Position generateRandomPlayerPosition(Random random) {
        int i = random.nextInt(width);
        int j = random.nextInt(height);
        Position p = new Position(topLeft.getX() + i, topLeft.getY() - j);
        if (isLightSource) {
            if (p.equals(lightSource)) {
                if (p.equals(topLeft)) {
                    return bottomRight;
                }
                return topLeft;
            }
        }
        return p;
    }

    private int distanceFromLightSource(int x, int y) {
        return Math.max(Math.abs(x - lightSource.getX()), Math.abs(y - lightSource.getY()));
    }

    private void turnLightingOn(WorldState ws) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int x = topLeft.getX() + i;
                int y = topLeft.getY() - j;
                if (distanceFromLightSource(x, y) == 1) {
                    ws.setTile(x, y, Tileset.FLOOR1);
                } else if (distanceFromLightSource(x, y) == 2) {
                    ws.setTile(x, y, Tileset.FLOOR2);
                } else if (distanceFromLightSource(x, y) == 3) {
                    ws.setTile(x, y, Tileset.FLOOR3);
                } else if (distanceFromLightSource(x, y) >= 4) {
                    ws.setTile(x, y, Tileset.FLOOR4);
                }
            }
        }
    }

    public void draw(WorldState ws) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ws.setTile(topLeft.getX() + i, topLeft.getY() - j, TILE);
            }
        }
        if (isLightSource) {
            turnLightingOn(ws);
            ws.setTile(lightSource.getX(), lightSource.getY(), Tileset.LIGHT);
        }
        drawWalls(ws);
    }

    public void generateLightSource(Random random) {
        int i = random.nextInt(width);
        int j = random.nextInt(height);
        lightSource = new Position(topLeft.getX() + i, topLeft.getY() - j);
    }

    public Room(Position topLeft, int width, int height, Random random) {
        this.topLeft = topLeft;
        this.bottomRight = new Position(topLeft.getX() + width - 1, topLeft.getY() - height + 1);
        this.width = width;
        this.height = height;
        if (width > 1 && height > 1) {
            isLightSource = true;
            generateLightSource(random);
        }
    }

    @Override
    public int compareTo(Room r) {
        if (this.topLeft.getX() == r.getTopLeft().getX()) {
            return r.getTopLeft().getY() - this.topLeft.getY();
        }
        return this.topLeft.getX() - r.getTopLeft().getX();
    }
}
