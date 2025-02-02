package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

public class Enemy {
    private Position p;
    private static final TETile PLAYER = Tileset.AVATAR;
    private static final TETile ENEMY = Tileset.SAND;
    private static final TETile FLOOR = Tileset.FLOOR;

    public Position getPosition() {
        return p;
    }

    public void updatePosition(WorldState ws, Position p) {
        ws.setTile(this.p.getX(), this.p.getY(), FLOOR);
        this.p = p;
        ws.setTile(p.getX(), p.getY(), ENEMY);
    }

    public void move(WorldState ws, Position playerPosition) {
        Tile[][] worldTiles = ws.getWorldTiles();
        List<Tile> list = ShortestPath.generate(worldTiles[p.getX()][p.getY()],
                worldTiles[playerPosition.getX()][playerPosition.getY()]);
        int x = list.get(1).getX();
        int y = list.get(1).getY();
        if (ws.isTile(x, y, PLAYER) || ws.isTile(x, y, ENEMY)) {
            System.out.println("Hi");
        } else {
            ws.setTile(x, y, ENEMY);
            ws.setTile(p.getX(), p.getY(), FLOOR);
            p = new Position(x, y);
        }
    }


    public Enemy(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), ENEMY);
    }
}