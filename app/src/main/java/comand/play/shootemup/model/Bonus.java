package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

/**
 * Класс Bonus наследуется от класса GameObject и представляет собой классический вариант бонуса
 * с восстановлением здоровья (БЗ).
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see GameObject
 */
public class Bonus extends GameObject{
    float size;

    /**
     * Конструктор класса Bonus вызывает super-конструктор для установки координат и скорости,
     * а также задает размер.
     * @param location
     * @param size
     */
    public Bonus(Point location, float size) {
        super(location, new Point(0, 0.5f));
        this.size = size;
    }
    /**
     * Метод isPointInside проверяет соприкосновение точек БЗ с игроком.
     */
    @Override
    public boolean isPointInside(Point point) {
        float x = (point.x- location.x);
        float y = (point.y- location.y);
        return x*x + y*y < size*size;
    }
    /**
     * Метод isCollision всегда возвращает false.
     */
    @Override
    public boolean isCollision(GameObject collision) {
        return false;
    }

    /**
     * Метод getBonus восстаналивает здоровье игрока.
     */
    public void getBonus(){
        GameController.gameController.health++;
    }

    /**
     * Метод bonusDestroyed вызывается при уничтожении бонуса
     */
    public void bonusDestroyed(){}

    /**
     * Метод getPoints возвращает координаты Бонуса.
     * @return
     */
    @Override
    public Point[] getPoints() {
        return new Point[]{
                new Point(location.x, location.y + size),
                new Point(location.x, location.y - size),
                new Point(location.x - size, location.y),
                new Point(location.x + size, location.y),
                new Point(location.x - 0.7071f*size, location.y - 0.7071f*size),
                new Point(location.x - 0.7071f*size, location.y + 0.7071f*size),
                new Point(location.x + 0.7071f*size, location.y - 0.7071f*size),
                new Point(location.x + 0.7071f*size, location.y + 0.7071f*size),
        };
    }

    /**
     * Метод Draw отрисовывает графику БЗ.
     * @param canvas
     * @param paint
     */
    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 0, 255));
        float multiple = GameView.displaySize.x;
        paint.setColor(Color.argb(255, 217, 82, 82));
        Path path = new Path();
        path.moveTo((location.x + size) * multiple, location.y * multiple);
        path.lineTo(location.x * multiple, (location.y + size) * multiple);
        path.lineTo((location.x + size * 2) * multiple, (location.y + size * 4) * multiple);
        path.lineTo((location.x + size * 2) * multiple, (location.y + size) * multiple);
        path.lineTo((location.x + size) * multiple, location.y * multiple);
        path.close();
        canvas.drawPath(path, paint);
    }
}
