package comand.play.shootemup.model;

public class Point {
    public float x;
    public float y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
