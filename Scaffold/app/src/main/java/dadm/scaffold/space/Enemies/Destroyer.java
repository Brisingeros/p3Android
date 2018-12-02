package dadm.scaffold.space.Enemies;

import java.util.ArrayList;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Bullet;

public class Destroyer extends Enemy {

    private int direction;
    private double distance;
    private double MAX_DISTANCE;
    private double JUMP_DISTANCE;
    private final int MAX_ENEMIES_ROW = 6;

    private static final long TIME_BETWEEN_BULLETS = 4250;

    private int initLifes;

    private Random rnd = new Random(System.currentTimeMillis());

    public Destroyer(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship6);

        MAX_DISTANCE = gameEngine.width / 6;
        JUMP_DISTANCE = gameEngine.height / 16;

        speedFactor = pixelFactor * 100d / 4000d;

        pointsOnDestroy = 20;

        if(type != -1)
            initBulletPool(gameEngine);
    }

    public void init(int posEnemy){

        distance = 0;
        direction = 1;

        positionY = (posEnemy/MAX_ENEMIES_ROW) * (imageHeight + 20) - this.imageHeight*2.3f;

        if(posEnemy >= MAX_ENEMIES_ROW){
            posEnemy = posEnemy - MAX_ENEMIES_ROW;
        }

        positionX = (imageWidth * posEnemy) + 20;

        //

        numLifes = rnd.nextInt(2)+1;
        initLifes = numLifes;

        //

        timeSinceLastFire = rnd.nextInt(500);

    }

    @Override
    protected void initBulletPool(GameEngine gameEngine) {

        bullets = new ArrayList<>();


        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }

    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        updatePosition(elapsedMillis, gameEngine.theInputController);
        if (initLifes == 1)
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
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS){
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
    }

    public void onCollision(GameEngine gameEngine) {
        numLifes--;

        if (numLifes == 0){
            gameEngine.removeGameObject(this);
            gameEngine.onPointsEvent(pointsOnDestroy);
            gameEngine.releaseDestroyer(this);
        }

    }
}
