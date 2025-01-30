package byow.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GameSaver {

    public static void resetSavedState() {
        File seedFile = new File("seed.txt");
        File playerPositionFile = new File("savedPlayerPosition.txt");
        if (seedFile.exists()) {
            seedFile.delete();
        }
        if (playerPositionFile.exists()) {
            playerPositionFile.delete();
        }
    }

    public static void save(String seed, Position p) {
        File seedFile = new File("seed.txt");
        List<String> lines = Arrays.asList(seed);
        resetSavedState();
        try {
            seedFile.createNewFile();
            Files.write(Paths.get("seed.txt"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File playerPositionFile = new File("savedPlayerPosition.txt");
        lines = Arrays.asList(String.valueOf(p.getX()), String.valueOf(p.getY()));
        try {
            playerPositionFile.createNewFile();
            Files.write(Paths.get("savedPlayerPosition.txt"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String readSeed() {
        File seedFile = new File("seed.txt");
        if (!seedFile.exists()) {
            System.exit(0);
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get("seed.txt"));
            return lines.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Position readPlayerPosition() {
        int x = 0;
        int y = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get("savedPlayerPosition.txt"));
            x = Integer.parseInt(lines.get(0));
            y = Integer.parseInt(lines.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Position(x, y);
    }


}