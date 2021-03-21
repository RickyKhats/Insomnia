package uweellibs;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    public void Println(Object... objects) {
        for (String Text : Replace(objects))
            if (endsWithForbiddenCharacter(Text))
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.println("> " + Text.replaceAll("\n", "\n> "));
    }

    protected ArrayList<String> Replace(Object... objects){
        ArrayList<String> text = new ArrayList<>();
        for (Object object : objects)
            if (!object.toString().equals(""))
                if (!object.toString().replaceAll(" {2}", " ").equals(" "))
                    text.add(object.toString());
                return text;
    }

    public void Print(Object... objects) {
        for (String Text : Replace(objects))
            if (endsWithForbiddenCharacter(Text))
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
            else
                System.out.print("> " + Text.replaceAll("\n", "\n> "));
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
}
