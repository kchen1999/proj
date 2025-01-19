package byow.Core;

/*
How it works in detail:
Step 1: Room Placement

Randomly place a set of rooms (rectangular or other shapes) on the grid.
Ensure that the rooms don’t overlap by checking positions or using a grid-based allocation strategy.
Assign random widths and heights to the rooms.

Step 2: Random Walk for Hallways

Start from one room (or multiple rooms if preferred).
Move in a random direction (up, down, left, right), and carve out the hallway tiles. At each step, you can
randomly change direction to create turns.
The random walk can be terminated after a certain length or when it reaches another room. Ensure that the path
ends in an intersection if needed (this can be enforced programmatically).

Step 3: Ensure Connectivity

If the random walk does not connect some rooms (due to the random nature of the path), additional random walks
or post-processing can be used to fill in the gaps, ensuring that every room is reachable.

Step 4: Randomization of Hallways

Vary the width of hallways (1 or 2 tiles).
Include intersections or multiple paths that branch off the main hallways to enhance the world’s complexity.

Step 5: Tile Representation

Convert the grid into a tile-based system with walls, floors, and unused spaces.
Use different tiles for walls and floors to ensure visual distinction.
The unused spaces (e.g., areas not connected to hallways) can be represented with a unique tile type.

Step 6: Post-Processing (Optional)

Use Cellular Automata for further refinement of the world layout (e.g., smoothing out walls, adding caves, etc.).
Introduce optional outdoor spaces or thematic features that fit within the generated grid.

 */

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MapGenerator {
    private static final List<Room> rooms = new ArrayList<>();
    private Random random;

    private static class Room {
        private static final TETile tile = Tileset.FLOOR;
        private Position topLeft;
        private Position bottomRight;

        private void draw(TETile[][] world, int width, int height) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    world[topLeft.getX() + i][topLeft.getY() - j] = tile;
                }
            }
        }

        private Position getTopLeft() {
            return topLeft;
        }

        private Position getBottomRight() {
            return bottomRight;
        }

        public Room(TETile[][] world, Position topLeft, int width, int height) {
            this.topLeft = topLeft;
            this.bottomRight = new Position(topLeft.getX() + width, topLeft.getY() - height);
            this.draw(world, width, height);
            rooms.add(this);
        }

    }

    public boolean isOverlap(Position topLeft, int width, int height) {
        for (Room room : rooms) {
            Position topRight = new Position(topLeft.getX() + width, topLeft.getY());
            Position bottomLeft = new Position(topLeft.getX(), topLeft.getY() - height);
            Position bottomRight = new Position(topLeft.getX() + width, topLeft.getY() - height);
            if(topLeft.getX() >= room.getTopLeft().getX() && topLeft.getX() <= room.getBottomRight().getX()
                    && topLeft.getY() <= room.getTopLeft().getY() && topLeft.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(topRight.getX() >= room.getTopLeft().getX() && topRight.getX() <= room.getBottomRight().getX()
                    && topRight.getY() <= room.getTopLeft().getY() && topRight.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(bottomLeft.getX() >= room.getTopLeft().getX() && bottomLeft.getX() <= room.getBottomRight().getX()
                    && bottomLeft.getY() <= room.getTopLeft().getY() && bottomLeft.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(bottomRight.getX() >= room.getTopLeft().getX() && bottomRight.getX() <= room.getBottomRight().getX()
                    && bottomRight.getY() <= room.getTopLeft().getY() && bottomRight.getY() >= room.getBottomRight().getY()) {
                return true;
            }
            if(topLeft.getX() < room.getTopLeft().getX() && topRight.getX() > room.getBottomRight().getX()
                    && topLeft.getY() == room.getBottomRight().getY()) {
                return true;
            }
            if(topLeft.getX() == room.getBottomRight().getX() && topLeft.getY() > room.getTopLeft().getY()
                && bottomLeft.getY() < room.getBottomRight().getY()) {
                return true;
            }
            if(bottomLeft.getX() < room.getTopLeft().getX() && bottomRight.getX() > room.getBottomRight().getX()
                    && bottomLeft.getY() == room.getTopLeft().getY()) {
                return true;
            }
            if(bottomRight.getX() == room.getTopLeft().getX() && topRight.getY() > room.getTopLeft().getY()
                    && bottomRight.getY() < room.getBottomRight().getY()) {
                return true;
            }
        }
        return false;
    }

    public MapGenerator(int seed) {
        this.random = new Random(seed);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);

        // initialize tiles
        TETile[][] world = new TETile[80][30];
        for (int x = 0; x < 80; x += 1) {
            for (int y = 0; y < 30; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int i = 0; i < 1; i++) {
            Room r = new Room(world, new Position(0, 2), 4, 2);
        }
        Room r1 = new Room(world, new Position(0, 2), 4, 2);
        // draws the world to the screen
        ter.renderFrame(world);
    }
}