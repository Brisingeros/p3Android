package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Enemies.Destroyer;
import dadm.scaffold.space.Projectile;
import dadm.scaffold.space.Ship;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();

    public int numEnemy = 0;
    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;

    private int gamePoints;

    private Activity mainActivity;

    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;

        gamePoints = 0;

    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();

    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            gameObjects.add(gameObject);
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = gameObjects.size();

        List<Ship> aliados = new ArrayList<>();
        List<Ship> enemigos = new ArrayList<>();
        List<Projectile> balasAliados = new ArrayList<>();
        List<Projectile> balasEnemigos = new ArrayList<>();

        GameObject aux = null;

        for (int i = 0; i < numGameObjects; i++) {
            //gameObjects.get(i).onUpdate(elapsedMillis, this);
            aux = gameObjects.get(i);
            switch (aux.getType()){ //jugador, enemigo, disparo
                case 0:
                    aliados.add((Ship) aux);
                    break;

                case 1:
                    enemigos.add((Ship) aux);
                    break;

                case 2:
                    Projectile projAux = (Projectile) aux;

                    if (projAux.getParentType() == 0){
                        balasAliados.add(projAux);
                    } else {
                        balasEnemigos.add(projAux);
                    }

                    break;

                default:
                    break;
            }

            aux.onUpdate(elapsedMillis,this);
        }

        for (Ship s: aliados) {
            for (Projectile p: balasEnemigos) {
                if(s.collisionAABB((Sprite) p)){
                    p.onCollision(this);
                    s.onCollision(this);
                }
            }

            for (Ship e: enemigos) {
                if(s.collisionAABB((Sprite) e)){
                    e.onCollision(this);
                    s.onCollision(this);
                }
            }
        }

        for (Ship s: enemigos) {
            for (Projectile p: balasAliados) {
                if(s.collisionAABB((Sprite) p)){
                    p.onCollision(this);
                    s.onCollision(this);
                }
            }

            for (Ship e: aliados) {
                if(s.collisionAABB((Sprite) e)){
                    e.onCollision(this);
                    s.onCollision(this);
                }
            }
        }

        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                gameObjects.remove(objectsToRemove.remove(0));
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }

    public void addPoints(int pointsOnDestroy) {
        gamePoints += pointsOnDestroy;
    }
}
