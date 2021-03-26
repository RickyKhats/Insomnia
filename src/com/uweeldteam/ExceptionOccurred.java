package com.uweeldteam;

import sun.swing.SwingUtilities2;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;

import static java.awt.BorderLayout.SOUTH;

public class ExceptionOccurred {
    public ExceptionOccurred(Throwable e) {
        final JFrame exceptionWindow = new JFrame("Возникло исключение!");
        exceptionWindow.setSize(300, 500);
        JLabel text = new JLabel((String.format("<html>App version : 0.1 <br>%s<br>%s<br>exception occurred in <br> {4}%s<br>Возникла исключительная ситуация, которую я не продумал!<br>Вы можете отправить нам лог ошибки<br>Мы намного быстрее её исправим<br>Так же вы можете попробовать продолжить работу программы<br>Это может привести к ещё большим ошибкам, и, даже повреждению сохранений игры.</html>", new Date().toString(), e.getLocalizedMessage(), Arrays.toString(
                e.getStackTrace()).substring(1, Arrays.toString(
                e.getStackTrace()).length() - 1).replaceAll("\n", "<br>").
                replaceAll(",", "<br>   at "))).
                replaceAll(" ", "&nbsp;"));
        text.setVerticalAlignment(1);
        text.setHorizontalAlignment(2);
        text.setForeground(Color.RED);
        JScrollPane scroll = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
                    while (true)
                        if (!sendMessageWindow.isEnabled() && closeApplication.isSelected()) {
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
        exceptionWindow.add(panel, SOUTH);
        exceptionWindow.setVisible(true);

        try {
            Main.Engine().Window().Close();
        } catch (NullPointerException ignored) {
        }

    }
}
