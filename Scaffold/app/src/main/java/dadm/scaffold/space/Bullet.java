package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Projectile {

    private double speedFactor;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
        type = types.indexOf("disparo");
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this, "bullet");
        }
    }

    @Override
    public boolean isColliding() {
        return false;
    }

}
