package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameView;

/**
 * Класс Bullet наследуетя от класса GameObject и хранит в себе данные о снарядах.
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see GameObject
 */
public class Bullet extends GameObject{
    /**
     * Конструктор класса Bullet, задает размер и приндлежность игрок/враг. Вызывает super констр
     * уктор класса GameObject.
     * @param location
     * @param speed
     * @param size
     * @param friend
     */
    public Bullet(Point location, Point speed, Float size, boolean friend) {
        super(location, speed);
        this.size = size;
        this.friend = friend;
    }

    float size = 0.02f;
    boolean friend;

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
     * Метод getPoints возвращает текущие координаты пули.
     * @return
     */
    @Override
    public Point[] getPoints() {
        return new Point[]{
                new Point(location.x, location.y - size),
                new Point(location.x, location.y + size)
        };
    }

    /**
     * Метод Draw отрисовывает графику пули.
     * @param canvas
     * @param paint
     */
    @Override
    public void Draw(Canvas canvas, Paint paint) {
        if(friend)
            paint.setColor(Color.argb(255,0,255,0));
        else
            paint.setColor(Color.argb(255,255,0,0));
        Float multiple = GameView.displaySize.x;
        canvas.drawRect((location.x - 0.5f * size) * multiple,
                (location.y - size) * multiple,
                (location.x + 0.5f * size) * multiple,
                (location.y + size) * multiple,
                paint);
    }

    /**
     * Метод Tick вызывает super метод Tick и передет ему параметр deltaTime
     * @param deltaTime
     */
    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
    }
}