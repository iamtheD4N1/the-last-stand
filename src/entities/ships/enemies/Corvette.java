package entities.ships.enemies;

import main.Main;

/**
 * A közepes méretű ellenfél
 * Csak konstruktort tartalmazza a tökéletes belállításokkal
 */
public class Corvette extends Enemy{
    public Corvette() {
        super(0.0001, 0.002, 0.25, 4000, 3, Main.game.textures.get("e2"));
    }
}
