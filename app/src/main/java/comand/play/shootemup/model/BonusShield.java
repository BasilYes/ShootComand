package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;


public class BonusShield extends Bonus{
    public BonusShield(Point location, float size) {
        super(location, size);
        GameController.gameController.shieldIn = true;
    }

    @Override
    public void bonusDestroyed() {
        GameController.gameController.shieldIn = false;
    }

    @Override
    public void getBonus() {
        GameView.gameView.openSound(3);
        GameController.gameController.shieldCountdown = 5.0f*
                GameController.gameController.getDeltaTimeMultiple();
        GameController.gameController.shieldIn = false;
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 0, 0, 255));
        float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
}