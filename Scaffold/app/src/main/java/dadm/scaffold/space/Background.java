package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Background extends Sprite {

    private double speedFactor;
    private double elapsedTime;


    public Background(GameEngine gameEngine) {

        super(gameEngine, R.drawable.background);

        speedFactor = gameEngine.pixelFactor * -300d / 2000d;
        elapsedTime = 0;
        type = -1;

    }

    @Override
    public void onCollision(GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedFactor * elapsedMillis;

        if (positionY < -imageHeight) {
            // And return it to the pool
            positionY = 0;
        }

        elapsedTime += elapsedMillis;

    }
}
