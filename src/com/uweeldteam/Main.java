//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import uweellibs.Console;
import uweellibs.MonoBehaviour;
import uweellibs.graphics.Button;
import uweellibs.graphics.Window;

import javax.swing.*;

public class Main extends MonoBehaviour {
    public static Engine engine;

    public static void main(String[] args) {

        Window window = new Window("Insomnia", 1000,1000);
        var button = new Button(window, "guano", 645, 204).
                OnRightClick(() -> Console.Println("RightClicked")).
                OnLeftClick(() -> Console.Println("LeftClicked")).
                BackgroundResource("button.png");
        window.Add(button, "South");
        /*try {
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
         */
    }

    public static Engine Engine() {
        return engine;
    }
}
