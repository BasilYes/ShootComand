package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {
    public Point location;
    public Point speed;
    GameObject(Point location, Point speed){
        this.location = location;
        this.speed = speed;
    }
    public abstract boolean isPointInside(final Point point);
    public abstract boolean isCollision(GameObject collision);
    public abstract Point[] getPoints();
    public abstract void Draw(Canvas canvas, Paint paint);
    public void Tick(float deltaTime){
        location.x += speed.x*deltaTime;
        location.y += speed.y*deltaTime;
    }
}
