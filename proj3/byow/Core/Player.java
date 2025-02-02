package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player {
    private Position p;
    private static final TETile PLAYER = Tileset.AVATAR;
    private static final TETile FLOOR = Tileset.FLOOR;
    private static final TETile ENEMY = Tileset.SAND;

    public Position getPosition() {
        return p;
    }

    public void updatePosition(WorldState ws, Position p) {
        ws.setTile(this.p.getX(), this.p.getY(), FLOOR);
        this.p = p;
        ws.setTile(p.getX(), p.getY(), PLAYER);
    }

    public boolean move(WorldState ws, char dir) {
        if (dir == 'W') {
            if (!ws.isWall(p.getX(), p.getY() + 1) && !ws.isTile(p.getX(), p.getY() + 1, ENEMY)) {
                ws.setTile(p.getX(), p.getY() + 1, PLAYER);
                ws.setTile(p.getX(), p.getY(), FLOOR);
                p = new Position(p.getX(), p.getY() + 1);
                return true;
            }
        } else if (dir == 'A') {
            if (!ws.isWall(p.getX() - 1, p.getY()) && !ws.isTile(p.getX() - 1, p.getY(), ENEMY)) {
                ws.setTile(p.getX() - 1, p.getY(), PLAYER);
                ws.setTile(p.getX(), p.getY(), FLOOR);
                p = new Position(p.getX() - 1, p.getY());
                return true;
            }
        } else if (dir == 'S') {
            if (!ws.isWall(p.getX(), p.getY() - 1) && !ws.isTile(p.getX(), p.getY() - 1, ENEMY)) {
                ws.setTile(p.getX(), p.getY() - 1, PLAYER);
                ws.setTile(p.getX(), p.getY(), FLOOR);
                p = new Position(p.getX(), p.getY() - 1);
                return true;
            }
        } else if (dir == 'D') {
            if (!ws.isWall(p.getX() + 1, p.getY()) && !ws.isTile(p.getX() + 1, p.getY(), ENEMY)) {
                ws.setTile(p.getX() + 1, p.getY(), PLAYER);
                ws.setTile(p.getX(), p.getY(), FLOOR);
                p = new Position(p.getX() + 1, p.getY());
                return true;
            }
        }
        return false; 
    }

    public Player(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), PLAYER);
    }
}
