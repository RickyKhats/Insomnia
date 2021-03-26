package uweellibs;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    public static void Println(Object... objects) {
        for (String Text : Replace(objects))
            if (endsWithForbiddenCharacter(Text))
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
    }

    protected static ArrayList<String> Replace(Object... objects) {
        ArrayList<String> text = new ArrayList<>();
        try {
            for (Object object : objects)
                text.add(object.toString());
        } catch (NullPointerException ignored) {
        }
        return text;
    }

    public static boolean endsWithForbiddenCharacter(String text) {
        for (String forbiddenCharacter : Codec.forbiddenCharacters)
            if (text.endsWith(forbiddenCharacter))
                return true;
        for (String forbiddenCharacter : Codec.integers)
            if (text.endsWith(forbiddenCharacter))
                return true;
        return false;
    }

    public static String Read() {
        return new Scanner(System.in).nextLine();
    }

    public void Print(Object... objects) {
        for (String Text : Replace(objects))
            if (endsWithForbiddenCharacter(Text))
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
    }
}
