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

    private static void relax(PriorityQueue<Tile> fringe, Tile v, Tile w) {
        int d = v.getDistFromS() + 1;
        if (d < w.getDistFromS()) {
            w.setDistFromS(d);
            w.setPrev(v);
            fringe.add(w);
        }
    }

    private static void resetVisitedTiles(Set<Tile> allVisitedTiles) {
        for (Tile tile : allVisitedTiles) {
            tile.setDistFromS(Integer.MAX_VALUE);
            tile.setDistToT(0);
            tile.setPrev(null);
        }
    }

    private static LinkedList<Tile> generatePath(Tile s, Tile t) {
        LinkedList<Tile> path = new LinkedList<>();
        while (t.getPrev() != null) {
            path.addFirst(t);
            t = t.getPrev();
        }
        path.addFirst(s);
        return path;
    }

    public static List<Tile> generate(Tile s, Tile t) {
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
                relax(fringe, v, w);
                allVisitedTiles.add(w);
            }
        }
        LinkedList<Tile> shortestPath = generatePath(s, t);
        resetVisitedTiles(allVisitedTiles);
        return shortestPath;
    }
}
