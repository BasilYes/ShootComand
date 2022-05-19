package comand.play.shootemup.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;

import comand.play.shootemup.R;
import comand.play.shootemup.controller.GameView;

public class Sprite extends Bonus{

    static private int last_id;
    static private final String[] urls = {
            "https://vk.com/mirea_official",
            "https://github.com/"
    };
    static private final int[] drawableIds = {
//            R.drawable.vk,
//            R.drawable.github
    };
    private int id;

    public Sprite() {
        super(new Point(), (float)(Math.random()*0.05+0.1));
        location = new Point((float) Math.random()*(1.0f-size), -size);
        id = (last_id++)%urls.length;
    }

    @Override
    public boolean isPointInside(Point point) {
        return point.x > location.x &&
                point.y > location.y &&
                point.x < location.x+size &&
                point.y < location.y+size;
    }

    @Override
    public void getBonus() {
        GameView.gameView.activity.startActivity(
                new Intent(Intent.ACTION_VIEW, Uri.parse(urls[id])));
    }

    @Override
    public void bonusDestroyed() {

    }

    @Override
    public Point[] getPoints() {
        return new Point[]{
                new Point(location.x, location.y),
                new Point(location.x, location.y + size),
                new Point(location.x + size, location.y),
                new Point(location.x + size, location.y + size),
        };
    }

    @Override
    public void Draw(Canvas canvas, Paint paint) {
        Bitmap image = BitmapFactory.decodeResource(GameView.gameView.activity.getResources()
                , drawableIds[id]);
        float multiple = GameView.displaySize.x;
        canvas.drawBitmap(image, null,
                new RectF(location.x*multiple, location.y*multiple,
                        (location.x+size)*multiple,(location.y+size)*multiple),
                paint);
    }
}
