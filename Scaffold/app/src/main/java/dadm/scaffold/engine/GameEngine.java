package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Enemies.Destroyer;
import dadm.scaffold.space.Projectile;
import dadm.scaffold.space.Ship;

public class GameEngine {

    private static final int INITIAL_DESTROYER_AMOUNT = 48;
    private static final int INITIAL_PAWN_AMOUNT = 5;

    private int numDestroyers;

    private List<Destroyer> destroyers;
    private List<PawnSpawner> spawnersPawn;

    private Random rnd = new Random(System.currentTimeMillis());

    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;

    private int gamePoints;

    private long timeToSpawnDestroyers;
    private long timeToSpawnPawns;

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

        timeToSpawnDestroyers = rnd.nextInt(5000) + 4000;
        timeToSpawnPawns = rnd.nextInt(5000) + 4000;

        gamePoints = 0;

    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void initGameEngine(){
        spawnersPawn = new ArrayList<>(INITIAL_PAWN_AMOUNT);
        destroyers = new ArrayList<>(INITIAL_DESTROYER_AMOUNT);

        for (int i = 0; i < spawnersPawn.size(); i++){
            spawnersPawn.add(new PawnSpawner(this));
        }

        for (int i = 0; i < destroyers.size(); i++){
            destroyers.add(new Destroyer(this));
        }

        numDestroyers = destroyers.size();
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        initGameEngine();

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

        //Spawners

        timeToSpawnDestroyers -= elapsedMillis;
        timeToSpawnPawns -= elapsedMillis;

        spawnEnemies();
    }

    public Destroyer getDestroyer(){
        if (destroyers.isEmpty())
            return null;

        numDestroyers--;
        return destroyers.remove(0);
    }

    public PawnSpawner getPawnSpawner(){
        if (spawnersPawn.isEmpty())
            return null;

        return spawnersPawn.remove(0);
    }

    public void spawnEnemies(){

        if (timeToSpawnDestroyers < 0 && numDestroyers >= 12){
            timeToSpawnDestroyers = rnd.nextInt(5000) + 4000;

            for(int i = 0; i < 12; i++){

                Destroyer des = getDestroyer();

                if (des != null){
                    des.init(i);
                    addGameObject(des);
                }
            }
        }

        if (timeToSpawnPawns < 0){
            timeToSpawnPawns = rnd.nextInt(5000) + 4000;

            PawnSpawner pw = getPawnSpawner();

            if (pw != null){
                pw.init();
            }
        }

    }

    public void releaseDestroyer(Destroyer des){
        destroyers.add(des);
        numDestroyers++;
    }

    public void releasePawnSpawner(PawnSpawner pw){
        spawnersPawn.add(pw);
    }

    public void onDraw() {
        theGameView.draw();
    }

    public void onPointsEvent (int gameEvent) {
        // We notify all the GameObjects
        int numObjects = gameObjects.size();
        addPoints(gameEvent);
        for (int i = 0; i < numObjects; i++) {
            gameObjects.get(i).onPointsEvent(gameEvent);
        }
    }

    public void onLivesEvent (int actualLifes) {
        // We notify all the GameObjects
        int numObjects = gameObjects.size();
        for (int i = 0; i < numObjects; i++) {
            gameObjects.get(i).onLivesEvent(actualLifes);
        }
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

    public int getGamePoints() {
        return gamePoints;
    }

    public void gameOver(){
        this.stopGame();
        ((ScaffoldActivity)mainActivity).endGame(gamePoints);
    }
}