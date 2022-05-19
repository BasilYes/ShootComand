package comand.play.shootemup.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import comand.play.shootemup.MainActivity;
import comand.play.shootemup.R;
import comand.play.shootemup.model.Point;
import comand.play.shootemup.view.StatsFragment;
import comand.play.shootemup.model.GameObject;

public class GameView extends View {

    static public GameView gameView;
    static public GameController gameController;
    static public Point displaySize;
    public TextView pointsView = null;
    public StatsFragment statsFragment = null;
    public MainActivity activity;
    private boolean onGame = false;

    public void setAboutUs(boolean aboutUs) {
        this.aboutUs = aboutUs;
    }

    private boolean aboutUs = false;
    private MediaPlayer mediaPlayer = null;
    private SoundPool soundPool = null;
    private int[] soundList = null;
    public void setOnGame(boolean onGame) {
        this.onGame = onGame;
        gameController.health = 4;
    }

    public GameView(Context context) {
        super(context);
        activity = (MainActivity)context;
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        displaySize = new Point(metrics.widthPixels, metrics.heightPixels);

        gameController = new GameController(this, (MainActivity) context);
        gameView = this;
        if (GameController.playMusic)
            startMusic();
        if (GameController.playSound)
            startSound();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!GameController.useGiro)
                    GameController.player.moveTo(motionEvent.getX()/displaySize.x
                            , motionEvent.getY()/displaySize.x);
                return true;
            }
        });

        activity.sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

                // Normalize the rotation vector if it's big enough to get the axis
                if (omegaMagnitude > 1) {
                    axisX /= omegaMagnitude;
                    axisY /= omegaMagnitude;
                    //axisZ /= omegaMagnitude;
                }
                if (GameController.useGiro) {
                    GameController.player.location.y = Math.max(Math.min((axisY + 0.5f), 1.0f), 0.0f)
                            * GameController.gameController.ySize;
                    GameController.player.location.x = Math.max(Math.min((-axisX + 0.5f), 1.0f), 0.0f);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        }, activity.sensor, SensorManager.SENSOR_DELAY_GAME);

        new CountDownTimer(Integer.MAX_VALUE, 16){
            @Override
            public void onTick(long l) {
                gameController.updateDeltaTime();
                gameController.starTick();
                if (onGame)
                    gameController.Tick();
                if(aboutUs)
                    gameController.aboutUsTick();
                invalidate();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        paint.setAntiAlias(false);

        if (pointsView != null)
            pointsView.setText(Integer.toString((int)gameController.points));

        for (GameObject obj: GameController.stars){
            obj.Draw(canvas, paint);
        }

        GameController.player.Draw(canvas, paint);
        for (GameObject obj: GameController.enemy){
            obj.Draw(canvas, paint);
        }
        for (GameObject obj: GameController.eBullet){
            obj.Draw(canvas, paint);
        }
        for (GameObject obj: GameController.bullet){
            obj.Draw(canvas, paint);
        }
        for (GameObject obj: GameController.bonuses){
            obj.Draw(canvas, paint);
        }

        if (onGame){
            int lives = gameController.health;
            float x = 0.02f;
            float y = 0.02f;
            final float s = 0.02f;
            float multiple = displaySize.x;
            while (lives > 0) {
                paint.setColor(Color.argb(255, 217, 82, 82));
                Path path = new Path();
                path.moveTo((x + s) * multiple, y * multiple);
                path.lineTo(x * multiple, (y + s) * multiple);
                path.lineTo((x + s * 2) * multiple, (y + s * 4) * multiple);
                path.lineTo((x + s * 2) * multiple, (y + s) * multiple);
                path.lineTo((x + s) * multiple, y * multiple);
                path.close();
                canvas.drawPath(path, paint);
                lives--;
                if (lives > 0) {
                    paint.setColor(Color.argb(255, 234, 112, 112));
                    path = new Path();
                    path.moveTo((x + s * 2) * multiple, (y + s * 4) * multiple);
                    path.lineTo((x + s * 2) * multiple, (y + s) * multiple);
                    path.lineTo((x + s * 3) * multiple, y * multiple);
                    path.lineTo((x + s * 4) * multiple, (y + s) * multiple);
                    path.lineTo((x + s * 2) * multiple, (y + s * 4) * multiple);
                    path.close();
                    canvas.drawPath(path, paint);
                    lives--;
                }
                x += s * 5;
            }
//            while (lives > 0) {
//                paint.setColor(Color.argb(255, 217, 82, 82));
//                Path path = new Path();
//                path.moveTo((x + s) * multiple, y * multiple);
//                path.lineTo(x * multiple, (y + s) * multiple);
//                path.lineTo((x + s * 2) * multiple, (y + s * 4) * multiple);
//                path.lineTo((x + s * 2) * multiple, (y + s * 2) * multiple);
//                path.lineTo((x + s) * multiple, y * multiple);
//                path.close();
//                canvas.drawPath(path, paint);
//                lives--;
//                if (lives > 0) {
//                    paint.setColor(Color.argb(255, 234, 112, 112));
//                    path = new Path();
//                    path.moveTo((x + s * 2) * multiple, (y + s * 4) * multiple);
//                    path.lineTo((x + s * 2) * multiple, (y + s * 2) * multiple);
//                    path.lineTo((x + s * 3) * multiple, y * multiple);
//                    path.lineTo((x + s * 4) * multiple, (y + s * 2) * multiple);
//                    path.close();
//                    canvas.drawPath(path, paint);
//                    lives--;
//                }
//                x += s * 5;
//            }
        }
    }
    public void startMusic(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.soundtrack);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }
    public void stopMusic(){
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void pauseMusic(){
        if (mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    public void unpauseMusic(){
        if (mediaPlayer!=null){
            mediaPlayer.start();
        }
    }
    public void startSound(){
        if (soundList==null){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(8)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundList =  new int[]{
/////////////////////////////////////////////////////////                    soundPool.load(getContext(), R.raw.shoot, 1),
/////////////////////////////////////////////////////////                    soundPool.load(getContext(), R.raw.bonus, 1),
            };
            Log.d("SoundTest", "  ");
        }
    }
    public void stopSound(){
        if (soundList!=null) {
            soundPool.release();
            soundPool = null;
            soundList = null;
        }
    }
    public void playSound(int id){
        if (soundList!=null)
            if (GameController.playSound)
                soundPool.play(soundList[id], 1, 1, 0, 0, 1);
    }
}