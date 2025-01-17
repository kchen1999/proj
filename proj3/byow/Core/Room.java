package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    private static final TETile tile = Tileset.FLOOR;
    private Position topLeft;
    private Position bottomRight;

    private void draw(TETile[][] world, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[topLeft.getX() - i][topLeft.getY() + j] = tile;
            }
        }
    }

    public Room(TETile[][] world, Position topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.bottomRight = new Position(topLeft.getX() + width, topLeft.getY() - height);
        this.draw(world, width, height);
    }


}