package dadm.scaffold;

import java.util.Random;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.space.Enemies.Pawn;

public class PerlinNoise {

    private float Amplitude;
    private float WaveLength;

    private float M, A, C, Z;

    private float [] puntosToGo;

    public PerlinNoise(GameEngine gameEngine){

        Amplitude = gameEngine.width / 2;
        WaveLength = (gameEngine.height + 600) / 8;

        M = 4294967296.0f;
        A = 1664525.0f;
        C = 1;

        Z = (float) Math.floor(Math.random() * M);

        puntosToGo = new float[9];

        for (int i = 0; i < puntosToGo.length; i++){
            puntosToGo[i] = rand();
        }

    }

    private float rand(){
        Z = (A*Z + C) % M;
        return Z / M - 0.5f;
    }

    private float interpolate(float pa, float pb, float px){
        float ft = (float) (px * Math.PI);
        float f = (float) ((1.0f - Math.cos(ft)) * 0.5f);

        return pa * (1-f) + pb * f;
    }

    public float getValue(float x, Pawn peon){

        float y;
        float a = puntosToGo[peon.toGo];
        float b = puntosToGo[peon.toGo+1];

        if(x % WaveLength <= 2.0f && !peon.toChange){

            peon.toChange = true;
            peon.toGo++;
            y = Amplitude + a * Amplitude;

        } else {
            y = Amplitude + interpolate(a, b, (x % WaveLength) / WaveLength)  * Amplitude;

            if (x % WaveLength > 2.0f && peon.toChange)
                peon.toChange = false;
        }

        return y;

    }

}
