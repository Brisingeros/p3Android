package dadm.scaffold.space.Enemies;

import dadm.scaffold.PerlinNoise;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.input.InputController;

public class Pawn extends Enemy {

    public int toGo;
    public boolean toChange;

    private PerlinNoise rail;

    public Pawn(GameEngine gameEngine, PerlinNoise perlin, float offsetY) {
        super(gameEngine, R.drawable.ship3);
        numLifes = 1;

        speedFactor = pixelFactor * 100d / 3000d;
        positionY = offsetY;

        rail = perlin;
        toGo = 0;
        toChange = false;
    }

    @Override
    protected void initBulletPool(GameEngine gameEngine) {

    }

    @Override
    protected void updatePosition(long elapsedMillis, InputController inputController) {
        positionY += elapsedMillis * speedFactor;
        positionX = rail.getValue((float) (positionY + 600.0f), this);
    }

    @Override
    protected void checkFiring(long elapsedMillis, GameEngine gameEngine) {

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
