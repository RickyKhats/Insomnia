//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import uweellibs.Console;
import uweellibs.MonoBehaviour;
import uweellibs.graphics.Button;
import uweellibs.graphics.Window;

public class Main extends MonoBehaviour {
    public static Engine engine;

    public static void main(String[] args) {

        Window window = new Window("Insomnia", 500,500);
        var button = new Button("gavno", 645, 204).
                OnRightClick(() -> Console.Println("RightClicked")).
                OnLeftClick(() -> Console.Println("LeftClicked")).
                BackgroundResource("button.png");
        window.Add(button);
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
