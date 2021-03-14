package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Engine extends MonoBehaviour {
    Game game;

    public void PreInit() {
        if (PlayerPrefs.GetObject("Game", Game.class) == null) {
            Game(new Game());
            return;
        } else {
            Game((Game) PlayerPrefs.GetObject("Game", Game.class));
        }
        File file = new File("Engine.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(Json.ToJson(this));
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return game;
    }
}
