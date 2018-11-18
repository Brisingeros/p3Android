package dadm.scaffold.space.Enemies;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Enemy extends Sprite {

    protected static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    protected long timeSinceLastFire;

    protected double speedFactor;

    protected Enemy(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
    }

    @Override
    public boolean isColliding() {
        return false;
    }

    @Override
    public void onCollision(Sprite sprite) {

        if(sprite.getType() == 2 && sprite.pa)
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    public abstract void shoot();

}
