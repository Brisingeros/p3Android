package dadm.scaffold.space.Enemies;

import java.util.Random;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.space.Ship;

public abstract class Enemy extends Ship {

    protected long timeSinceLastFire;

    protected Enemy(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
    }

    @Override
    public void onCollision(GameEngine gameEngine) {
        numLifes--;

        if (numLifes == 0)
            gameEngine.removeGameObject(this);

    }

}
