package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Background extends Sprite {

    private double speedFactor;
    private double elapsedTime;

    private Background otherBg;


    public Background(GameEngine gameEngine, float posX, float posY) {

        super(gameEngine, R.drawable.background);

        this.positionX = posX;
        this.positionY = posY;

        speedFactor = gameEngine.pixelFactor * 300d / 2000d;
        elapsedTime = 0;
        type = -1;

    }

    @Override
    public void onCollision(GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedFactor * elapsedMillis;

        if (positionY > imageHeight) {
            // And return it to the pool
            positionY = otherBg.positionY -this.imageHeight + 5;
        }

        elapsedTime += elapsedMillis;

    }

    public int getImageHeight(){
        return this.imageHeight;
    }

    public void setBg(Background bg){
        otherBg = bg;
    }
}
