package dadm.scaffold.counter;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.engine.PawnSpawner;
import dadm.scaffold.engine.UIGameObject;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.Background;
import dadm.scaffold.space.Enemies.Destroyer;
import dadm.scaffold.space.SpaceShipPlayer;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;
    private int idShipPlayer;

    public View joystick, shooter;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View v = view;
        idShipPlayer = getArguments().getInt("idShip");

        joystick = v.findViewById(R.id.joystick_main);
        shooter = v.findViewById(R.id.joystick_touch);

        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);

                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    joystick.setBackgroundResource(R.drawable.move);
                                    shooter.setBackgroundResource(R.drawable.shoot);
                                }
                            });
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    joystick.setBackgroundResource(0);
                                    shooter.setBackgroundResource(0);
                                }
                            });
                        }
                    }
                });

                t.start();

                Background bg1 = new Background(theGameEngine, 0.0f, 0.0f);
                Background bg2 = new Background(theGameEngine, 0.0f, -bg1.getImageHeight() + 5);

                bg1.setBg(bg2);
                bg2.setBg(bg1);

                theGameEngine.addGameObject(bg1);
                theGameEngine.addGameObject(bg2);
                theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine, idShipPlayer));
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.addGameObject(new UIGameObject(v));
                theGameEngine.startGame();

                /*
                //TODO: Set in GameEngine
                for(int i = 0; i < 12; i++){
                    theGameEngine.addGameObject(new Destroyer(theGameEngine));
                }


                new PawnSpawner(theGameEngine);
                */


            }
        });


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();

        new AlertDialog.Builder(getActivity(),R.style.AlertDialogStyle)
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }
}
