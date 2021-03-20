//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import uweellibs.MonoBehaviour;

public class Main extends MonoBehaviour {
    public static Engine engine;

    public static void main(String[] args) {
        new ExceptionOccurred(new Throwable("Anus"));
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
