//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.uweeldteam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;
import sun.swing.SwingUtilities2;
import uweellibs.MonoBehaviour;

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
            } catch (IndexOutOfBoundsException var2) {
                engine = new Engine(false);
            }
        } catch (Exception var3) {
            ExceptionOccurred(var3);
        }

    }

    public static Engine Engine() {
        return engine;
    }

    public static void ExceptionOccurred(Exception e) {
        ExceptionOccurred(e, false);
    }

    static void ExceptionOccurred(Exception e, boolean hasThis) {
        final JFrame exceptionWindow = new JFrame("Возникло исключение!");
        exceptionWindow.setSize(300, 500);
        String var10002 = (new Date()).toString();
        JLabel text = new JLabel(("<html>App version : 0.1 <br>" + var10002 + "<br>" + e.getLocalizedMessage() + "<br>exception occurred in " + Arrays.toString(e.getStackTrace()).substring(1, Arrays.toString(e.getStackTrace()).length() - 1).replaceAll("\n", "<br>").replaceAll(",", "<br>   at ") + "<br>Возникло исключение!<br>Вы можете отправить нам лог ошибки<br>Мы намного быстрее её исправим<br>Так же вы можете попробовать продолжить работу программы<br>Это может привести к ещё большим ошибкам, и, даже повреждению сохранений игры.</html>").replaceAll(" ", "&nbsp;"));
        text.setVerticalAlignment(1);
        text.setHorizontalAlignment(2);
        text.setForeground(Color.RED);
        JScrollPane scroll = new JScrollPane(text, 20, 30);
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                g.setColor(Color.BLACK);
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }

            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (!thumbBounds.isEmpty() && this.scrollbar.isEnabled()) {
                    g.translate(thumbBounds.x, thumbBounds.y);
                    g.setColor(Color.GRAY);
                    SwingUtilities2.drawRect(g, 0, 0, thumbBounds.width, thumbBounds.height + 50);
                }

            }
        });
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                g.setColor(Color.BLACK);
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }

            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (!thumbBounds.isEmpty() && this.scrollbar.isEnabled()) {
                    g.translate(thumbBounds.x, thumbBounds.y);
                    g.setColor(Color.GRAY);
                    SwingUtilities2.drawRect(g, 0, 0, thumbBounds.width, thumbBounds.height + 50);
                }

            }
        });
        exceptionWindow.add(scroll);
        text.setSize(scroll.getSize());
        final JCheckBox sendMessage = new JCheckBox("Отправить лог ошибки");
        final JCheckBox closeApplication = new JCheckBox("Закрыть игру");
        JButton nextStep = new JButton("Продолжить");
        JPanel panel = new JPanel();
        sendMessage.setBackground(Color.BLACK);
        sendMessage.setForeground(Color.LIGHT_GRAY);
        sendMessage.setFocusPainted(false);
        closeApplication.setBackground(Color.BLACK);
        closeApplication.setForeground(Color.LIGHT_GRAY);
        closeApplication.setFocusPainted(false);
        closeApplication.setSelected(true);
        nextStep.setBackground(Color.BLACK);
        nextStep.setForeground(Color.LIGHT_GRAY);
        nextStep.setFocusPainted(false);
        nextStep.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                exceptionWindow.dispose();
                JFrame sendMessageWindow = new JFrame("Репорт");
                if (sendMessage.isSelected()) {
                    JTextArea message = new JTextArea();
                    message.setToolTipText("Сообщение");
                    JButton send = new JButton("Отправить");
                    JPanel panel = new JPanel();
                    JScrollPane scroll = new JScrollPane(message);
                    scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
                        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                            g.setColor(Color.BLACK);
                            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                        }

                        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                            if (!thumbBounds.isEmpty() && this.scrollbar.isEnabled()) {
                                g.translate(thumbBounds.x, thumbBounds.y);
                                g.setColor(Color.GRAY);
                                SwingUtilities2.drawRect(g, 0, 0, thumbBounds.width, thumbBounds.height + 50);
                            }

                        }
                    });
                    scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                            g.setColor(Color.BLACK);
                            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                        }

                        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                            if (!thumbBounds.isEmpty() && this.scrollbar.isEnabled()) {
                                g.translate(thumbBounds.x, thumbBounds.y);
                                g.setColor(Color.GRAY);
                                SwingUtilities2.drawRect(g, 0, 0, thumbBounds.width, thumbBounds.height + 50);
                            }

                        }
                    });
                    panel.add(send);
                    panel.setBackground(Color.GRAY);
                    scroll.getViewport().setForeground(Color.BLACK);
                    message.setForeground(Color.GREEN);
                    message.setBackground(Color.BLACK);
                    send.setForeground(Color.LIGHT_GRAY);
                    send.setBackground(Color.BLACK);
                    sendMessageWindow.add(scroll);
                    sendMessageWindow.add(panel, "South");
                    sendMessageWindow.setResizable(false);
                    sendMessageWindow.setSize(250, 400);
                    sendMessageWindow.setVisible(true);
                }

                (new Thread(() -> {
                    while(sendMessageWindow.isEnabled()) {
                    }

                    if (closeApplication.isSelected()) {
                        System.exit(1);
                    }

                })).start();
            }

            public void mousePressed(MouseEvent mouseEvent) {
            }

            public void mouseReleased(MouseEvent mouseEvent) {
            }

            public void mouseEntered(MouseEvent mouseEvent) {
            }

            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        panel.add(sendMessage);
        panel.add(closeApplication);
        panel.add(nextStep);
        panel.setBackground(Color.BLACK);
        exceptionWindow.setResizable(false);
        exceptionWindow.add(panel, "South");
        exceptionWindow.setVisible(true);

        try {
            Engine().Window().Close();
        } catch (NullPointerException var10) {
            if (!hasThis) {
                exceptionWindow.dispose();
                ExceptionOccurred(var10, true);
            }
        }

    }
}
