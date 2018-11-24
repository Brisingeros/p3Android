package dadm.scaffold.space;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Projectile extends Sprite {

    protected double speedFactor;
    protected double elapsedTime;
    protected int factor;
    protected Ship parent;

    protected Projectile(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);

        type = types.indexOf("disparo");
    }

    public void init(Ship parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
        factor = parent.getType() == 0?1:-1; //si es 0, el parent es jugador, y el disparo debe ir hacia arriba. Si es 1 es enemigo, debe ir hacia abajo

        elapsedTime = 0;
    }

    public int getParentType(){
        return parent.getType();
    }

}
