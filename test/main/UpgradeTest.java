package main;

import entities.upgrades.AmpUp;
import entities.upgrades.Shield;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.lang.annotation.Target;

import static java.lang.Thread.sleep;

public class UpgradeTest {
    @BeforeClass
    public static void ini() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Main.game = new Game(1280, 720, new JFrame("Test"));
    }

    @Test
    public void PickUp() throws InterruptedException {
        Assert.assertEquals(0, Main.game.powerUps.size());
        Main.game.powerUps.add(new AmpUp(0.5, 0.5));
        Assert.assertEquals(1, Main.game.powerUps.size());
        Main.game.player.setPosX(0.5);
        Main.game.player.setPosY(0.5);
        sleep(20);
        Assert.assertEquals(0, Main.game.powerUps.size());
    }

    @Test
    public void OverridesPlayer(){
        Main.game.player.fire(0, 0);
        Assert.assertEquals(Main.game.bullets.getLast().getShooter(), Main.game.player);
        Assert.assertNotEquals(1, Main.game.bullets.getLast().getDmg());
    }

    @AfterClass
    public static void finish(){
        Main.game.timer.stop();
        Main.game = null;
    }


}
