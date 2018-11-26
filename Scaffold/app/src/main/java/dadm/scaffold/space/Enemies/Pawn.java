package dadm.scaffold.space.Enemies;

import java.util.ArrayList;
import java.util.Random;

import dadm.scaffold.PerlinNoise;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.DiagonalBullet;

public class Pawn extends Enemy {

    public int toGo;
    public boolean toChange;

    private static final long TIME_BETWEEN_BULLETS = 3250;

    private PerlinNoise rail;

    private Random rnd = new Random(System.currentTimeMillis());

    public Pawn(GameEngine gameEngine, PerlinNoise perlin, float offsetY) {
        super(gameEngine, R.drawable.ship3);
        numLifes = 1;

        speedFactor = pixelFactor * 100d / 3000d;
        positionY = offsetY;

        timeSinceLastFire = rnd.nextInt(500);

        pointsOnDestroy = 45;

        rail = perlin;
        toGo = 0;
        toChange = false;

        if(type != -1)
            initBulletPool(gameEngine);
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
}
