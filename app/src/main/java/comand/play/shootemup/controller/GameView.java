package comand.play.shootemup.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import comand.play.shootemup.model.Point;
import comand.play.shootemup.view.StatsFragment;
import comand.play.shootemup.model.GameObject;

public class GameView extends View {

    static public Point displaySize;
    public GameController gameController;
    public TextView pointsView = null;
    public StatsFragment statsFragment = null;
    private boolean onGame = false;

    public void setOnGame(boolean onGame) {
        this.onGame = onGame;
        gameController.health = 4;
    }

    public GameView(Context context) {
        super(context);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        displaySize = new Point(metrics.widthPixels, metrics.heightPixels);

        this.gameController = new GameController(this);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GameController.player.moveTo(motionEvent.getX()/displaySize.x
                        , motionEvent.getY()/displaySize.x);
                return true;
            }
        });

        new CountDownTimer(Integer.MAX_VALUE, 16){
            @Override
            public void onTick(long l) {
                gameController.updateDeltaTime();
                gameController.starTick();
                if (onGame)
                    gameController.Tick();
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

        for (GameObject obj: gameController.stars){
            obj.Draw(canvas, paint);
        }

        gameController.player.Draw(canvas, paint);
        for (GameObject obj: gameController.enemy){
            obj.Draw(canvas, paint);
        }
        for (GameObject obj: gameController.eBullet){
            obj.Draw(canvas, paint);
        }
        for (GameObject obj: gameController.bullet){
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
}