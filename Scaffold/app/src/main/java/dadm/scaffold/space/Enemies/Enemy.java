package dadm.scaffold.space.Enemies;

import java.util.Random;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.space.Ship;

public abstract class Enemy extends Ship {

    protected Random rnd = new Random(System.currentTimeMillis());

    protected long timeSinceLastFire;
    protected int pointsOnDestroy;

    protected Enemy(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);

        type = types.indexOf("enemigo");

    }

}
