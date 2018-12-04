package dadm.scaffold.engine;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.PerlinNoise;
import dadm.scaffold.space.Enemies.Pawn;

public class PawnSpawner {

    private List<Pawn> pawns;

    private static final int INITIAL_PAWN_AMOUNT = 5;

    private int numPawns;

    private PerlinNoise perl;
    private GameEngine game;

    private PawnSpawner that;

    public PawnSpawner(GameEngine GE){
        game = GE;
        that = this;

        numPawns = 0;

        pawns = new ArrayList<>(INITIAL_PAWN_AMOUNT);
        for (int i = 0; i < INITIAL_PAWN_AMOUNT; i++){
            pawns.add(new Pawn(game, that));
        }
    }

    public void init(){
        numPawns = 5;

        that = this;

        perl = new PerlinNoise(game);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                spawnPawn();
            }
        });

        t.start();
    }

    public Pawn getPawn(){
        if (pawns.isEmpty())
            return null;

        return pawns.remove(0);
    }

    public void releasePawn(Pawn paw){
        pawns.add(paw);
        numPawns--;

        if (numPawns == 0){
            game.releasePawnSpawner(this);
        }
    }

    private void spawnPawn(){
        for (int i = 0; i < 5; i++){
            try {
                Pawn aux = getPawn();
                if (aux != null){
                    aux.init(-500);
                    game.addGameObject(aux);
                }

                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return;
    }

    public PerlinNoise getPerl() {
        return perl;
    }
}
