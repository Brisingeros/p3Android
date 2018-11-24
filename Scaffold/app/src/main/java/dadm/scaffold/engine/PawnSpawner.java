package dadm.scaffold.engine;

import dadm.scaffold.PerlinNoise;
import dadm.scaffold.space.Enemies.Pawn;

public class PawnSpawner {

    private PerlinNoise perl;
    private GameEngine game;

    public PawnSpawner(GameEngine GE){
        game = GE;
        perl = new PerlinNoise(game);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                spawnPawn();
            }
        });

        t.start();

    }

    private void spawnPawn(){
        for (int i = 0; i < 5; i++){
            try {
                game.addGameObject(new Pawn(game, perl, -500));
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return;
    }

}
