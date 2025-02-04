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

    private boolean moveHelper(WorldState ws, int x, int y) {
        if (ws.isTile(x, y, ENEMY)) {
            System.out.println("Game over");
            Engine.setGameOver();
            return false;
        }
        if (!ws.isWall(x, y)) {
            ws.setTile(x, y, PLAYER);
            ws.setTile(p.getX(), p.getY(), FLOOR);
            p = new Position(x, y);
            return true;
        }
        return false;
    }

    public boolean move(WorldState ws, char dir) {
        if (dir == 'W') {
            return moveHelper(ws, p.getX(),p.getY() + 1);
        } else if (dir == 'A') {
            return moveHelper(ws, p.getX() - 1,p.getY());
        } else if (dir == 'S') {
            return moveHelper(ws, p.getX(),p.getY() - 1);
        } else if (dir == 'D') {
            return moveHelper(ws, p.getX() + 1,p.getY());
        }
        return false; 
    }

    public Player(WorldState ws, Position p) {
        this.p = p;
        ws.setTile(p.getX(), p.getY(), PLAYER);
    }
}
