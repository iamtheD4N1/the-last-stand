package entities.ships.player;

import entities.bullets.Bullet;
import main.Main;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Az instabil játékos
 * Atvált egy másik dimenzióban ahol nem találhatják el
 * De nem lőhet
 * Instabilitás miatt bármikor visszakerülhet a játéktérbe
 */
public class ShifterPlayer extends Player{
    boolean shifted;

    public ShifterPlayer(){
        super(Main.game.textures.get("shiftplayer"));
        shifted = false;
    }

    /**
     * Ha shiftelünk lasabban mozgunk és vagy egy kicsi esélyünk visszakerülni
     */
    @Override
    public void update(){

        if(shifted) {
            setVmax(getVmax() / 2);
            super.update();
            setVmax(getVmax() * 2);
            if((int)(Math.random()*1000) < 2)
                shifted = false;
        }
        else
            super.update();
    }

    /**
     * Ha shiftelve vagyunk, nem találhatnak el minket
     * @param bullet A tesztelendő lövedék
     * @return
     */
    @Override
    public boolean hitcheck(Bullet bullet){
        if(shifted)
            return false;
        return super.hitcheck(bullet);
    }

    /**
     * Ha shiftelve vagyunk nem tüzelünk
     * @param x az egér x koordinátája
     * @param y az egér y koordinátája
     */
    @Override
    public void fire(double x, double y){
        if(!shifted)
            super.fire(x, y);
    }

    /**
     * Jobbklickre állítjuk a shift kapcsolót
     */
    @Override
    public void special(){
        shifted = !shifted;
    }

    /**
     * Ha shiftel, halványan rajzolja ki
     * @param g A JPanel Graphics-e
     * @param o A JPanel maga
     */
    @Override
    public void draw(Graphics g, ImageObserver o){
        if(!shifted)
            super.draw(g, o);
        else{
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
            super.draw(g, o);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }
}
