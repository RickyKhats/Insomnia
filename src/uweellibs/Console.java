package uweellibs;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    public static String[] forbiddenCharacters = {
            "!", "@", "#", "№",
            "$", ";", "%", ":",
            "^", "&", "?", "*",
            "(", ")", "-", "_",
            "]", "[", "=", "+",
            "{", "}", "\\", "|",
            ",", ".", "?", "/",
            "<", ">", "\"", "'",
            "`", "~", "1", "2",
            "3", "4", "5", "6",
            "7", "8", "9", "0",
    };

    public static void Println(Object... objects) {
        ArrayList<String> text = new ArrayList<>();
        for (Object object : objects)
            if (!object.toString().equals(""))
                if (!object.toString().replaceAll(" {2}", " ").equals(" "))
                    text.add(object.toString());
        for (String Text : text)
            if (endsWithForbiddenCharacter(Text))
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
    }

    public static void Print(Object... objects) {
        ArrayList<String> text = new ArrayList<>(objects.length);
        for (Object object : objects)
            text.add(object.toString());
        for (String Text : text)
            if (endsWithForbiddenCharacter(Text))
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
    }

    public static boolean endsWithForbiddenCharacter(String text) {
        for (String forbiddenCharacter : forbiddenCharacters)
            if (text.endsWith(forbiddenCharacter))
                return true;
        return false;
    }

    public static String Read() {
        return new Scanner(System.in).nextLine();
    }
}
