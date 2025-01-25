package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    private static final TETile tile = Tileset.FLOOR;
    private Position topLeft;
    private Position bottomRight;
    private int width;
    private int height;

    public void draw(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[topLeft.getX() + i][topLeft.getY() - j] = tile;
            }
        }
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