package com.uweeldteam;

import uweellibs.Debug;

public class Main extends uweellibs.MonoBehaviour {
    public static Engine engine;

    public static void main(String[] args) {
        Debug.Log("Application started");
        new Main();
    }

    public static Engine Engine() {
        return engine;
    }

    public synchronized void Init() {
        try {
            engine = new Engine(false);
        } catch (Exception e) {
            new ExceptionOccurred(e);
        }
        Debug.Log("Main initialized");
    }
}
