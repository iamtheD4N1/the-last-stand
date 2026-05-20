package entities.upgrades;

import entities.ships.player.Player;
import entities.ships.player.ShifterPlayer;
import main.Main;

/**
 * Kockázatos játék fejlesztése az alternatív
 * dimenzióba lépéssel
 */
public class Shift extends PowerUp {
    public Shift(double x, double y){
        super(x, y, Main.game.textures.get("shift"), Main.game.textures.get("shiftbg"));
    }

    /**
     * A játékosból Shifter lesz
     * @return Az új ShifterPLayer
     */
    @Override
    public Player pickup() {
        return new ShifterPlayer();
    }
}
