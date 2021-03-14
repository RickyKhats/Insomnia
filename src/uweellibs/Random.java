package uweellibs;

public class Random {
    public static float Range(float min, float max) {
        return (float) ((Math.random() * ((max - min) + 1)) + min);
    }

    public static float Range(Range range) {
        return (float) ((Math.random() * ((range.max - range.min) + 1)) + range.min);
    }

    public static int Range(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public static boolean Bool() {
        return Range(0, 1) == 1;
    }

    public static char Char(char[] chars) {
        return chars[Range(0, chars.length)];
    }

    public static String String(String[] strings) {
        return strings[Range(0, strings.length)];
    }

    public static boolean Percent(int percent) {
        return Random.Range(0, 100) > percent;
    }
}
