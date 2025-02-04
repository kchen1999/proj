package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Flower {
    private Position p;
    private static final TETile FLOWER = Tileset.FLOWER;

    public Flower(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), FLOWER);
    }

}