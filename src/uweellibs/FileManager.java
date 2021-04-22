package uweellibs;

import com.uweeldteam.ExceptionOccurred;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public static boolean Exists(String path){
        return new File(path).get().exists();
    }

    public static void Create(String path) {
        try {
            boolean fileCreated = new java.io.File(path).createNewFile();
            if (fileCreated)
                Debug.Log("File created");
        } catch (IOException e) {
            Debug.Log("File not created");
            new ExceptionOccurred(e);
        }
    }

    public static String ReadFile(File file) {

        try {
            return new String(Files.readAllBytes(Paths.get(file.get().getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class File {

        java.io.File file;

        public File(String path) {
            file = new java.io.File(path);
        }

        public java.io.File get() {
            return file;
        }
    }
}
