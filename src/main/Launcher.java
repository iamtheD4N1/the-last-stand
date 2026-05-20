package main;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

/**
 * Ez kis ablak a program indítója, ami a toplistát is kezeli.
 */
public class Launcher extends JPanel {
    /**
     * Egy szerű szöveg, ami csak annyit ír: Enter your name
     */
    private final JLabel nametext = new JLabel();
    /**
     * Ide kell beírni a nevet
     */
    private final JTextField name = new JTextField(5);
    /**
     * Játék indítása gomb
     */
    private final JButton launch = new JButton("PLAY");
    /**
     * A hang ki/be kapcsoló gomb
     */
    private final JToggleButton sound = new JToggleButton("SOUND  ON");
    /**
     * A kilépés gomb
     */
    private final JButton exit = new JButton("EXIT");
    /**
     * A toplistát kiíró szöveg
     */
    private final JTextArea scores = new JTextArea();

    /**
     * A legjobb 10 Scoret tartalmazó lista
     */
    protected final LinkedList<Score> toplist = new LinkedList<>();

    /**
     * A játékinditó gomb Listnereje
     */
    private static class LaunchListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Main.launchwindow.setVisible(false);
                Main.startGame();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * A hang kapcsoló Listenerje
     */
    private class SoundListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.soundToggle();
            if(Main.getsound())
                sound.setText("SOUND  ON");
            else
                sound.setText("SOUND OFF");
        }
    }

    /**
     * A kilépő gomb Listenerje
     */
    private static class ExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.launchwindow.dispose();
            System.exit(0);
        }
    }

    public Launcher(int x, int y){
        setSize(new Dimension(x, y));
        readscores();
        updateScores();

        nametext.setText("Enter your name:");
        add(nametext);
        add(name);
        launch.addActionListener(new LaunchListener());
        add(launch);
        sound.addActionListener(new SoundListener());
        add(sound);
        scores.setEditable(false);
        add(scores);
        exit.addActionListener(new ExitListener());
        add(exit);
    }

    /**
     * A beírt név és pontszámát menti a pontszámok listába
     * @param n A pontszám
     */
    public void addScore(int n){
        Score score = new Score(name.getText(), n);
        int index = toplist.size();
        for(Score iter : toplist){
            if(iter.score < score.score){
                index = toplist.indexOf(iter);
                break;
            }
        }
        toplist.add(index, score);
        if(toplist.size() == 11)
            toplist.removeLast();
        writescores();
    }

    /**
     * Kiírja a toplist.txt-be a toplistát
     */
    public void writescores(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/res/toplist.txt"));
            for(Score score : toplist)
                out.writeObject(score);
            out.close();
            updateScores();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Beolvassa a toplist.txt fileból a toplistát
     */
    public void readscores(){
        try {
            toplist.clear();
            FileInputStream fin = new FileInputStream("src/res/toplist.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            while(fin.available() != 0)
                toplist.add((Score) oin.readObject());
            fin.close();
            oin.close();
        } catch (IOException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
        }
    }

    /**
     * A toplista kiírását frissíti
     */
    public void updateScores(){
        scores.setText("TOPLIST:\n");
        int i = 1;
        for(Score score : toplist){
            scores.setText(scores.getText() + "\n" + i++ + ". " + score.name + ": " + score.score);
        }
    }
}
