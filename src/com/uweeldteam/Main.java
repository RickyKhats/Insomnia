package com.uweeldteam;

// TODO: 27.03.2021
//  Починить ошибку проверки наличия предмета

public class Main extends uweellibs.MonoBehaviour {
    public static Engine engine;

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
