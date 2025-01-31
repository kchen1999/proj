package byow.Core;

import byow.TileEngine.TETile;

public class Tile {
    private TETile tile;

    public TETile getTile() {
        return tile;
    }

    public Tile(TETile tile) {
        this.tile = tile;
    }
}