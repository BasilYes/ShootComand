package comand.play.shootemup.model;

import static comand.play.shootemup.controller.GameView.openSound;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class Player extends GameObject {
    public int bulletCount = 1;
    float size = 0.12f;
    private float fireStatus = 0.4f;
    private float fireReating = 0.45f;

    public Player(Point location) {
        super(location, new Point(0,0));
    }

    @Override
    public boolean isPointInside(Point point) {
        return -1.5*(point.x - location.x) - size*0.65f < point.y - location.y &&
                1.5*(point.x - location.x) - size*0.65f < point.y - location.y &&
                0.666666f*(point.x - location.x) + size*0.65f > point.y - location.y &&
                -0.666666f*(point.x - location.x) + size*0.65f > point.y - location.y;
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
        return new Point[]{
                new Point(location.x, location.y + 0.65f*size),
                new Point(location.x, location.y - 0.65f*size),
                new Point(location.x - 0.60f*size, location.y + 0.25f*size),
                new Point(location.x + 0.60f*size, location.y + 0.25f*size)
        };
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        float multiple = GameView.displaySize.x;
        paint.setColor(Color.argb(255,196,196,196));
        Path path = new Path();
        path.moveTo(location.x*multiple, (location.y + 0.65f*size)*multiple);
        path.lineTo(location.x*multiple, (location.y - 0.65f*size)*multiple);
        path.lineTo((location.x - 0.60f*size)*multiple, (location.y + 0.25f*size)*multiple);
        path.lineTo(location.x*multiple, (location.y + 0.65f*size)*multiple);
        path.close();
        canvas.drawPath(path,paint);
        paint.setColor(Color.argb(255,140,140,140));
        path = new Path();
        path.moveTo(location.x*multiple, (location.y + 0.65f*size)*multiple);
        path.lineTo(location.x*multiple, (location.y - 0.65f*size)*multiple);
        path.lineTo((location.x + 0.60f*size)*multiple, (location.y + 0.25f*size)*multiple);
        path.lineTo(location.x*multiple, (location.y + 0.65f*size)*multiple);
        path.close();
        canvas.drawPath(path,paint);
    }

    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
        fireStatus-=deltaTime;

        if (fireStatus<= 0){
            float bSize= 0.02f;
            fireStatus = fireReating;
            float bulletStatus = (1.0f - 1.0f/fireReating)*2.0f + 1.0f ;
            switch (bulletCount){
                case 1:
                    GameController.bullet.add(new Bullet(
                            new Point(location.x, location.y-size*0.65f),
                            new Point(0,bulletStatus)
                            , bSize, true));
                    openSound(0);
                    break;
                case 2:
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    break;
                case 3:
                    GameController.bullet.add(new Bullet(
                            new Point(location.x, location.y-size*0.65f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    break;
                case 4:
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.3f, location.y-size*0.2f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.6f, location.y+size*0.25f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.6f, location.y+size*0.25f),
                            new Point(0,-bulletStatus)
                            , bSize, true));
                    break;
                default:
                    GameController.bullet.add(new Bullet(new Point(location.x, location.y-size*0.65f),
                            new Point(0,-1.0f)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.3f, location.y-size*0.2f),
                            new Point(0,-1.0f)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.3f, location.y-size*0.2f),
                            new Point(0,-1.0f)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x + size*0.6f, location.y+size*0.25f),
                            new Point(0,-1.0f)
                            , bSize, true));
                    GameController.bullet.add(new Bullet(
                            new Point(location.x - size*0.6f, location.y+size*0.25f),
                            new Point(0,-1.0f)
                            , bSize, true));
                    break;
            }
            }
        }

    public void moveTo(float x, float y)
    {
        location.x = x;
        location.y = y;
    }
}
