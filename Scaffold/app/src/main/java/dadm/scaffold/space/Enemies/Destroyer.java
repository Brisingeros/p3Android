package dadm.scaffold.space.Enemies;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.input.InputController;

public class Destroyer extends Enemy {

    private int direction;
    private double distance;
    private double MAX_DISTANCE;
    private double JUMP_DISTANCE;
    private final int MAX_ENEMIES_ROW = 6;


    private Random rnd = new Random(System.currentTimeMillis());

    public Destroyer(GameEngine gameEngine, int posEnemy) {
        super(gameEngine, R.drawable.ship6);

        positionY = (posEnemy/MAX_ENEMIES_ROW) * (imageHeight + 20) - this.imageHeight*2.3f;

        if(posEnemy >= MAX_ENEMIES_ROW){
            posEnemy = posEnemy - MAX_ENEMIES_ROW;
        }

        positionX = (imageWidth * posEnemy) + 20;

        MAX_DISTANCE = gameEngine.width / 6;
        JUMP_DISTANCE = gameEngine.height / 16;
        distance = 0;
        direction = 1;

        speedFactor = pixelFactor * 100d / 4000d;
        numLifes = rnd.nextInt(2)+1;
    }

    @Override
    protected void initBulletPool(GameEngine gameEngine) {

    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);

        if (positionY > maxY){
            gameEngine.removeGameObject(this);
        }
    }

    @Override
    protected void updatePosition(long elapsedMillis, InputController inputController) {
        distance += speedFactor  * elapsedMillis;
        positionX += direction * speedFactor  * elapsedMillis;
        if (distance > MAX_DISTANCE) {
            distance = 0;
            direction *= -1;
            positionY += JUMP_DISTANCE;
        }
    }

    @Override
    protected void checkFiring(long elapsedMillis, GameEngine gameEngine) {

    }
}
