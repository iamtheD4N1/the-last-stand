package entities.ships;

import entities.Entity;
import entities.bullets.Bullet;
import main.Main;

import java.awt.image.BufferedImage;

/**
 * A hajók absztakt osztálya
 * A surlódási tényezőt tárolják és a maximális sebességet
 * Valamint a életpontot és mikor ltőtt utoljára
 */
public abstract class Ship extends Entity {
    double strafe, vmax;
    /**
     * Mind a 4 irányt külön kezeljük, igaz hogy 2-2 egymás ellentétje
     * Ez a surlódás implementációnak követelménye.
     */
    double dw = 0, da = 0, ds = 0, dd = 0;
    long lastshot;
    int hp;

    public Ship(double x, double y, double st, double v, int health, BufferedImage t){
        super(x, y, t);
        strafe = st;
        vmax = v;
        lastshot = System.currentTimeMillis()+1000;
        hp = health;
    }

    /**
     * 1 irányban lellenőrzi, hogy gyorsulnia kéne-e arra, vagy lassulnia
     * @param v A sebesség
     * @param key ha igaz gyorsul, ha hamis lassul
     * @return az új sebesség abban az irányban
     */
    protected double checkmove(double v, boolean key){
        if(key) {
            v += getStrafe();
            if(v > vmax)
                v = vmax;
        }
        else {
            v -= strafe;
            if(v < 0)
                v = 0;
        }
        return v;
    }

    /**
     * Tickenként hívódik
     * Megnézi mind a 4 irányban, hogy merre kéne mennie
     */
    protected void move(){
        setDx(dd-da);
        setDy(ds-dw);
        double denom = Math.sqrt((getDx()*getDy())+(getDy()*getDy()));

        if(denom > getVmax()){
            double ratio = (getVmax()/denom);
            setDx(getDx()*ratio);
            setDy(getDy()*ratio);
        }
        setPosX(getPosX()+getDx());
        setPosY(getPosY()+getDy());
    }

    /**
     * Lellenőrzi hogy eltalálta-e egy lövedék
     * @param bullet A tesztelendő lövedék
     * @return Igaz ha talált
     */
    public boolean hitcheck(Bullet bullet){
        if(bullet.getShooter()==this)
            return false;
        double dx = bullet.getPosX()-getPosX();
        double dy = bullet.getPosY()-getPosY();

        double distance = Math.sqrt((dx*dx)+(dy*dy));

        if(distance*Main.game.getResX() < (getTexture().getHeight())){
            damage(bullet.getDmg());
            return true;
        }
        return false;
    }

    /**
     * Lövünk az egér irányába
     * @param x az egér x koordinátája
     * @param y az egér y koordinátája
     */
    public void fire(double x, double y){
        Main.game.addBullet(new Bullet(getPosX()+getDx(), getPosY()+getDy(), x, y, 0.02, this));
    }

    /**
     * Ha talált, veszítsen életpontot
     * @param dmg Veszítendő életpont
     */
    public void damage(int dmg){
        hp-=dmg;
    }

    public double getStrafe() {
        return strafe;
    }

    public double getVmax() {
        return vmax;
    }

    public double getDw() {
        return dw;
    }

    public double getDa() {
        return da;
    }

    public double getDs() {
        return ds;
    }

    public double getDd() {
        return dd;
    }

    public long getLastshot(){
        return lastshot;
    }

    public int getHp() {
        return hp;
    }

    public void setVmax(double vmax) {
        this.vmax = vmax;
    }

    public void setDw(double dw) {
        this.dw = dw;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public void setDs(double ds) {
        this.ds = ds;
    }

    public void setDd(double dd) {
        this.dd = dd;
    }

    public void setLastshot(long lastshot) {
        this.lastshot = lastshot;
    }
}
