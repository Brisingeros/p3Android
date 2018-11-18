package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Ship extends Sprite {

    protected static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    protected static final int INITIAL_BIGBULLET_POOL_AMOUNT = 1;
    protected static final int INITIAL_DIAGONALBULLET_POOL_AMOUNT = 6;
    protected List<Bullet> bullets;
    protected List<BigBullet> bigbullets;

    protected Ship(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
    }

    protected Projectile getBullet(String type) {

        List aux = null;

        switch (type){
            case "bullet":
                aux = bullets;
                break;

            case "bigbullet":
                aux = bigbullets;
                break;

            case "diagonalbullet":
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
                break;
        }

    }

}
