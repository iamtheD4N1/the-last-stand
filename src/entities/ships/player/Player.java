package entities.ships.player;

import entities.bullets.Bullet;
import entities.ships.Ship;
import main.Main;

import java.awt.image.BufferedImage;

/**
 * A játékost általánosító absztakt osztály
 * Az épp lenyomott billentyűket tartja még számon
 */
public abstract class Player extends Ship {
    boolean W, A, S, D, space;

    public Player(double x, double y, BufferedImage t){
        super(x, y, 0.002, 0.01, 1, t);
    }

    /**
     * A másoló konstruktor, ami egy fejlesztés felvételekor hívódik meg
     * @param override az új textúra
     */
    public Player(BufferedImage override){
        super(Main.game.player.getPosX(), Main.game.player.getPosY(), Main.game.player.getStrafe(), Main.game.player.getVmax(), Main.game.player.getHp(), override);
        W = Main.game.player.W;
        A = Main.game.player.A;
        S = Main.game.player.S;
        D = Main.game.player.D;
        space = Main.game.player.space;
        setDw(Main.game.player.getDw());
        setDa(Main.game.player.getDa());
        setDs(Main.game.player.getDs());
        setDd(Main.game.player.getDd());
    }

    /**
     * Lellenőrzi a lenyomott billentyűket
     */
    @Override
    public void update(){
        setDw(checkmove(getDw(), W));
        setDa(checkmove(getDa(), A));
        setDs(checkmove(getDs(), S));
        setDd(checkmove(getDd(), D));

        move();
    }

    /**
     * Ha meg lettünk sebezve, meghalunk és GAMEOVER
     * @param dmg Veszítendő életpont
     */
    @Override
    public void damage(int dmg){
        throw new RuntimeException("GAME OVER");
    }

    /**
     * Ez a függvény a jobb klick kezelésért felel
     */
    public void special(){}

    public void pressedW() {
        W = true;
    }

    public void pressedA() {
        A = true;
    }

    public void pressedS() {
        S = true;
    }

    public void pressedD() {
        D = true;
    }

    /**
     * Az adott irényba teleportál minket egy kicsit
     */
    public void pressedSpace(){
        if(W)
            setPosY(getPosY()-0.06);
        if(A)
            setPosX(getPosX()-0.06);
        if(S)
            setPosY(getPosY()+0.06);
        if(D)
            setPosX(getPosX()+0.06);
    }

    public void releasedW() {
        W = false;
    }

    public void releasedA() {
        A = false;
    }

    public void releasedS() {
        S = false;
    }

    public void releasedD() {
        D = false;
    }

    public void releasedSpace(){space = false;}
}
