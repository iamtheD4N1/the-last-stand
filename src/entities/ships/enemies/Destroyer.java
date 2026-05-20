package entities.ships.enemies;

import main.Main;

/**
 * A nagy méretű ellenfél
 * Csak konstruktort tartalmazza a tökéletes belállításokkal
 */
public class Destroyer extends Enemy{
    public Destroyer() {
        super(0.00001, 0.0005, 0.35, 2000, 5, Main.game.textures.get("e3"));
    }
}
