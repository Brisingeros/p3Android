package dadm.scaffold.space.Enemies;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class Pawn extends Enemy {

    protected Pawn(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ship3);

        numLifes = 1;
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
