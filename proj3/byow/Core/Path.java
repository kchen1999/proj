package byow.Core;

public class Path implements Comparable<Path> {
    private Room start;
    private Room end;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public Room getStart() {
        return start;
    }

    public Room getEnd() {
        return end;
    }

    public void printPath() {
        System.out.println("Printing path: ");
        start.printRoom();
        end.printRoom();
        System.out.println("");
    }

    private void calculateDistance(Room start, Room end) {
        int x = Math.abs(start.getCentre().getX() - end.getCentre().getX());
        int y = Math.abs(start.getCentre().getY() - end.getCentre().getY());
        distance = Math.hypot(x, y);
        if (start.getTopLeft().getX() < end.getTopLeft().getX()) {
            this.start = start;
            this.end = end;
        } else if (start.getTopLeft().getX() > end.getTopLeft().getX()) {
            this.start = end;
            this.end = start;
        } else if (start.getTopLeft().getY() < end.getTopLeft().getY()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }

    }

    public Path(Room start, Room end) {
        calculateDistance(start, end);
    }

    @Override
    public int compareTo(Path p) {
        if (this.distance < p.getDistance()) {
            return - 1;
        } else if (this.distance >= p.getDistance()) {
            return 1;
        }
        return 1;

    }
}

