package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Ship {

    private static final long TIME_BETWEEN_BULLETS = 250;
    private static final long TIME_BETWEEN_BIGBULLETS = 3000;

    private long timeSinceLastFire;
    private long timeSinceLastBigFire;

    public SpaceShipPlayer(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        //maxX = gameEngine.width - imageWidth;
        //maxY = gameEngine.height - imageHeight;

        type = types.indexOf("jugador");
        numLifes = 3;

        if(type != -1)
            initBulletPool(gameEngine);
    }

    protected void initBulletPool(GameEngine gameEngine) {

        bullets = new ArrayList<>();
        bigbullets = new ArrayList<>();

        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
        for (int i=0; i<INITIAL_BIGBULLET_POOL_AMOUNT; i++) {
            bigbullets.add(new BigBullet(gameEngine));
        }
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY - (maxY / 6);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    protected void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    protected void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS){//(gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = (Bullet) getBullet("bullet");
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }

        /////////////////////////////

        if (gameEngine.theInputController.isFiring && timeSinceLastBigFire > TIME_BETWEEN_BIGBULLETS){//(gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            BigBullet bullet = (BigBullet) getBullet("bigbullet");
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastBigFire = 0;
        }
        else {
            timeSinceLastBigFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine) {
        numLifes--;

        if (numLifes == 0){
            //TODO: End game
            gameEngine.removeGameObject(this);
        }
    }
}
