package entities.ships.player;

import entities.bullets.Bullet;
import main.Main;

/**
 * Az erőben fejlesztett játékos
 * Megnövelt tűzerő
 */
public class AmpedPlayer extends Player{

    public AmpedPlayer(){
        super(Main.game.textures.get("ampedplayer"));
    }

    /**
     * A jákos lövedékei megölnek mindenkit egy találattal
     * @param x az egér x koordinátája
     * @param y az egér y koordinátája
     */
    @Override
    public void fire(double x, double y){
        Main.game.addBullet(new Bullet(getPosX()+getDx(), getPosY()+getDy(), x, y, 0.06, 9999,this));
    }
}
