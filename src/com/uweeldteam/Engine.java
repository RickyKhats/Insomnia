package com.uweeldteam;

import com.uweeldteam.game.Game;
import uweellibs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Engine extends MonoBehaviour {

    public static void Println(String text) {
        window.Println(text);
    }

    static class ConsoleWindow {
        JFrame window = new JFrame();
        JPanel background = new JPanel();
        JLabel console = new JLabel("<html> </html>");
        private String lastMessage = "";
        String text = "";
        public ConsoleWindow(String title) {
            console.setForeground(Color.GREEN);
            background.setBackground(Color.BLACK);
            background.add(console);
            background.setLayout(new BorderLayout());
            window.setName(title);
            window.add(background);
            window.setLayout(new BorderLayout());
            window.setSize(300, 300);
            window.addKeyListener(new KeyListener() {
                boolean CheckChar(char ch) {
                    String character = String.valueOf(ch).toLowerCase();
                    return
                            Arrays.asList(Codec.ru).contains(character)
                                    || Arrays.asList(Codec.en).contains(character)
                                    || Arrays.asList(Codec.integers).contains(character)
                                    || character.equals(String.valueOf(Codec.quote))
                                    || character.equals(Codec.space);

                }

                public void keyPressed(KeyEvent keyEvent) {
                    switch (keyEvent.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                            Print("\n");
                            switch (Game.gameState) {
                                case fight:
                                    Main.Engine().Game().fight.ReadCommand(lastMessage);
                                    break;
                                case normal:
                                    Main.Engine().Game().ReadCommand(lastMessage);
                                    break;
                            }
                            Console.Println(lastMessage);
                            lastMessage = "";
                            break;
                        case KeyEvent.VK_BACK_SPACE:
                            text = (text.length() > 0) ? text.substring(0, text.length() -1) : "" ;
                            console.setText("<html>" + text.replaceAll("\n", "<br>") + "</html>");
                            break;
                        case KeyEvent.VK_SPACE:
                            Print(" ");
                        default:
                            if (CheckChar(keyEvent.getKeyChar()))
                                Print(keyEvent.getKeyChar());
                    }
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

                    } else lastMessage += keyEvent.getKeyChar();
                }

                public void keyTyped(KeyEvent keyEvent) {

                }

                public void keyReleased(KeyEvent keyEvent) {

                }
            });
            background.setBounds(0, 0, window.getWidth(), window.getHeight());
            console.setBounds(0, 0, window.getWidth(), window.getHeight());
            console.setHorizontalAlignment(SwingConstants.LEFT);
            console.setVerticalAlignment(SwingConstants.NORTH);
            window.setVisible(true);
            new Thread(() -> {
                do {
                    Time.Sleep(50);
                    Update();
                } while (true);
            });
        }

        public void Println(Object... objects) {
            ArrayList<String> text = new ArrayList<>();
            for (Object object : objects)
                if (!object.toString().equals(""))
                    if (!object.toString().replaceAll(" {2}", " ").equals(" "))
                        text.add(object.toString());
            for (String Text : text)
                if (Console.endsWithForbiddenCharacter(Text))
                    this.text += Text + ".\n";
                else
                    this.text += Text + "\n";
            console.setText("<html>" + this.text.replaceAll("\n", "<br>") + "</html>");
        }

        public void Print(Object... objects) {
            ArrayList<String> text = new ArrayList<>();
            for (Object object : objects)
                if (!object.toString().equals(""))
                    if (!object.toString().replaceAll(" {2}", " ").equals(" "))
                        text.add(object.toString());
            for (String Text : text)
                if (Console.endsWithForbiddenCharacter(Text))
                    this.text += Text + ".";
                else
                    this.text += Text;
            console.setText("<html>" + this.text.replaceAll("\n", "<br>") + "</html>");
        }

        public void Update() {
            background.setBounds(0, 0, window.getWidth(), window.getHeight());
            console.setBounds(0, 0, background.getWidth(), background.getHeight());
        }
    }

    Game game;
    static ConsoleWindow window = new ConsoleWindow("Insomnia - console");

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
