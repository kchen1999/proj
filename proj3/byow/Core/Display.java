package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class Display {
    private int width;
    private int height;
    private static final int LEFT_OFFSET = 5;

    private void showTileUnderMouse(WorldState ws, int hudTopOffset) {
        String tileDescription = "";
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        if ((mouseX < width && mouseX > 0 && mouseY < (height - Engine.getHudTopOffset() / 2) && mouseY > 0)) {
            tileDescription = ws.getTile((int) (StdDraw.mouseX()),
                    (int) (StdDraw.mouseY() - (hudTopOffset / 2)));
        }
        StdDraw.textLeft(LEFT_OFFSET, height - 1.2, "Tile: " + tileDescription);
    }

    private void showNumOfMovesUntilFullView(int movesInCurrentView) {
        StdDraw.textLeft(LEFT_OFFSET, 1.2, "Moves in current view: " + movesInCurrentView +
                "       Press Q to toggle view (after 26 moves)" +
                "       Press E to show enemy paths");
    }

    public void showHeadsUpDisplay(WorldState ws, int hudTopOffset, int movesInCurrentView) {
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        showTileUnderMouse(ws, hudTopOffset);
        showNumOfMovesUntilFullView(movesInCurrentView);
        StdDraw.show();
    }

    public void loadEnterRandomSeed(StringBuilder seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(width / 2, height / 2, "Enter random seed (Press S at end to confirm): ");
        StdDraw.text(width / 2, height / 2 - 3.2, seed.toString());
        StdDraw.show();
    }

    public void loadGameOver() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 32);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 4 * 3, "GAME OVER!");
        font = new Font("Monaco", Font.BOLD, 21);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "Load Main Menu (M)");
        StdDraw.text(width / 2, height / 2 - 1.6, "Quit (:Q)");
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
        StdDraw.text(width / 2, height / 2 - 3.2, "Quit (:Q)");
        StdDraw.show();
    }

    public Display(int width, int height) {
        this.width = width;
        this.height = height + Engine.getHudTopOffset();
    }
}
