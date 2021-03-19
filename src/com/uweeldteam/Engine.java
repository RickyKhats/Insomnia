//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import com.uweeldteam.game.Game;
import com.uweeldteam.game.fight.Fight;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import uweellibs.Codec;
import uweellibs.Json;
import uweellibs.MonoBehaviour;
import uweellibs.PlayerPrefs;
import uweellibs.WaitForSeconds;

public class Engine extends MonoBehaviour {
    static Engine.ConsoleWindow window = new Engine.ConsoleWindow("Insomnia - cmd", 800, 420);
    Game game;

    public Engine(boolean newGame) {
        try {
            if (newGame) {
                this.Game(new Game());
            } else {
                try {
                    this.Game((Game)PlayerPrefs.GetObject("Game", Game.class));
                } catch (NullPointerException var7) {
                    this.Game(new Game());
                }
            }

            File file = new File("Engine.json");
            boolean fileCreated = false;

            try {
                fileCreated = file.createNewFile();
            } catch (IOException var6) {
                var6.printStackTrace();
            }

            if (fileCreated) {
                try {
                    PrintWriter printWriter = new PrintWriter(file);
                    printWriter.println(Json.ToJson(this));
                    printWriter.close();
                } catch (FileNotFoundException var5) {
                    var5.printStackTrace();
                }
            }
        } catch (Exception var8) {
            com.uweeldteam.Main.ExceptionOccurred(var8);
        }

    }

    public static void Println(String text) {
        window.Println(text);
    }

    private void Game(Game game) {
        this.game = game;
    }

    public Game Game() {
        return this.game;
    }

    public Engine.ConsoleWindow Window() {
        return window;
    }

    static class ConsoleWindow {
        JFrame window;
        JLabel console;
        JPanel background;
        JScrollPane scroll;
        private String lastMessage = "";
        private String text = "";

