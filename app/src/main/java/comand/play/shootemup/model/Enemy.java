package comand.play.shootemup.model;

public abstract class Enemy extends GameObject {

    public int getHealth() {
        return health;
    }

    protected int health;

    public void addDamage(int damage)
    {
        health -= damage;
    }

    Enemy(Point location, Point speed, int health) {
        super(location, speed);
        this.health = health;
    }
}
