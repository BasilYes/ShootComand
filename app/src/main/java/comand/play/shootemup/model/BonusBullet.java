package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

/**
 * Класс BonusBullet наследуется от класса Bonus и представляет собой классический бонус с
 * дополнительной пулей (БДП).
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see Bonus
 */
public class BonusBullet extends Bonus{
    /**
     * Конструктор класса BonusBullet, вызывает super-конструктор класса Bonus.
     * @param location
     * @param size
     */
    public BonusBullet(Point location, float size) {
        super(location, size);
    }

    /**
     * Метод bonusDestroyed откладывает таймер очков появления следующего БДП на 100 игровых секунд.
     */
    @Override
    public void bonusDestroyed() {
        GameController.gameController.addBullTimer = 100.0f;
    }

    /**
     * Метод getBonus увеличивает количество пуль игрока на 1 и увеличивает таймер очков на
     * появление следующего БДП.
     */
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

    /**
     * Метод Draw отрисовывает графику БДП.
     * @param canvas
     * @param paint
     */
    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 0, 255, 0));
        float multiple = GameView.displaySize.x;
        Path path = new Path();
        Point[] myPath = { new Point(location.x * multiple, location.y * multiple),
                new Point((location.x - 0.05f) * multiple, (location.y + 0.04f) * multiple),
                new Point((location.x - 0.05f)  * multiple, (location.y + 0.08f) * multiple),
                new Point((location.x + 0.05f) * multiple, (location.y + 0.08f) * multiple),
                new Point((location.x + 0.05f)  * multiple, (location.y + 0.04f) * multiple),
                new Point(location.x * multiple, location.y * multiple)};
        // переходим в первую точку рисования
        path.moveTo(myPath[0].x, myPath[0].y);
        // рисуем отрезки по заданным точкам
        for (int i = 1; i < myPath.length; i++){
            path.lineTo(myPath[i].x, myPath[i].y);
        }

        canvas.drawPath(path, paint);
    }
}
