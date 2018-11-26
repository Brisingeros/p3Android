package dadm.scaffold.input;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import dadm.scaffold.R;

public class JoystickInputController extends InputController {

    private float startingPositionX;
    //private float startingPositionY;

    private final double maxDistance;

    public final JoystickInputController that = this;

    public JoystickInputController(View view) {
        view.findViewById(R.id.joystick_main).setOnTouchListener(new JoystickTouchListener());
        view.findViewById(R.id.joystick_touch).setOnTouchListener(new FireButtonTouchListener());
        /*
        joystick.setBackgroundColor(Color.GREEN);
        shooter.setBackgroundColor(Color.RED);
        */

//        joystick.setBackgroundResource(R.drawable.explosion);
//        shooter.setBackgroundResource(R.drawable.explosion);

        double pixelFactor = view.getHeight() / 400d;
        maxDistance = 50*pixelFactor;
    }

    public void setInvisible(){
//        joystick.setBackgroundResource(R.drawable.ship2);
//        shooter.setBackgroundResource(R.drawable.ship2);
    }

    private class JoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                startingPositionX = event.getX(0);
                //startingPositionY = event.getY(0);
            }
            else if (action == MotionEvent.ACTION_UP) {
                horizontalFactor = 0;
                //verticalFactor = 0;
            }
            else if (action == MotionEvent.ACTION_MOVE) {
                // Get the proportion to the max
                horizontalFactor = (event.getX(0) - startingPositionX) / maxDistance;
                if (horizontalFactor > 1) {
                    horizontalFactor = 1;
                }
                else if (horizontalFactor < -1) {
                    horizontalFactor = -1;
                }
                /*verticalFactor = (event.getY(0) - startingPositionY) / maxDistance;
                if (verticalFactor > 1) {
                    verticalFactor = 1;
                }
                else if (verticalFactor < -1) {
                    verticalFactor = -1;
                }*/
            }
            return true;
        }
    }

    private class FireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                isFiring = true;
            }
            else if (action == MotionEvent.ACTION_UP) {
                isFiring = false;
            }
            return true;
        }
    }
}

