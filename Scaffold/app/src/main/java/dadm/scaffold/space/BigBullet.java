package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class BigBullet extends Projectile {

    public BigBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.special_shoot);

        this.speedFactor = gameEngine.pixelFactor * -300d / 2000d;
        elapsedTime = 0;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;

        if ((elapsedTime > 250 && gameEngine.theInputController.isFiring)){
            explode(gameEngine);
        }
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this, "bigbullet");
        }

        elapsedTime += elapsedMillis;
    }

    @Override
    public void onCollision(GameEngine gameEngine) {
        explode(gameEngine);
    }

    public void explode(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        //Create explosion
        Explosion explode = new Explosion(gameEngine);
        explode.init(this.positionX + imageWidth/2, this.positionY);
        gameEngine.addGameObject(explode);
        // And return it to the pool
        parent.releaseBullet(this, "bigbullet");
    }
}
