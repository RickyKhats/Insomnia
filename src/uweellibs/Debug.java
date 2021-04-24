package uweellibs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Debug extends Console {
    private static DateTime date = new DateTime();
    private static File logFile;
    private static FileWriter printWriter;

    static {
        Now();
    }

    public static FileWriter PrintWriter() {
        return printWriter;
    }

    static void Now() {
        date.Now();
        FileManager.Create("Debugs");
        FileManager.Create("Log-" + Debug.date.toString());
        logFile = new File("Log-" + Debug.date.toString());
    }

    static void Print(String message) {
        Console.Println(message);
        try {
            printWriter = new FileWriter(logFile.get(), true);
            printWriter.write(message + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Log(Object... objects) {
        new Thread(() -> {
            ArrayList<String> messages = Replace(objects);
            for (String message : messages) {
                Print("[Message] " + message);
            }
        }).start();

    }

    public static void WarningLog(Object... objects) {
        new Thread(() -> {
            ArrayList<String> messages = Replace(objects);
            for (String message : messages) {
                Print("[Warning] " + message);
            }
        }).start();

    }

    public static void ErrorLog(Object... objects) {
        new Thread(() -> {
            ArrayList<String> messages = Replace(objects);
            for (String message : messages) {
                Print("[Error] " + message);
            }
        }).start();
    }

}
