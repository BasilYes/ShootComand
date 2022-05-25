package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

/**
 * Класс DefaultEnemy наследуется от класса Enemy и представляет собой базового врага в игре.
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see Enemy
 */
public class DefaultEnemy extends Enemy{
    float size = 0.1f;
    float fireReating = 1.5f;
    float fireStatus = 0.0f;

    /**
     * Конструктор класса DefaultEnemy вызывает super-конструктор класса Enemy для установки
     * координат,скорости и здоровья.
     * @param location
     */
    public DefaultEnemy(Point location) {
        super(location, new Point(0, 0.8f), 1);
    }

    /**
     * Метод isPointInside проверяет соприкосновение точки врага с точкой игрока.
     * @param point
     * @return
     */
    @Override
    public boolean isPointInside(Point point) {
        return -2*(point.x - location.x) + size > point.y - location.y &&
                2*(point.x - location.x) + size > point.y - location.y &&
                (point.x - location.x) - size*0.5f < point.y - location.y &&
                -(point.x - location.x) - size*0.5f < point.y - location.y;
    }

    /**
     * Метод isCollision проверяет соприкосновение объекта врага с объектом игрок.
     * @param collision
     * @return
     */
    @Override
    public boolean isCollision(GameObject collision) {
        Point[] points = collision.getPoints();
        for (Point point: points){
            if (isPointInside(point)){
                return true;
            }
        }
        return false;
    }

    /**
     * Метод getPonits возвращает координаты врага.
     * @return
     */
    @Override
    public Point[] getPoints() {
        //return new Point[]{location};
        return new Point[]{
                new Point(location.x, location.y - 0.5f*size),
                new Point(location.x + 0.5f*size, location.y),
                new Point(location.x, location.y + size),
                new Point(location.x - 0.5f*size, location.y)
        };
    }

    /**
     * Метод Draw отрисовывает графику врага.
     * @param canvas
     * @param paint
     */
    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,250,196,196));
        Float multiple = GameView.displaySize.x;
        Path path = new Path();
        path.moveTo(location.x              *multiple, (location.y - 0.5f*size)*multiple);
        path.lineTo(location.x              *multiple, (location.y +      size)*multiple);
        path.lineTo((location.x - 0.5f*size)*multiple, location.y              *multiple);
        path.lineTo(location.x              *multiple, (location.y - 0.5f*size)*multiple);
        path.close();
        canvas.drawPath(path,paint);
        paint.setColor(Color.argb(255,200,140,140));
        path = new Path();
        path.moveTo(location.x              *multiple, (location.y - 0.5f*size)*multiple);
        path.lineTo((location.x + 0.5f*size)*multiple, location.y              *multiple);
        path.lineTo(location.x              *multiple, (location.y +      size)*multiple);
        path.lineTo(location.x              *multiple, (location.y - 0.5f*size)*multiple);
        path.close();
        canvas.drawPath(path,paint);
    }

    /**
     * Метод Tick вызывает super-метод Tick и устаналивает механизм движения врага.
     * @param deltaTime
     */
    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
        fireStatus-=deltaTime;
        if (fireStatus<= 0){
            float bSize= 0.02f;
            fireStatus = fireReating;
            GameController.eBullet.add(new Bullet(new Point(location.x, location.y+size),
                    new Point(0,speed.y*2)
                    , bSize, false));
        }
    }
}