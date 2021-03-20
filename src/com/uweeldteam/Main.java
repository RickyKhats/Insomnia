//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import sun.swing.SwingUtilities2;
import uweellibs.MonoBehaviour;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;

public class Main extends MonoBehaviour {
    public static Engine engine;

    public Main() {
    }

    public static void main(String[] args) {
        try {
            try {
                if (args[0].equals("newGame")) {
                    engine = new Engine(true);
                }
            } catch (IndexOutOfBoundsException e) {
                engine = new Engine(false);
            }
        } catch (Exception e) {
            new ExceptionOccurred(e);
        }
    }

    public static Engine Engine() {
        return engine;
    }
}
