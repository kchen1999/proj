package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Enemy {
    private Position p;
    private static final TETile TILE = Tileset.SAND;
    private static final TETile FLOOR = Tileset.FLOOR;

    public Position getPosition() {
        return p;
    }

    public void updatePosition(WorldState ws, Position p) {
        ws.setTile(this.p.getX(), this.p.getY(), FLOOR);
        this.p = p;
        ws.setTile(p.getX(), p.getY(), TILE);
    }


    public Enemy(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), TILE);
    }
}