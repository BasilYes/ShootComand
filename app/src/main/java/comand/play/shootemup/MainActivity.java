package comand.play.shootemup;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;
import comand.play.shootemup.view.MenuFragment;

/**
 * Класс MainActivity наследуется от класса Activity и представляет собой главное явление в
 * приложении.
 * @author Василий Реуков
 * @version 1.0
 * @see Activity
 */
public class MainActivity extends Activity {
    public SensorManager sensorManager;
    public Sensor sensor;

    /**
     * Метод onCreate задает для GameView доступ к классу SensorManager, устанавливает начальный
     * экран, включает игровой HUD, включает звуки, музыку и управление гироскопм.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GameView.gameView == null){

            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

            setContentView(R.layout.activity_main);
            MenuFragment menuFragment = new MenuFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.hud_frame_layout, menuFragment)
                    .commit();
            RelativeLayout relativeLayout = findViewById(R.id.relative_layout);

            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            GameController.useGiro =
                    sharedPreferences.getBoolean(getString(R.string.giro_key), false);
            GameController.playSound =
                    sharedPreferences.getBoolean(getString(R.string.sound_key), true);
            GameController.playMusic =
                    sharedPreferences.getBoolean(getString(R.string.music_key), true);

            new GameView(this);
            GameView.gameView.setKeepScreenOn(true);

            relativeLayout.addView(GameView.gameView);
        }
    }

    /**
     * Метод onPause останавливает музыку и звуки при переходе игры на фон.
     */
    @Override
    protected void onPause() {
        super.onPause();
        GameView.gameView.stopMusic();
        GameView.gameView.stopSound();
    }

    /**
     * Метод onResume проверяет настройки игры и запускает разрешенные музыку и звуки при
     * возвращении в игру.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (GameController.playMusic)
            GameView.gameView.startMusic();
        if (GameController.playSound)
            GameView.gameView.startSound();
    }

    /**
     * Метод onStop останавливает музыку и звуки при остановке игры.
     */
    @Override
    protected void onStop() {
        super.onStop();
        GameView.gameView.stopMusic();
        GameView.gameView.stopSound();
    }
}