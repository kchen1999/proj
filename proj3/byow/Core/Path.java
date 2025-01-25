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

    private void orderPath(Room start, Room end) {
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

    private void calculateDistance(Room start, Room end) {
        orderPath(start, end);
        int horizontalDistance = this.end.getTopLeft().getX() - this.start.getBottomRight().getX() + 1;
        if (this.start.getTopLeft().getX() == this.end.getTopLeft().getX()) {
            this.distance = this.end.getBottomRight().getY() - this.start.getTopLeft().getY() + 1;
        } else if (this.start.getTopLeft().getY() < this.end.getTopLeft().getY()) {
            if (this.end.getTopLeft().getX() <= this.start.getBottomRight().getX()) {
                this.distance = this.end.getBottomRight().getY() - this.start.getTopLeft().getY() + 1;
            } else if (this.start.getTopLeft().getY() >= this.end.getBottomRight().getY()) {
                this.distance = horizontalDistance;
            } else {
                this.distance = horizontalDistance + this.end.getBottomRight().getY() - this.start.getTopLeft().getY() + 1;
            }
        } else if(this.start.getTopLeft().getY() > this.end.getTopLeft().getY()) {
            if (this.end.getTopLeft().getX() <= this.start.getBottomRight().getX()) {
                this.distance = this.start.getBottomRight().getY() - this.end.getTopLeft().getY() + 1;
            } else if (this.end.getTopLeft().getY() >= this.start.getBottomRight().getY()) {
                this.distance = horizontalDistance;
            } else {
                this.distance = horizontalDistance + this.start.getBottomRight().getY() - this.end.getTopLeft().getY() + 1;
            }
        } else {
            this.distance = horizontalDistance;
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

