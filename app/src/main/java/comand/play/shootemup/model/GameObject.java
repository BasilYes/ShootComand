package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Абстрактный класс GameObject содержит в себе базовую логику всех двигающихся объектов игры.
 * @author Василий Реуков
 * @version 1.0
 */
public abstract class GameObject {
    public Point location;
    public Point speed;

    /**
     * Конструктор класса GameObject уставливает координаты и скорость объекта.
     * @param location
     * @param speed
     */
    GameObject(Point location, Point speed){
        this.location = location;
        this.speed = speed;
    }

    /**
     * Абстрактный метод isPointInside для проверки соприкосновения точек.
     * @param point
     * @return
     */
    public abstract boolean isPointInside(final Point point);

    /**
     * Абстрактный метод isCollision для проверки соприкосновения объектов.
     * @param collision
     * @return
     */
    public abstract boolean isCollision(GameObject collision);

    /**
     * Абстрактный метод getPoints для получения координат объекта.
     * @return
     */
    public abstract Point[] getPoints();

    /**
     * Абстрактный класс Draw для отрисовки графики объектов.
     * @param canvas
     * @param paint
     */
    public abstract void Draw(Canvas canvas, Paint paint);

    /**
     * Метод Tick задающий нормальное движение объектов по экрану.
     * @param deltaTime
     */
    public void Tick(float deltaTime){
        location.x += speed.x*deltaTime;
        location.y += speed.y*deltaTime;
    }
}
