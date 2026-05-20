package entities.ships.enemies;

import entities.bullets.Bullet;
import main.Main;

/**
 * A legkisebb ellenfél, lőni nem tud, cserébe a játékost zavarja, blokkolja lövedékeit
 */
public class Fighter extends Enemy{
    public Fighter() {
        super(0.0001, 0.005, 0.02, 200000, 1, Main.game.textures.get("e1"));
    }

    /**
     * Ez a hajó arra néz, amerre mozog, és nem amerre a játékos van
     */
    @Override
    public void update(){
        super.update();
        setRot(Math.atan(-(getDx())/(getDy())));
        if(getDy() > 0)
            setRot(getRot()+Math.PI);
    }

    /**
     * Ez a hajó nem tud lőni, ez a fv nem csinál semmit
     * @param x A játékos x koordinátája
     * @param y A játékos y koordinátája
     */
    @Override
    public void fire(double x, double y){}

    /**
     * Ennek a halyónak megnöveljük a hitboxát, mert nehéz eltalálni
     * @param bullet A tesztelendő lövedék
     * @return Igaz ha eltaláltuk
     */
    @Override
    public boolean hitcheck(Bullet bullet){
        if(bullet.getShooter()==this)
            return false;
        double dx = bullet.getPosX()-getPosX();
        double dy = bullet.getPosY()-getPosY();

        double distance = Math.sqrt((dx*dx)+(dy*dy));

        if(distance*Main.game.getResX() < (getTexture().getHeight()*2)){
            damage(bullet.getDmg());
            return true;
        }
        return false;
    }
}
