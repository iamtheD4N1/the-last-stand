package entities.bullets;

import entities.Entity;
import entities.ships.Ship;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A lövedékek osztálya
 * Elmenti hogy mennyit sebet és ki lőtte
 * Önmagát nem tudja megsebezni a lövő, de a csapattársait igen
 */
public class Bullet extends Entity {
    private Ship shooter;
    private int dmg;

    public Bullet(double x0, double y0, double x, double y, double vel, Ship shooter){
        super(x0, y0, x, y);
        setDx(vel*Math.sin(getRot()));
        setDy(vel*Math.cos(getRot()));
        this.shooter = shooter;
        dmg = 1;
    }

    public Bullet(double x0, double y0, double x, double y, double vel, int dmg, Ship shooter){
        super(x0, y0, x, y);
        setDx(vel*Math.sin(getRot()));
        setDy(vel*Math.cos(getRot()));
        this.dmg = dmg;
        this.shooter = shooter;
    }

    /**
     * Az adott irányba arréblépteti
     */
    @Override
    public void update() {
        setPosX(getPosX()+getDx());
        setPosY(getPosY()+getDy());
    }

    /**
     * A lövedéknek nincs textúrája csak egy fehér kör
     * @param g A JPanel Graphics-e
     * @param o A JPanel maga
     */
    @Override
    public void draw(Graphics g, ImageObserver o){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        int x = (int)(getPosX()*main.Main.getX());
        int y = (int)(getPosY()*main.Main.getX());
        g2.translate(-10, -10);
        g2.fillOval(x, y, 20, 20);
        //g2.fillRect(x, y, 20, 20);
        g2.translate(10, 10);
    }

    public Ship getShooter() {
        return shooter;
    }

    public int getDmg() {
        return dmg;
    }
}
