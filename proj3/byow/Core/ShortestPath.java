package byow.Core;

import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class ShortestPath {

    private static int calculateDistance(Tile w, Tile t) {
        return Math.abs(w.getX() - t.getX()) + Math.abs(w.getY() - t.getY());
    }

    private static void relax(PriorityQueue<Tile> fringe, Tile v, Tile w, Tile t) {
        int d = v.getDistFromS() + 1;
        if (d < w.getDistFromS()) {
            w.setDistFromS(d);
            w.setPrev(v);
            fringe.add(w);
        }
    }

    /**
     * Return a list of tiles representing the shortest path from the enemy to the player.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Tile> generate(Tile s, Tile t) {
        LinkedList<Tile> list = new LinkedList<>();
        PriorityQueue<Tile> fringe = new PriorityQueue<>();
        Set<Tile> allVisitedTiles = new HashSet<>();
        fringe.add(s);
        s.setDistFromS(0);
        allVisitedTiles.add(s);
        while (!fringe.isEmpty()) {
            Tile v = fringe.poll();
            if (v.equals(t)) {
                break;
            }
            for (Tile w : v.getAdj()) {
                w.setDistToT(calculateDistance(w, t));
                relax(fringe, v, w, t);
                allVisitedTiles.add(w);
            }
        }
        while (t.getPrev() != null) {
            list.addFirst(t);
            t = t.getPrev();
        }
        list.addFirst(s);
        for (Tile tile : allVisitedTiles) {
            tile.setDistFromS(Integer.MAX_VALUE);
            tile.setDistToT(0);
            tile.setPrev(null);
        }
        return list;
    }

}