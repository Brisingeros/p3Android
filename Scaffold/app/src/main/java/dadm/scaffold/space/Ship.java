package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public abstract class Ship extends Sprite {

    protected static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    protected static final int INITIAL_BIGBULLET_POOL_AMOUNT = 1;
    protected static final int INITIAL_DIAGONALBULLET_POOL_AMOUNT = 6;
    protected List<Bullet> bullets;
    protected List<BigBullet> bigbullets;
    protected List<DiagonalBullet> diagonalbullets;

    protected int numLifes;

    protected int maxX;
    protected int maxY;
    protected double speedFactor;

    protected Ship(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
    }

    protected abstract void initBulletPool(GameEngine gameEngine);

    protected Projectile getBullet(String type) {

        List aux = new ArrayList();

        switch (type){
            case "bullet":
                aux = bullets;
                break;

            case "bigbullet":
                aux = bigbullets;
                break;

            case "diagonalbullet":
                aux = diagonalbullets;
                break;
        }

        if (aux.isEmpty()) {
            return null;
        }
        return (Projectile) aux.remove(0);
    }

    protected void releaseBullet(Projectile bullet, String type) {

        switch (type){
            case "bullet":
                bullets.add((Bullet) bullet);
                break;

            case "bigbullet":
                bigbullets.add((BigBullet) bullet);
                break;

            case "diagonalbullet":
                diagonalbullets.add((DiagonalBullet) bullet);
                break;
        }

    }

    protected abstract void updatePosition(long elapsedMillis, InputController inputController);
    protected abstract void checkFiring(long elapsedMillis, GameEngine gameEngine);

}
