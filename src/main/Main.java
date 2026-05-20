package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A program indulása
 */
public class Main {
    /**
     * A program ikonja
     */
    static Image icon;
    static boolean sound = true;

    /**
     * A launchert tartalmazó ablak
     */
    static public JFrame launchwindow;

    /**
     * A launcher maga
     */
    public static Launcher launcher;

    /**
     * A játok maga
     */
    public static Game game;
    static int x, y;

    /**
     * Inicializálja az ablakot amiben a launcher van, és launchert
     */
    public static void startLauncher() {
        x = 350;
        y = 350;
        launchwindow = new JFrame("Last Stand Launcher");
        launchwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        launchwindow.setResizable(false);
        launchwindow.setIconImage(icon);
        launcher = new Launcher(x, y);
        launchwindow.add(launcher);
        launchwindow.setBounds(0, 0, x, y);
        //window.pack();
        launchwindow.setLocationRelativeTo(null);

        launchwindow.setVisible(true);
    }

    /**
     * inicializálja az ablakot amiben a játék van és a játékot
     */
    public static void startGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        x = 1280;
        y = 720;
        JFrame window = new JFrame("Last Stand");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, x, y);
        window.setResizable(false);
        window.setIconImage(icon);


        game = new Game(x, y, window);
        window.add(game);
        window.pack();
        window.addKeyListener(game);
        window.addMouseListener(game);
        window.setLocationRelativeTo(null);
        //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //window.setUndecorated(true);

        // Új kulzor
        Toolkit t = Toolkit.getDefaultToolkit();
        BufferedImage c = ImageIO.read(new File("src/res/textures/cursor.png"));
        Cursor cursor = t.createCustomCursor(c, new Point(c.getWidth() / 2, c.getHeight() / 2), "none");
        window.setCursor(cursor);


        window.setVisible(true);
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    static boolean getsound() {
        return sound;
    }

    static void soundToggle() {
        sound = !sound;
    }

    /**
     * A program itt indul. Csak a felugró ablakok ikonját olvassa be, és elindítja a launchert.
     * @param args Parancssori kapcsolók (nincsenek kezelve)
     * @throws UnsupportedAudioFileException Zenével kapcsolatos exception, nem kéne dobnia
     * @throws LineUnavailableException      Zenével kapcsolatos exception, nem klne dobnia
     * @throws IOException                   Ha egy file hiányozna
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        icon = ImageIO.read(new File("src/res/textures/player.png"));
        startLauncher();
    }
}