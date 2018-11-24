package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class DiagonalBullet extends Projectile {

    private int diagonal;

    public DiagonalBullet(GameEngine gameEngine, int diag) {
        super(gameEngine, R.drawable.bullet);

        diagonal = diag;
        speedFactor = gameEngine.pixelFactor * -200d / 1000d;
        type = types.indexOf("disparo");
    }

    @Override
    public void onCollision(GameEngine gameEngine) {
        parent.releaseBullet(this, "diagonalbullet");
        gameEngine.removeGameObject(this);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedFactor * elapsedMillis * factor;
        positionX += speedFactor * elapsedMillis * diagonal / 2;

        if((positionY > gameEngine.height) || (positionY < -imageHeight) || (positionX > gameEngine.width) || (positionX < -imageWidth)){
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this, "diagonalbullet");
        }

    }
}
