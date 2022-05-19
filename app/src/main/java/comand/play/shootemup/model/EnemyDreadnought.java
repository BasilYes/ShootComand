package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class EnemyDreadnought extends Enemy{
    float size = 0.14f;
    private float fireStatus = 0.5f;
    private float fireReating = 1f;

    public EnemyDreadnought(Point location, Point speed, int health) {
        super(location, speed, health);
        this.health *= 1.2;
        this.speed.y = speed.y / 2;
    }

    @Override
    public boolean isPointInside(Point point) {
        return -2*(point.x - location.x) + size > point.y - location.y &&
                2*(point.x - location.x) + size > point.y - location.y &&
                (point.x - location.x) - size*0.5f < point.y - location.y &&
                -(point.x - location.x) - size*0.5f < point.y - location.y;
    }

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

    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
        if (speed.x > 0)
            speed.x -= 0.1f;
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
