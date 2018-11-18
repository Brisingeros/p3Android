package dadm.scaffold.space;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Projectile extends Sprite {

    protected double speedFactor;
    protected double elapsedTime;

    protected Ship parent;

    protected Projectile(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);

        type = types.indexOf("disparo");
    }

    public void init(Ship parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
        elapsedTime = 0;
    }

}
