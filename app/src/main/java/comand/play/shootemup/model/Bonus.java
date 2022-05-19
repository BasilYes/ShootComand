package comand.play.shootemup.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import comand.play.shootemup.controller.GameController;
import comand.play.shootemup.controller.GameView;

public class Bonus extends GameObject{
    float size;
    public Bonus(Point location, float size) {
        super(location, new Point(0, 0.5f));
        this.size = size;
    }

    @Override
    public boolean isPointInside(Point point) {
        float x = (point.x- location.x);
        float y = (point.y- location.y);
        return x*x + y*y < size*size;
    }

    @Override
    public boolean isCollision(GameObject collision) {
        return false;
    }

    public void getBonus(){
        GameController.gameController.health++;
    }

    public void bonusDestroyed(){}

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

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 0, 255));
        float multiple = GameView.displaySize.x;
        canvas.drawCircle(location.x*multiple, location.y*multiple, size*multiple, paint);
    }
}
