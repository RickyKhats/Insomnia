package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Engine extends MonoBehaviour {
    static class ConsoleListener extends JFrame implements KeyListener{

        public void keyPressed(KeyEvent keyEvent) {
            if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){

            }
        }

        public void keyTyped(KeyEvent keyEvent) {

        }

        public void keyReleased(KeyEvent keyEvent) {

        }
    }
    Game game;
    static JTextArea console = new JTextArea();
    static JFrame window = new ConsoleListener();

    public void PreInit() {
        window.setName("Insomnia - console");
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
        window.setSize(300,300);
        window.setLayout(null);
        window.add(console);
        window.setVisible(true);
    }

    public void PostUpdate() {
        console.setBounds(0,0, window.getWidth(), window.getHeight());
    }

    public synchronized void PostInit() {

    }

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return game;
    }
}
