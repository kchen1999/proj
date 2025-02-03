package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    private static final Color BRIGHTEST = new Color(80, 80, 200);
    private static final Color MID_LIGHT = new Color(50, 50, 160);
    private static final Color DIM = new Color(30, 30, 120);
    private static final Color ALMOST_DARK = new Color(15, 15, 80);
    private static final Color DARKEST = new Color(5, 5, 40);
    public static final TETile AVATAR = new TETile('@', Color.white, new Color(10, 10, 30), "you");
    public static final TETile WALL = new TETile(' ', Color.white, new Color(110, 110, 110),
            "wall");
    public static final TETile FLOOR = new TETile('·', Color.white, new Color(5, 5, 25),
            "floor");
    public static final TETile ENEMY_PATH = new TETile('✷', new Color(192, 32, 32), new Color(5, 5, 25),
            "floor");
    public static final TETile FLOOR0 = new TETile('·', Color.white, BRIGHTEST, "floor0");
    public static final TETile FLOOR1 = new TETile('·', Color.white, MID_LIGHT, "floor1");
    public static final TETile FLOOR2 = new TETile('·', Color.white, DIM, "floor2");
    public static final TETile FLOOR3 = new TETile('·', Color.white, ALMOST_DARK, "floor3");
    public static final TETile FLOOR4 = new TETile('·', Color.white, DARKEST, "floor4");
    public static final TETile LIGHT = new TETile('•', Color.white, BRIGHTEST, "light");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, new Color(10, 10, 30), "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


