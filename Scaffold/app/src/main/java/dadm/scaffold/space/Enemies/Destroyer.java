package dadm.scaffold.space.Enemies;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class Destroyer extends Enemy {

    private Random rnd = new Random(System.currentTimeMillis());

    protected Destroyer(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship6);

        numLifes = rnd.nextInt(2)+1;
    }

    @Override
    protected void initBulletPool(GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }
}
