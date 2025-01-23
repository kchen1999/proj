package byow.Core;

public class Path implements Comparable<Path> {
    private Room start;
    private Room end;
    private double distance;

    public double getDistance() {
        return distance;
    }

    private void calculateDistance() {
        int x = Math.abs(start.getCentre().getX() - end.getCentre().getX());
        int y = Math.abs(start.getCentre().getY() - end.getCentre().getY());
        distance = Math.hypot(x, y);
    }

    public Path(Room start, Room end) {
        this.start = start;
        this.end = end;
        calculateDistance();
    }

    @Override
    public int compareTo(Path p) {
        if (this.distance < p.getDistance()) {
            return - 1;
        } else if (this.distance > p.getDistance()) {
            return 1;
        }
        return 0;
    }
}