        public ConsoleWindow(String title, int weight, int height) {
            try {
                this.window = new JFrame(title);
                this.background = new JPanel();
                this.window.add(this.background);
                this.console = new JLabel();
                this.scroll = new JScrollPane(this.console, 22, 31);
                this.background.add(this.scroll);
                this.background.setLayout(new BorderLayout());
                this.window.setLayout(new BorderLayout());
                this.window.setSize(weight, height);
                this.window.setResizable(false);
                this.window.addKeyListener(new KeyListener() {
                    boolean CheckChar(char ch) {
                        String character = String.valueOf(ch).toLowerCase();
                        return Arrays.asList(Codec.ru).contains(character) || Arrays.asList(Codec.en).contains(character) || Arrays.asList(Codec.integers).contains(character) || Arrays.asList(Codec.forbiddenCharacters).contains(character) || character.equals(String.valueOf('"'));
                    }

                    public void keyPressed(KeyEvent keyEvent) {
                        Engine.ConsoleWindow var10000;
                        String var10001;
                        switch(keyEvent.getKeyCode()) {
                            case 8:
                                if (ConsoleWindow.this.lastMessage.length() > 0) {
                                    ConsoleWindow.this.text = ConsoleWindow.this.text.substring(0, ConsoleWindow.this.text.length() - 1);
                                    ConsoleWindow.this.lastMessage = ConsoleWindow.this.lastMessage.substring(0, ConsoleWindow.this.lastMessage.length() - 1);
                                }
                                break;
                            case 10:
                                ConsoleWindow.this.Print("\n");
                                switch(com.uweeldteam.Main.Engine().Game().gameState) {
                                    case fight:
                                        Fight.ReadCommand(ConsoleWindow.this.lastMessage);
                                        break;
                                    case normal:
                                        com.uweeldteam.Main.Engine().Game().ReadCommand(ConsoleWindow.this.lastMessage);
                                        break;
                                    case death:
                                        if ("новая игра".equals(ConsoleWindow.this.lastMessage)) {
                                            com.uweeldteam.Main.main(new String[]{"newGame"});
                                            return;
                                        }

                                        ConsoleWindow.this.Println("Вы мертвы, начните новую игру");
                                        break;
                                    default:
                                        throw new IllegalStateException();
                                }

                                ConsoleWindow.this.Print(ConsoleWindow.this.defaultText());
                                ConsoleWindow.this.lastMessage = "";
                                break;
                            case 32:
                                ConsoleWindow.this.Print(" ");
                                var10000 = ConsoleWindow.this;
                                var10001 = var10000.lastMessage;
                                var10000.lastMessage = var10001 + keyEvent.getKeyChar();
                            default:
                                if (this.CheckChar(keyEvent.getKeyChar())) {
                                    ConsoleWindow.this.Print(keyEvent.getKeyChar());
                                    var10000 = ConsoleWindow.this;
                                    var10001 = var10000.lastMessage;
                                    var10000.lastMessage = var10001 + keyEvent.getKeyChar();
                                }
                        }

                    }

                    public void keyTyped(KeyEvent keyEvent) {
                    }

                    public void keyReleased(KeyEvent keyEvent) {
                    }
                });
                this.background.setSize(this.window.getSize());
                this.scroll.setLayout(new ScrollPaneLayout());
                this.background.setBackground(Color.BLACK);
                this.scroll.getVerticalScrollBar().setBackground(Color.BLACK);
                this.scroll.getHorizontalScrollBar().setBackground(Color.BLACK);
                this.scroll.getVerticalScrollBar().getFocusCycleRootAncestor().setForeground(Color.BLACK);
                this.scroll.getViewport().setBackground(Color.BLACK);
                this.console.setForeground(Color.GREEN);
                this.scroll.setSize(this.background.getWidth() + 20, this.background.getHeight() - 38);
                this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
                this.console.setSize(this.scroll.getSize());
                this.console.setVerticalAlignment(1);
                this.console.setHorizontalAlignment(2);

                try {
                    this.console.setFont(Font.createFont(0, new FileInputStream("main_font.ttf")).deriveFont(0, 13.0F));
                } catch (IOException | FontFormatException var6) {
                    var6.printStackTrace();
                }

                this.window.setVisible(true);
                this.FormatText();
                (new Thread(() -> {
                    int times = 0;

                    while(true) {
                        new WaitForSeconds(0.05F);
                        ++times;
                        JLabel var10000;
                        String var10001;
                        if (times > 10 && times < 20) {
                            var10000 = this.console;
                            var10001 = this.text.replaceAll("\n", "<br>>&nbsp;");
                            var10000.setText("<html>>&nbsp;" + var10001.replaceAll(" ", "&nbsp;") + "</html>");
                        } else {
                            var10000 = this.console;
                            var10001 = this.text.replaceAll("\n", "<br>>&nbsp;");
                            var10000.setText("<html>>&nbsp;" + var10001.replaceAll(" ", "&nbsp;") + "|</html>");
                        }

                        if (times == 20) {
                            times = 0;
                        }
                    }
                })).start();
            } catch (Exception var7) {
                com.uweeldteam.Main.ExceptionOccurred(var7);
            }

            try {
                throw new NullPointerException();
            } catch (Exception var5) {
                com.uweeldteam.Main.ExceptionOccurred(var5);
            }
        }

        void FormatText() {
            JLabel var10000 = this.console;
            String var10001 = this.text.replaceAll("\n", "<br>>&nbsp;");
            var10000.setText("<html>>&nbsp;" + var10001.replaceAll(" ", "&nbsp;") + "</html>");
            this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
        }

        public void Print(Object... objects) {
            ArrayList<String> text = new ArrayList();
            Object[] var3 = objects;
            int var4 = objects.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object object = var3[var5];
                if (!object.toString().equals("")) {
                    text.add(object.toString());
                }
            }

            String Text;
            for(Iterator var7 = text.iterator(); var7.hasNext(); this.text = String.format("%s%s", this.text, Text)) {
                Text = (String)var7.next();
            }

            (new Thread(() -> {
                new WaitForSeconds(0.05F);
                this.FormatText();
            })).start();
        }

        public void Close() {
            this.window.setVisible(false);
        }

        String defaultText() {
            try {
                return com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.normal ? "Город -> " : (com.uweeldteam.Main.Engine().Game().gameState == Engine.GameState.fight ? "Бой -> " : "Мёртв -> ");
            } catch (NullPointerException var2) {
                return "Город -> ";
            }
        }

        public void Println(Object... objects) {
            ArrayList<String> text = new ArrayList();
            Object[] var3 = objects;
            int var4 = objects.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object object = var3[var5];
                if (!object.toString().equals("") && !object.toString().replaceAll(" {2}", " ").equals(" ")) {
                    text.add(object.toString());
                }
            }

            Iterator var7 = text.iterator();

            while(var7.hasNext()) {
                String Text = (String)var7.next();
                this.Print(Text + "\n");
            }

            this.FormatText();
        }
    }

    public static enum GameState {
        fight,
        normal,
        death;

        private GameState() {
        }
    }
}
