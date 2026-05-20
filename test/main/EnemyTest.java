package main;

import entities.ships.Ship;
import entities.ships.enemies.Corvette;
import entities.ships.enemies.Fighter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class EnemyTest {

    @BeforeClass
    public static void ini() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Main.game = new Game(1280, 720, new JFrame("Test"));
    }

    @Test
    public void FindingPath(){
        Ship e1 = new Fighter();
        double dx = Main.game.player.getPosX()-e1.getPosX();
        double dy = Main.game.player.getPosY()-e1.getPosY();
        Main.game.getShips().add(e1);

        e1.update();

        Assert.assertTrue((dx < 0) == (e1.getDa() > 0));
        Assert.assertTrue((dx > 0) == (e1.getDd() > 0));
        Assert.assertTrue((dy < 0) == (e1.getDw() > 0));
        Assert.assertTrue((dy > 0) == (e1.getDs() > 0));
    }

    @Test
    public void Shoot(){
        Corvette e2 = new Corvette();
        Main.game.ships.add(e2);
        e2.update();

        Assert.assertEquals(0, Main.game.bullets.size());

        e2.fire(Main.game.player.getPosX(), Main.game.player.getPosY());
        Assert.assertEquals(1, Main.game.bullets.size());
    }

    @AfterClass
    public static void finish(){
        Main.game.timer.stop();
        Main.game = null;
    }

}
