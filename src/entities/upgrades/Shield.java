package entities.upgrades;

import entities.ships.player.Player;
import entities.ships.player.ShieldedPlayer;
import main.Main;

/**
 * Megnövekedett védelem fejlesztés
 */
public class Shield extends PowerUp {
    public Shield(double x, double y){
        super(x, y, Main.game.textures.get("shield"), Main.game.textures.get("shieldbg"));
    }


    /**
     * A játékosból Shieleded lesz
     * @return Az új ShieldedPlayer
     */
    @Override
    public Player pickup() {
        return new ShieldedPlayer();
    }
}
