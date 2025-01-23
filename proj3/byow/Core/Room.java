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
        for (int i = 1; i < width + 1; i++) {
            for (int j = 1; j < height + 1; j++) {
                world[topLeft.getX() + i - 1][topLeft.getY() - j + 1] = tile;
            }
        }
    }

    public Position getCentre() {
        int x = (topLeft.getX() + bottomRight.getX()) / 2;
        int y = (topLeft.getY() + bottomRight.getY()) / 2;
        return new Position(x, y);
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
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public Room(Position topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.bottomRight = new Position(topLeft.getX() + width, topLeft.getY() - height);
        this.width = width;
        this.height = height;
    }

}