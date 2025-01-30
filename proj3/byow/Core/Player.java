package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player {
    private Position p;
    private static final TETile tile = Tileset.AVATAR;
    private static final TETile floor = Tileset.FLOOR;

    public Position getPosition() {
        return p;
    }

    public void updatePosition(WorldState ws, Position p) {
        ws.setTile(this.p.getX(), this.p.getY(), floor);
        this.p = p;
        ws.setTile(p.getX(), p.getY(), tile);
    }

    public void move(WorldState ws, char dir) {
        if (dir == 'W') {
            if (!ws.isWall(p.getX(), p.getY() + 1)) {
                ws.setTile(p.getX(), p.getY() + 1, tile);
                ws.setTile(p.getX(), p.getY(), floor);
                p = new Position(p.getX(), p.getY() + 1);
            }
        } else if (dir == 'A') {
            if (!ws.isWall(p.getX() - 1, p.getY())) {
                ws.setTile(p.getX() - 1, p.getY(), tile);
                ws.setTile(p.getX(), p.getY(), floor);
                p = new Position(p.getX() - 1, p.getY());
            }
        } else if (dir == 'S') {
            if (!ws.isWall(p.getX(), p.getY() - 1)) {
                ws.setTile(p.getX(), p.getY() - 1, tile);
                ws.setTile(p.getX(), p.getY(), floor);
                p = new Position(p.getX(), p.getY() - 1);
            }
        } else if (dir == 'D') {
            if (!ws.isWall(p.getX() + 1, p.getY())) {
                ws.setTile(p.getX() + 1, p.getY(), tile);
                ws.setTile(p.getX(), p.getY(), floor);
                p = new Position(p.getX() + 1, p.getY());
            }
        }
    }

    public Player(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), tile);
    }
}