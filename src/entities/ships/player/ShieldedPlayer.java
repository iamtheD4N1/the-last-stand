package entities.ships.player;

import entities.bullets.Bullet;
import main.Main;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A védelemben fejlesztett játékos
 * Van egy pajzsa ami levéd 1 lövést
 */
public class ShieldedPlayer extends Player{
    boolean isShielded;

    public ShieldedPlayer(){
        super(Main.game.textures.get("shieldplayer"));
        isShielded = false;
    }

    /**
     * Jobbklikre a pajzsot ki/be állítjuk
     */
    @Override
    public void special(){
        isShielded = !isShielded;
    }

    /**
     * ha pajzsunk fent van, elpbb az tűnik el nem mi
     * @param bullet A tesztelendő lövedék
     * @return igaz ha eltaláltak minket
     */
    @Override
    public boolean hitcheck(Bullet bullet){
        if(isShielded) {
            if(bullet.getShooter()==this)
                return false;
            double dx = bullet.getPosX()-getPosX();
            double dy = bullet.getPosY()-getPosY();

            double distance = Math.sqrt((dx*dx)+(dy*dy));

            if(distance*Main.game.getResX() < ((getTexture().getHeight()*2)/Math.sqrt(2))){
                isShielded = false;
                return true;
            }
            return false;
        }
        return super.hitcheck(bullet);
    }

    /**
     * Ha pajzsunk fent van, azt is rajzoljuk ki
     * @param g A JPanel Graphics-e
     * @param o A JPanel maga
     */
    @Override
    public void draw(Graphics g, ImageObserver o){
        super.draw(g, o);
        if(isShielded){
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.BLUE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.33));
            int x = (int)(getPosX()*main.Main.getX());
            int y = (int)(getPosY()*main.Main.getX());
            g2.translate(-getTexture().getHeight(), -getTexture().getWidth());
            g2.fillOval(x, y, getTexture().getHeight()*2, getTexture().getWidth()*2);
            g2.translate(getTexture().getHeight(), getTexture().getWidth());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }

}
