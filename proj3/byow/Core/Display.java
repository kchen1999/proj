package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class Display {
    private int width;
    private int height;
    private static final int LEFT_OFFSET = 5;
    private static final int HEADER_1_FONT = 32;
    private static final int HEADER_2_FONT = 21;
    private static final int HEADER_3_FONT = 14;
    private static final int DISPLAY_ENEMY_PATHS_LIMIT = 3;
    private static final int OBSCURED_VIEW_MOVES = 25;
    private static final String FONT = "Monaco";

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

    private void showNumOfMovesUntilFullView(int movesInCurrentView, int displayEnemyPaths) {
        StdDraw.textLeft(LEFT_OFFSET, 1.2, "Moves in current view: " + movesInCurrentView +
                "       Press Q to toggle view (after " + OBSCURED_VIEW_MOVES + " moves)" +
                "       Press E to show enemy paths (" + (DISPLAY_ENEMY_PATHS_LIMIT - displayEnemyPaths) +
                " times remaining)");
    }

    public void showHeadsUpDisplay(WorldState ws, int hudTopOffset, int movesInCurrentView, int displayEnemyPaths) {
        Font font = new Font(FONT, Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        showTileUnderMouse(ws, hudTopOffset);
        showNumOfMovesUntilFullView(movesInCurrentView, displayEnemyPaths );
        StdDraw.show();
    }

    private void showGameResultMessage(boolean victory) {
        if (victory) {
            StdDraw.text(width / 2, height / 4 * 3 - 3.2, "Congratulations! You win!");
        } else {
            StdDraw.text(width / 2, height / 4 * 3 - 3.2, "You lost! Maybe next time :(");
        }
    }

    public void loadEnterRandomSeed(StringBuilder seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(width / 2, height / 2, "Enter random seed (Press S at end to confirm): ");
        StdDraw.text(width / 2, height / 2 - 3.2, seed.toString());
        StdDraw.show();
    }

    public void loadGameOver(boolean victory) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font(FONT, Font.BOLD, HEADER_1_FONT);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 4 * 3, "GAME OVER!");
        showGameResultMessage(victory);
        font = new Font(FONT, Font.BOLD, HEADER_2_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "Load Main Menu (M)");
        StdDraw.text(width / 2, height / 2 - 1.6, "Quit (:Q)");
        StdDraw.show();
    }

    public void loadInstructions() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font(FONT, Font.BOLD, HEADER_1_FONT);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 4 * 3, "INSTRUCTIONS / LORE");
        font = new Font(FONT, Font.BOLD, HEADER_2_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2 + 3, "In the shifting void, sight is fleeting.");
        font = new Font(FONT, Font.PLAIN, HEADER_3_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2 - 0.2, "Every 25 steps, darkness swallows the world, leaving only " +
                "instinct to guide you.");
        StdDraw.text(width / 2, height / 2 - 1.8, "For 5 fleeting moments, clarity returns—just enough to glimpse the dangers that " +
                "lurk.");
        StdDraw.text(width / 2, height / 2 - 3.4, "Two relentless hunters stalk your path, unseen until it’s too late. If they catch you, your " +
                "journey ends.");
        font = new Font(FONT, Font.BOLD, HEADER_2_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2 - 6.6, "Somewhere in the depths, a single flower waits. A beacon. A goal. A test of your will.");
        font = new Font(FONT, Font.PLAIN, HEADER_3_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2 - 9.8, "Three times, and only three, the enemy’s path will reveal itself—glowing red, a glimpse of the " +
                "inevitable. ");
        StdDraw.text(width / 2, height / 2 - 11.4, "Use it wisely, or be lost in the dark.");
        StdDraw.text(width / 2, height / 2 - 13, "Can you reach the light before the darkness claims you?");
        StdDraw.show();
    }

    public void loadMainMenu() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font(FONT, Font.BOLD, HEADER_1_FONT);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 4 * 3, "GAME: DIM PURSUIT");
        font = new Font(FONT, Font.BOLD, HEADER_2_FONT);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, "New Game (N)");
        StdDraw.text(width / 2, height / 2 - 1.75, "Load Game (L)");
        StdDraw.text(width / 2, height / 2 - 3.5, "Instructions/Lore (I)");
        StdDraw.text(width / 2, height / 2 - 5.25, "Return to main menu (M)");
        StdDraw.text(width / 2, height / 2 - 7, "Quit (:Q)");
        StdDraw.show();
    }

    public Display(int width, int height) {
        this.width = width;
        this.height = height + Engine.getHudTopOffset();
    }
}
