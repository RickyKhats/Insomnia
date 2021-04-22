package uweellibs.graphics.view;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
        public Background(Color color) {
            setLayout(null);
            this.setBackground(color);
        }

        public void add(View view){
            this.add(view.get());
        }
    }
