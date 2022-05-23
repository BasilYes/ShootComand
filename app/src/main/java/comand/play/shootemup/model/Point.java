package comand.play.shootemup.model;

/**
 * Класс Point содержит в себе данные о точках игрового простраснтва.
 * @author Василий Реуков
 * @version 1.0
 */
public class Point {
    public float x;
    public float y;

    /**
     * Конструктор класса Point задает базовые значения координат (0;0).
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Конструктор класса Point задает базовые значения координат (x;y).
     * @param x
     * @param y
     */
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод toString позволяет получить координаты точки в виде строки для отладки.
     * @return
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
