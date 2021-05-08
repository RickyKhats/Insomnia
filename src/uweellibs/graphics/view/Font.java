package uweellibs.graphics.view;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import static java.awt.Font.createFont;

public class Font {
    java.awt.Font font;
    public Font(String path) {
        try {
            font = createFont(0, new FileInputStream("main_font.ttf")).deriveFont(java.awt.Font.PLAIN, 13.0F);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public java.awt.Font get() {
        return font;
    }
}
