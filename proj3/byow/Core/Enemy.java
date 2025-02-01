package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

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

    public void move(WorldState ws, Position playerPosition) {
        Tile[][] worldTiles = ws.getWorldTiles();
        List<Tile> list = ShortestPath.generate(worldTiles[p.getX()][p.getY()],
                worldTiles[playerPosition.getX()][playerPosition.getY()]);
        int x = list.get(1).getX();
        int y = list.get(1).getY();
        ws.setTile(x, y, TILE);
        ws.setTile(p.getX(), p.getY(), FLOOR);
        p = new Position(x, y);
    }


    public Enemy(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), TILE);
    }
}