package dadm.scaffold.space.Enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.PerlinNoise;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.PawnSpawner;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.DiagonalBullet;

public class Pawn extends Enemy {

    PawnSpawner parent;

    public int toGo;
    public boolean toChange;

    private static final long TIME_BETWEEN_BULLETS = 3250;

    private PerlinNoise rail;

    private Random rnd = new Random(System.currentTimeMillis());

    public Pawn(GameEngine gameEngine, PawnSpawner pw) {
        super(gameEngine, R.drawable.ship3);

        parent = pw;

        numLifes = 1;
        speedFactor = pixelFactor * 100d / 3000d;
        pointsOnDestroy = 45;

        if(type != -1)
            initBulletPool(gameEngine);
    }

    public void init(float offsetY){
        numLifes = 1;
        positionY = offsetY;
        timeSinceLastFire = rnd.nextInt(500);

        rail = parent.getPerl();
        toGo = 0;
        toChange = false;
    }

    @Override
    protected void initBulletPool(GameEngine gameEngine) {
        diagonalbullets = new ArrayList<>();
        int direct = rnd.nextInt(1000);
        direct = (direct > 500) ? 1 : -1;

        for (int i=0; i<INITIAL_DIAGONALBULLET_POOL_AMOUNT; i++) {
            diagonalbullets.add(new DiagonalBullet(gameEngine, direct));
            direct = -direct;
        }

    }

    @Override
    protected void updatePosition(long elapsedMillis, InputController inputController) {
        positionY += elapsedMillis * speedFactor;
        positionX = rail.getValue((float) (positionY + 600.0f), this);
    }

    @Override
    protected void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS){
            DiagonalBullet bullet = (DiagonalBullet) getBullet("diagonalbullet");
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

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);

        if (positionY > maxY){
            gameEngine.removeGameObject(this);
        }
    }

    public void onCollision(GameEngine gameEngine) {
        numLifes--;

        if (numLifes == 0){
            gameEngine.removeGameObject(this);
            gameEngine.onPointsEvent(pointsOnDestroy);
            parent.releasePawn(this);
        }

    }
}
