package uweellibs;

import com.uweeldteam.ExceptionOccurred;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File {

    java.io.File file;

    public File(String path) {
        file = new java.io.File(path);
    }

    public java.io.File get() {
        return file;
    }

}
