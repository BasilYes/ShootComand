package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class SpiralEnemy extends Enemy{
    float size = 0.05f;
    float fireReating = 1.0f;
    float fireStatus = 0.0f;
    float time = 0.0f;

    public SpiralEnemy(Point location) {
        super(location, new Point(0, 0.5f), 1);
//        if ( location.x > 0.5f)
//            time = 3.14f;
    }

    @Override
    public boolean isPointInside(Point point) {
        return -1.41421*(point.x - location.x) + size > point.y - location.y &&
                1.41421*(point.x - location.x) + size > point.y - location.y &&
                point.y - location.y > -size;
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
                new Point(location.x, location.y + size),
                new Point(location.x-1.41421f, location.y - size),
                new Point(location.x+1.41421f, location.y - size)
        };
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,251,218,221));
        Float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
    Random random = new Random();
    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
        fireStatus-=deltaTime;
        float A = 0.6f;
        time+=deltaTime;
        speed.x = A * (float) Math.sin(time * (float) Math.PI * 2f);
        speed.y = A * (float) Math.cos(time * (float) Math.PI * 2f);
        speed.y += 0.4f;
    }
}
