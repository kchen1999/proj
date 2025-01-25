package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    private static final TETile tile = Tileset.FLOOR;
    private static final TETile wall = Tileset.WALL;
    private Position topLeft;
    private Position bottomRight;
    private int width;
    private int height;

    private void drawWalls(TETile[][] world) {
        for (int j = -1; j < height + 1; j++) {
            world[topLeft.getX() - 1][topLeft.getY() - j] = wall;
        }
        for (int j = -1; j < height + 1; j++) {
            world[topLeft.getX() + width][topLeft.getY() - j] = wall;
        }
        for (int i = -1; i < width + 1; i++) {
            world[topLeft.getX() + i][topLeft.getY() + 1] = wall;
        }
        for (int i = -1; i < width + 1; i++) {
            world[topLeft.getX() + i][topLeft.getY() - height] = wall;
        }
    }

    public void draw(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[topLeft.getX() + i][topLeft.getY() - j] = tile;
            }
        }
        drawWalls(world);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void printRoom() {
        System.out.println("Room's top left is at x:" + topLeft.getX() + " y:" + topLeft.getY() + ".");
        System.out.println("With width " + width + " height " + height);
        System.out.println("Room's bottom right is at x:" + bottomRight.getX() + " y:" + bottomRight.getY() + ".");
        System.out.println("");
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public Room(Position topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.bottomRight = new Position(topLeft.getX() + width - 1, topLeft.getY() - height + 1);
        this.width = width;
        this.height = height;
    }

}