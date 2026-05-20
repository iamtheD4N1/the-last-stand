package entities.upgrades;

import entities.Entity;
import entities.ships.player.Player;
import main.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A fejlesztéseket általánosító absztrakt osztály
 * A kirajzolás logikáját tartalmazza és a felvételi feltételt
 */
public abstract class PowerUp extends Entity {
    /**
     * A háttérben lévő particle effect
     */
    BufferedImage bg;
    /**
     * A pillanatnyi átettszőség
     * Folyamatosan csökken
     * 1: teljesen látszik
     * 0: láthatalan
     */
    private float alpha;

    public PowerUp(double x, double y, BufferedImage t, BufferedImage particle){
        super(x, y, t);
        bg = particle;
        alpha = 1;
    }

    /**
     * Minden egyes fejlesztés letrehoz egy új hajót
     * @return Az új fejlesztett Player objektum
     */
    public abstract Player pickup();

    /**
     * Növeljük az átettszőséget
     */
    @Override
    public void update() {
        alpha = ((float)((10000-(System.currentTimeMillis()-getTime()))/10000.0));
        if(alpha > 1)
            alpha = 1;
        else if(alpha < 0)
            alpha = 0;
    }

    /**
     * Elég közel van-e a játékos a fejlesztéshez?
     * @return igaz ha felvettük
     */
    public boolean hitcheck(){
        double dx = Main.game.player.getPosX()-getPosX();
        double dy = Main.game.player.getPosY()-getPosY();

        double distance = Math.sqrt((dx*dx)+(dy*dy));

        return (distance*Main.game.getResX() < ((getTexture().getHeight()+Main.game.player.getTexture().getHeight())/Math.sqrt(2)));
    }

    /**
     * Minden fejlesztés ugyanúgy rajzolódik ki
     * A háttérben a partickle effect forgatjuk, az ikon egyre kevésbé látszódik
     * @param g A JPanel Graphics-e
     * @param o A JPanel maga
     */
    @Override
    public void draw(Graphics g, ImageObserver o){
        Graphics2D g2 = (Graphics2D) g;
        int shiftx = getTexture().getWidth()/2;
        int shifty = getTexture().getHeight()/2;
        int x = (int)(getPosX()*main.Main.getX());
        int y = (int)(getPosY()*main.Main.getX());
        g2.rotate(getRot(), x, y);
        g2.drawImage(bg, x-(bg.getHeight()/2), y-(bg.getWidth()/2), o);
        g2.rotate(-getRot(), x, y);
        setRot(getRot()+0.125);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(getTexture(), x-shiftx, y-shifty, o);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
