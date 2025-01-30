package byow.Core;

public class StringInputDevice implements InputSource{
    private String input;
    private int index;

    public StringInputDevice(String s) {
        input = s;
        index = 0;
    }


    @Override
    public char getNextKey() {
        char c = Character.toUpperCase(input.charAt(index));
        index += 1;
        return c;
    }

    @Override
    public boolean possibleNextInput() {
        return index < input.length();
    }
}

