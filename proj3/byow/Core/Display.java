package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Display {
    private int width;
    private int height;
    private StringBuilder seed;

    public void loadEnterRandomSeed(StringBuilder seed) {
        this.seed = seed;
        StdDraw.clear(Color.BLACK);
        StdDraw.text(width / 2, height / 2, "Enter random seed (Press S at end to confirm): ");
        StdDraw.text(width / 2, height / 2 - 3.2, seed.toString());
        StdDraw.show();
    }

    public void loadMainMenu() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 32);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 4 * 3, "RANDOM WORLD GAME");
        font = new Font("Monaco", Font.BOLD, 21);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "New Game (N)");
        StdDraw.text(width / 2, height / 2 - 1.6, "Load Game (L)");
        StdDraw.text(width / 2, height / 2 - 3.2, "Quit (Q)");
        StdDraw.show();
    }

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        seed = new StringBuilder();
    }
}