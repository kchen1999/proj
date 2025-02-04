package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private Position p;
    private static final TETile PLAYER = Tileset.AVATAR;
    private static final TETile ENEMY = Tileset.SAND;
    private static final TETile FLOOR = Tileset.FLOOR;
    private List<Tile> path = new ArrayList<>();

    public Position getPosition() {
        return p;
    }

    public List<Tile> getPath() {
        return path;
    }

    public void updatePosition(WorldState ws, Position p) {
        ws.setTile(this.p.getX(), this.p.getY(), FLOOR);
        this.p = p;
        ws.setTile(p.getX(), p.getY(), ENEMY);
    }

    public void move(WorldState ws, Position playerPosition) {
        Tile[][] worldTiles = ws.getWorldTiles();
        path = ShortestPath.generate(worldTiles[p.getX()][p.getY()],
                worldTiles[playerPosition.getX()][playerPosition.getY()]);
        System.out.println("Enemy position: x: " + p.getX() +  "y: " + p.getY());
        System.out.println("Player position: x: " + playerPosition.getX() + " y: " + playerPosition.getY());
        for (Tile t : path) {
            System.out.println("Tile position: x: " + t.getX() + " y: " + t.getY());
        }
        System.out.println("Shortest path complete");
        int x = path.get(1).getX();
        int y = path.get(1).getY();
        if (!ws.isTile(x, y, PLAYER) && !ws.isTile(x, y, ENEMY)) {
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