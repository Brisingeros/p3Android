package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class BigBullet extends Sprite {

    private double speedFactor;
    private double elapsedTime;

    private boolean colisionado;

    private SpaceShipPlayer parent;

    public BigBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.special_shoot);

        colisionado = false;
        speedFactor = gameEngine.pixelFactor * -300d / 2000d;
        elapsedTime = 0;
        type = types.indexOf("disparo")
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;

        if ((elapsedTime > 250 && gameEngine.theInputController.isFiring) || (colisionado)){
            explode(gameEngine);
        }
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBigBullet(this);
        }

        elapsedTime += elapsedMillis;
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
        elapsedTime = 0;
    }

    @Override
    public boolean isColliding() {
        return false;
    }

    @Override
    public void onCollision(Sprite sprite) {
        this.colisionado = true;
    }

    public void explode(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        //Create explosion
        Explosion explode = new Explosion(gameEngine);
        explode.init(this.positionX + imageWidth/2, this.positionY);
        gameEngine.addGameObject(explode);
        // And return it to the pool
        parent.releaseBigBullet(this);
    }
}
