package entities.upgrades;

import entities.ships.player.AmpedPlayer;
import entities.ships.player.Player;
import main.Main;

/**
 * A lőerfő fejlesztés
 */
public class AmpUp extends PowerUp {
    public AmpUp(double x, double y){
        super(x, y, Main.game.textures.get("amped"), Main.game.textures.get("ampedbg"));
    }

    /**
     * A játékosból AmpedUp lesz
     * @return Az új AmpedPlayer
     */
    @Override
    public Player pickup() {
        return new AmpedPlayer();
    }
}
