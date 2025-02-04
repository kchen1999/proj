package byow.Core;

import byow.TileEngine.TETile;
import java.util.List;

public class Tile implements Comparable<Tile> {
    private TETile tile;
    private int x;
    private int y;
    private List<Tile> adj;
    private int distFromS;
    private int distToT;
    private Tile prev;

    public TETile getTile() {
        return tile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistFromS() {
        return distFromS;
    }

    public List<Tile> getAdj() {
        return adj;
    }

    public Tile getPrev() {
        return prev;
    }

    public void setDistFromS(int distFromS) {
        this.distFromS = distFromS;
    }

    public void setDistToT(int distToT) {
        this.distToT = distToT;
    }

    public void setPrev(Tile prev) {
        this.prev = prev;
    }

    public void setAdj(List<Tile> adj) {
        this.adj = adj;
    }

    public void setTile(TETile tile) {
        this.tile = tile;
    }

    public Tile(TETile tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.distFromS = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Tile w) {
        if (this.distFromS + this.distToT - w.distFromS - w.distToT == 0) {
            return this.distToT - w.distToT;
        } else {
            return this.distFromS + this.distToT - w.distFromS - w.distToT;
        }
    }
}