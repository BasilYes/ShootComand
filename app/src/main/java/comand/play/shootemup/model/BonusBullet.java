package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;


public class BonusBullet extends Bonus{
    public BonusBullet(Point location, float size) {
        super(location, size);
    }

    @Override
    public void bonusDestroyed() {
        GameController.gameController.addBullTimer = 100.0f;
    }

    @Override
    public void getBonus() {
        GameView.gameView.openSound(2);
        GameController.player.bulletCount++;
        switch (GameController.player.bulletCount){
            case 2:
                GameController.gameController.addBullTimer = 1000.0f;
                break;
            case 3:
                GameController.gameController.addBullTimer = 1500.0f;
                break;
            case 4:
                GameController.gameController.addBullTimer = 2500.0f;
                break;
            default:
                GameController.gameController.addBullTimer = 999999999999999999.0f;
                break;
        }
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 0, 255, 0));
        float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
}
