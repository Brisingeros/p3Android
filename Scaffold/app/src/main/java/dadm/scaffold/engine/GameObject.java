package dadm.scaffold.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {

    protected List<String> types = new ArrayList<>(); //jugador, enemigo, disparo
    protected int type;

    public GameObject(){

        this.types.add("jugador");
        this.types.add("enemigo");
        this.types.add("disparo");

    }

    public abstract void startGame();

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine);

    public abstract void onDraw(Canvas canvas);

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public void onAddedToGameUiThread(){
    }

    public void onPointsEvent(int gameEvent){}
    public void onLivesEvent(int actualLifes) {}
    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){
    }

    public int getType(){
        return type;
    }

}
