package main;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.LinkedList;

public class LauncherTest {
    private static Launcher launcher;
    private static LinkedList<Score> old;

    @BeforeClass
    public static void ini(){
        old = new LinkedList<>();
        launcher = new Launcher(400, 200);
        old.addAll(launcher.toplist);
    }

    @Before
    public void CreateLauncher(){
        //launcher = new Launcher(400, 200);
        launcher.toplist.clear();
    }

    @After
    public void DeleteList(){
        launcher.toplist.clear();
    }

    @Test
    public void ToplistEmpty(){
        Assert.assertEquals(0, launcher.toplist.size());
    }

    @Test
    public void ToplistAdd(){
        launcher.addScore(1000);
        Assert.assertEquals(1, launcher.toplist.size());
    }

    @Test
    public void ToplistOrder(){
        launcher.addScore(800);
        launcher.addScore(2000);
        launcher.addScore(1000);
        launcher.addScore(100);
        Assert.assertTrue(launcher.toplist.get(0).score > launcher.toplist.get(1).score);
        Assert.assertTrue(launcher.toplist.get(1).score > launcher.toplist.get(2).score);
        Assert.assertTrue(launcher.toplist.get(2).score > launcher.toplist.get(3).score);
    }

    @Test
    public void ToplistMax10(){
        for(int i = 0; i < 10; i++)
            launcher.addScore(i*1000);
        Assert.assertEquals(10, launcher.toplist.size());
        launcher.addScore(5000);
        Assert.assertEquals(10, launcher.toplist.size());
    }

    @AfterClass
    public static void finish(){
        launcher.toplist.clear();
        launcher.toplist.addAll(old);
        launcher.writescores();
    }
}