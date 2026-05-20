package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A játék absztrakt felépítőköve
 * A poziciót, forgatását, és a pillanatnyi mozdulást tárólja
 * Valamint a kirajzolandó képet
 * A játékban minden kirajzolt elem egy entitás
 */
public abstract class Entity {
    /**
     * posX: 0-1 közötti érték, 0 a kép bal oldala és 1 a kép jobb oldala
     * posY: 0-1/y*x közötti érték, 0 a kép teteje és x/y a kép alja
     * rot: 0-2 a fordulata megadva radiánban
     * dx: pillanatnyi mozdulás az x tengelyen
     * dy: pillanatnyi mozdulás az y tengelyen
     */
    private double posX, posY, rot, dx, dy;
    /**
     * A kirajzolandó jép
     */
    private BufferedImage texture;
    /**
     * A létrehozás pillanata
     */
    long time;

    public Entity(double x, double y, double dir){
        posX = x;
        posY = y;
        rot = dir;
        time = System.currentTimeMillis();
    }

    /**
     * A lövedékeknél használt konstruktor
     * Forgás helyett a cél van megadva
     * @param x0 kezdeti x
     * @param y0 kezdeti y
     * @param x cél x
     * @param y cél y
     */
    public Entity(double x0, double y0, double x, double y){
        posX = x0;
        posY = y0;
        rot = Math.atan((x-x0)/(y-y0));
        if(y-y0 < 0)
            rot+=Math.PI;
        rot %= 2*Math.PI;
        time = System.currentTimeMillis();
    }

    public Entity(double x, double y, BufferedImage t){
        posX = x;
        posY = y;
        rot = 0;
        texture = t;
        time = System.currentTimeMillis();
    }

    /**
     * A minden tickben meghívott update() fügvvény a mozgási logikát tartalmazza
     */
    abstract public void update();

    /**
     * A minden tickben meghívott kirajzoló függvény
     * @param g A JPanel Graphics-e
     * @param o A JPanel maga
     */
    public void draw(Graphics g, ImageObserver o){
        Graphics2D g2 = (Graphics2D) g;
        int shiftx = texture.getWidth()/2;
        int shifty = texture.getHeight()/2;
        int x = (int)(posX*main.Main.getX());
        int y = (int)(posY*main.Main.getX());
        g2.rotate(rot, x, y);
        g2.drawImage(texture, x-shiftx, y-shifty, o);
        g2.rotate(-rot, x, y);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getRot(){
        return rot;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public long getTime(){
        return time;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setRot(double rot) {
        this.rot = rot;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
