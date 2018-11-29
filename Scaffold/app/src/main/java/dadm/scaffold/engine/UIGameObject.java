package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.view.View;
import android.widget.TextView;

import dadm.scaffold.R;

public class UIGameObject extends GameObject {
    private final TextView mText;
    private final TextView mTextLives;
    private int mPoints;
    private int mLives;
    private boolean mPointsHaveChanged;
    private boolean mLivesHaveChanged;
    
    public UIGameObject(View view) {
        mText = view.findViewById(R.id.points);
        mTextLives = view.findViewById(R.id.lives);
        type = -1;
    }
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {}

    @Override
    public void startGame() {
        mPoints = 0;
        mLives = 3;
        mText.post(mUpdatePointsRunnable);
        mTextLives.post(mUpdateLivesRunnable);
    }
    @Override
    public void onPointsEvent(int gameEvent) {

        mPoints += gameEvent;
        mPointsHaveChanged = true;

    }

    @Override
    public void onLivesEvent(int actualLives) {

        mLives = actualLives;
        mLivesHaveChanged = true;

    }

    private Runnable mUpdatePointsRunnable = new Runnable() {
        @Override
        public void run() {

            String text = String.valueOf(mPoints);
            mText.setText( "POINTS: " + text);
            
        }
    };

    private Runnable mUpdateLivesRunnable = new Runnable() {
        @Override
        public void run() {

            String text = String.valueOf(mLives);
            mTextLives.setText( "X" + text);

        }
    };
    
    @Override
    public void onDraw(Canvas canvas) {
        if (mPointsHaveChanged) {
            mText.post(mUpdatePointsRunnable);
            mPointsHaveChanged = false;
        }
        
        if(mLivesHaveChanged){
            mTextLives.post(mUpdateLivesRunnable);
            mLivesHaveChanged = false;
        }
    }
}
