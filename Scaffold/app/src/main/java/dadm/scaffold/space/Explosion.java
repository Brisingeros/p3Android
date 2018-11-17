package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Explosion extends Sprite {

    private SpaceShipPlayer parent;

    private double elapsedTime;

    public Explosion(GameEngine gameEngine){
        super(gameEngine, R.drawable.explosion);

        elapsedTime = 0;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        if (elapsedTime > 300){
            gameEngine.removeGameObject(this);
        } else {
            elapsedTime += elapsedMillis;
        }

    }


    public void init(double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        elapsedTime = 0;
    }

    @Override
    public void onCollision() {

    }
}
