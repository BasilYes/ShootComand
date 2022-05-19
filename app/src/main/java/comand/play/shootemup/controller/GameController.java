package comand.play.shootemup.controller;


import java.util.Iterator;
import java.util.LinkedList;

import comand.play.shootemup.model.Enemy;
import comand.play.shootemup.model.EnemyDreadnought;
import comand.play.shootemup.model.EnemyTest;
import comand.play.shootemup.model.Point;
import comand.play.shootemup.model.GameObject;
import comand.play.shootemup.model.Player;
import comand.play.shootemup.model.Star;

public class GameController{
    static public GameView gameView;
    static public GameController gameController;

    private float ySize;

    private long time;
    private float deltaTime;

    public float points = 0.0f;
    public int health;

    public static Player player;
    public static LinkedList<comand.play.shootemup.model.Enemy> enemy = new LinkedList<>();
    public static LinkedList<GameObject> eBullet = new LinkedList<>();
    public static LinkedList<GameObject> bullet = new LinkedList<>();
    public static LinkedList<GameObject> bonuses = new LinkedList<>();
    public static LinkedList<GameObject> stars = new LinkedList<>();

    public GameController(GameView gameView){
        this.gameView = gameView;
        gameController = this;
        time = System.nanoTime();

        ySize = gameView.displaySize.y / gameView.displaySize.x;
        player = new Player(new Point(0.5f, 0.5f*ySize));
        int startCount = (int)(ySize*100);
        for (int i =0; i < startCount; i++) {
            stars.add(new Star(new Point((float)Math.random(), (float)Math.random()*ySize),
                    new Point(0, (float)Math.random()),
                    (float)Math.random()));
        }
    }

    public void updateDeltaTime()
    {
        long newTime = System.nanoTime();
        deltaTime = (float) ((newTime - time) / 1000000000.0f * Math.sqrt(1.0f+points/1000.0f));
        time = newTime;
    }

    public void starTick()
    {
        Iterator<GameObject> starIteraror = stars.iterator();
        while (starIteraror.hasNext()){
            GameObject obj = starIteraror.next();
            if (obj.location.y > ySize) {
                obj.location.y = 0;
                obj.location.x = (float)(Math.random());
            }
            obj.Tick(deltaTime);
        }
    }

    double event = 0.0f;
    public void Tick() {
        player.Tick(deltaTime);
        points += deltaTime;

        event += Math.sin(points) + 1.0f;
        if (event >= 41.9f) {
                //enemy.add(new EnemyDreadnought(new Point((float) Math.random() * (0.9f) + 0.05f, -0.05f), new Point(1, 2), 2));
                enemy.add(new EnemyTest(new Point((float) Math.random() * (0.9f) + 0.05f, -0.05f)));
                event = 0;

        }

        Iterator<Enemy> enIteraror = enemy.iterator();
        LinkedList<Enemy> newEnemy = new LinkedList<>();
        while (enIteraror.hasNext()){
            Enemy obj = enIteraror.next();
            obj.Tick(deltaTime);
            if (player.isCollision(obj) || obj.isCollision(player)){
                addDamage();
            }
            else if (obj.getHealth() <= 0){
                points += 10;

            } else if (obj.location.y <= ySize) {
                newEnemy.add(obj);
            } else {
                event += 10.0f;
            }
        }
        enemy = newEnemy;



        Iterator<GameObject> eBulletIteraror = eBullet.iterator();
        LinkedList<GameObject> newEBullet = new LinkedList<>();
        while (eBulletIteraror.hasNext()){
            GameObject obj = eBulletIteraror.next();
            obj.Tick(deltaTime);
            if (player.isCollision(obj)){
                addDamage();
            } else if (obj.location.y <= ySize) {
                newEBullet.add(obj);
            }
        }
        eBullet = newEBullet;

        Iterator<GameObject> bulletIteraror = bullet.iterator();
        LinkedList<GameObject> newBullet = new LinkedList<>();
        while (bulletIteraror.hasNext()){
            GameObject obj = bulletIteraror.next();
            obj.Tick(deltaTime);
            boolean collided = false;

            Iterator<Enemy> enemyIteraror = enemy.iterator();
            while (enemyIteraror.hasNext())
            {
                Enemy en = enemyIteraror.next();
                if (en.isCollision(obj)){
                    en.addDamage(1);
                    collided = true;
                    break;
                }
            }
            if(!collided && obj.location.y > 0.04f)
                newBullet.add(obj);
        }
        bullet = newBullet;

        if (health<= 0)
            gameOver();
    }

    private void gameOver()
    {
        enemy.clear();
        eBullet.clear();
        bullet.clear();
        gameView.statsFragment.gameOver((int)points);
        gameView.setOnGame(false);
        points = 0.0f;
    }

    private void addDamage(){
        health--;
    }
}
