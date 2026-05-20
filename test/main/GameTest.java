package main;

import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class GameTest {
    private static Game game;

    @BeforeClass
    public static void ini() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        game = new Game(1280, 720, new JFrame("Test"));
    }

    @Test
    public void StartingPoint(){
        Assert.assertEquals(1, game.getShips().size());
    }

    @Test
    public void PlayerDamage(){
        Assert.assertThrows("GAME OVER", RuntimeException.class, ()-> game.player.damage(1));
    }

    @AfterClass
    public static void finish(){
        game = null;
    }

}