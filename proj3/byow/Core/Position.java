package byow.Core;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position p) {
        if (this.x == p.x && this.y == p.y) {
            return 0;
        }
        return this.x - p.x;
    }
}
