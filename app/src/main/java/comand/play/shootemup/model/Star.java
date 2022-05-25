package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameView;

/**
 * Класс Star наследуется от GameObject и представляет собой звездочку летящую на заднем фоне.
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see GameObject
 */
public class Star extends GameObject {
    public float size;

    /**
     * Конструктор класса Star вызывает super-конструктор класса GameObject для установки
     * координат и скорости, а затем устанавливает размеры звезд.
     * @param location
     * @param speed
     * @param size
     */
    public Star(Point location, Point speed, float size) {
        super(location, speed);
        this.size = 0.0005f+size*0.002f;
    }

    /**
     * Метод isPointInside всегда возвращает false.
     * @param point
     * @return
     */
    @Override
    public boolean isPointInside(Point point) {
        return false;
    }

    /**
     * Метод isCollision всегда возвращает false.
     * @param collision
     * @return
     */
    @Override
    public boolean isCollision(GameObject collision) {
        return false;
    }

    /**
     * Метод getPoints всегда возвращает false.
     * @return
     */
    @Override
    public Point[] getPoints() {
        return null;
    }

    /**
     * Метод Draw отрисовывает графику звездочки.
     * @param canvas
     * @param paint
     */
    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));
        float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
}
