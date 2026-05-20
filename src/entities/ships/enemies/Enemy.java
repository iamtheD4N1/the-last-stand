package entities.ships.enemies;

import entities.bullets.Bullet;
import entities.ships.Ship;
import main.Main;

import java.awt.image.BufferedImage;

/**
 * Az ellenfeleket általánosító absztrakt osztály
 * Tartalmazza, hogy milyen gyakral lőhet
 * És hogy milyen távolságra szeretne lenni a játékostól
 * Az ellenfelek arra néznek amerre a játékos van (kivéve a Fighter)
 */
public abstract class Enemy extends Ship {
    long cd;
    double range;

    public Enemy(double st, double v, double r, long cooldown, int health, BufferedImage t){
        super((Math.random()*2)-0.5, -0.2, st, v, health, t);
        cd = cooldown;
        range = r;
    }


    /**
     * Tickenként meghívódó frissítés, az új pozíciót megkeresi
     * Lő ha tud
     */
    @Override
    public void update() {
        double dx = Main.game.player.getPosX() - getPosX();
        double dy = Main.game.player.getPosY() - getPosY();

        if(Math.sqrt((dx*dx)+(dy*dy)) > range) {
            setDw(checkmove(getDw(), dy < 0));
            setDa(checkmove(getDa(), dx < 0));
            setDs(checkmove(getDs(), dy > 0));
            setDd(checkmove(getDd(), dx > 0));

        }
        else{
            setDw(checkmove(getDw(), dy > 0));
            setDa(checkmove(getDa(), dx > 0));
            setDs(checkmove(getDs(), dy < 0));
            setDd(checkmove(getDd(), dx < 0));

        }
        move();
        setRot(Math.atan(-dx/dy));
        if(dy > 0)
            setRot(getRot()+Math.PI);
        if(System.currentTimeMillis()-getLastshot() > cd) {
            fire(Main.game.player.getPosX(), Main.game.player.getPosY());
            setLastshot(System.currentTimeMillis());
        }
    }

    /**
     * Kilő egy lövedéket a játékos irányába
     * @param x A játékos x koordinátája
     * @param y A játékos y koordinátája
     */
    public void fire(double x, double y){
        Main.game.addBullet(new Bullet(getPosX() + getDx(), getPosY() + getDy(), x, y, 0.015, this));
    }
}
