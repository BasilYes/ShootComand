package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameView;

public class Bullet extends GameObject{
    public Bullet(Point location, Point speed, Float size, boolean friend) {
        super(location, speed);
        this.size = size;
        this.friend = friend;
    }

    float size = 0.02f;
    boolean friend;

    @Override
    public boolean isPointInside(Point point) {
        return false;
    }

    @Override
    public boolean isCollision(GameObject collision) {
        return false;
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{
                new Point(location.x, location.y - size),
                new Point(location.x, location.y + size)
        };
    }

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

    @Override
    public void Tick(float deltaTime) {
        super.Tick(deltaTime);
    }
}