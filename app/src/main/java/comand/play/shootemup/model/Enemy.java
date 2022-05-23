package comand.play.shootemup.model;

/**
 * Абстрактный класс Enemy наследуется от GameObject и хранит данные о модели врага.
 * @author Василий Реуков, Полина Лесневская
 * @version 1.1
 * @see GameObject
 */
public abstract class Enemy extends GameObject {
    /**
     * getHealth геттер для получения значения здоровья врага.
     * @return
     */
    public int getHealth() {
        return health;
    }

    protected int health;

    /**
     * Метод addDamage уменьшает здоровье врага на велечину поулченного урона.
     * @param damage
     */
    public void addDamage(int damage)
    {
        health -= damage;
    }

    /**
     * Конструктор класса Enemy задает здоровье врага, вызывает super-конструктор класса GameObject.
     * @param location
     * @param speed
     * @param health
     */
    Enemy(Point location, Point speed, int health) {
        super(location, speed);
        this.health = health;
    }
}
