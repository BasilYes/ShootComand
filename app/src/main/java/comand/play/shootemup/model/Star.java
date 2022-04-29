package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameView;

public class Star extends GameObject {
    public float size;

    public Star(Point location, Point speed, float size) {
        super(location, speed);
        this.size = 0.0005f+size*0.002f;
    }

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
        return null;
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));
        float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
}
