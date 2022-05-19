package comand.play.shootemup.controller;


import java.util.Iterator;
import java.util.LinkedList;

import comand.play.shootemup.MainActivity;
import comand.play.shootemup.model.Bonus;
import comand.play.shootemup.model.Enemy;
import comand.play.shootemup.model.EnemyDreadnought;
import comand.play.shootemup.model.EnemyTest;
import comand.play.shootemup.model.Point;
import comand.play.shootemup.model.GameObject;
import comand.play.shootemup.model.Player;
import comand.play.shootemup.model.Sprite;
import comand.play.shootemup.model.Star;

public class GameController{
    static public GameView gameView;
    static public GameController gameController;

    public float ySize;

    private long time;
    private float deltaTime;

    public float getDeltaTimeMultiple() {
        return deltaTimeMultiple;
    }

    private float deltaTimeMultiple;

    public MainActivity activity;
    public float points = 0.0f;
    public final float BULL_TIMER_INIT = 500.0f;
    public float addBullTimer = BULL_TIMER_INIT;
    public final float BULL_SPEED_TIMER_INIT = 150.0f;
    public float addBullSpeedTimer = BULL_SPEED_TIMER_INIT;
    public int health;
    public float shieldCountdown = 0;
    public boolean shieldIn = false;
    public Point input;

    public static boolean useGiro = false;
    public static boolean playMusic = false;
    public static boolean playSound = false;

    public static Player player;
    public static LinkedList<Enemy> enemy = new LinkedList<>();
    public static LinkedList<GameObject> eBullet = new LinkedList<>();
    public static LinkedList<GameObject> bullet = new LinkedList<>();
    public static LinkedList<Bonus> bonuses = new LinkedList<>();
    public static LinkedList<GameObject> stars = new LinkedList<>();

    public void addPoints(float points){
        this.points += points;
        addBullTimer -= points;
        addBullSpeedTimer -= points;
    }

    public GameController(GameView gameView, MainActivity activity){
        GameController.gameView = gameView;
        gameController = this;
        time = System.nanoTime();
        input = new Point(0, 0);

        ySize = GameView.displaySize.y / GameView.displaySize.x;
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
        deltaTimeMultiple = (float) (-1.0f/Math.sqrt(Math.sqrt(1.0f+points/1000.0f)) + 2.0f);
        deltaTime =  ((float)(newTime - time) / 1000000000.0f * deltaTimeMultiple);
        time = newTime;
    }

    public void starTick()
    {
        for (GameObject obj : stars) {
            if (obj.location.y > ySize) {
                obj.location.y = 0;
                obj.location.x = (float) (Math.random());
            }
            obj.Tick(deltaTime);
        }
    }

    double event = 1050f;

    public void aboutUsTick(){
        event += deltaTime*10.0f;
        if (event >= 1050.0f) {
            bonuses.add(new Sprite());
            event-= 50.0f;
        }
        Iterator<Bonus> bonusIterator = bonuses.iterator();
        LinkedList<Bonus> newBonuses = new LinkedList<>();
        while (bonusIterator.hasNext()){
            Bonus obj = bonusIterator.next();
            obj.Tick(deltaTime);
            if (player.isCollision(obj) || obj.isCollision(player)){
                obj.getBonus();
            } else if (obj.location.y+0.05f <= ySize) {
                newBonuses.add(obj);
            } else {
                obj.bonusDestroyed();
            }
        }
        bonuses = newBonuses;
    }

    public void Tick() {
        player.Tick(deltaTime);
        addPoints(deltaTime);

        shieldCountdown -= deltaTime;

        event += (Math.sin(points) + 1.0f)*Math.sqrt(points/100)*0.5;
        double backChance = (Math.sin(points/1000) + 1)/3;
        double defaultChance = 0.5*(1-backChance)+backChance;

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



        Iterator<GameObject> eBulletIterator = eBullet.iterator();
        LinkedList<GameObject> newEBullet = new LinkedList<>();
        while (eBulletIterator.hasNext()){
            GameObject obj = eBulletIterator.next();
            obj.Tick(deltaTime);
            if (player.isCollision(obj)){
                addDamage();
            } else if (obj.location.y <= ySize) {
                newEBullet.add(obj);
            }
        }
        eBullet = newEBullet;

        Iterator<GameObject> bulletIterator = bullet.iterator();
        LinkedList<GameObject> newBullet = new LinkedList<>();
        while (bulletIterator.hasNext()){
            GameObject obj = bulletIterator.next();
            obj.Tick(deltaTime);
            boolean collided = false;

            for (Enemy en : enemy) {
                if (en.isCollision(obj)) {
                    en.addDamage(1);
                    collided = true;
                    break;
                }
            }
            if(!collided && obj.location.y > 0.04f)
                newBullet.add(obj);
        }
        bullet = newBullet;

        Iterator<Bonus> bonusIterator = bonuses.iterator();
        LinkedList<Bonus> newBonuses = new LinkedList<>();
        while (bonusIterator.hasNext()){
            Bonus obj = bonusIterator.next();
            obj.Tick(deltaTime);
            if (player.isCollision(obj) || obj.isCollision(player)){
                obj.getBonus();
            } else if (obj.location.y+0.05f <= ySize) {
                newBonuses.add(obj);
            } else {
                obj.bonusDestroyed();
            }
        }
        bonuses = newBonuses;

        if (health<= 0)
            gameOver();
    }

    public void aboutUsOver(){
        bonuses.clear();
        event = 1050f;
        gameView.setAboutUs(false);
    }

    private void gameOver()
    {
        enemy.clear();
        eBullet.clear();
        bullet.clear();
        bonuses.clear();
        event = 1050f;
        //player.bulletCount = 1;
        //player.fireReating = 1;
        addBullTimer = BULL_TIMER_INIT;
        gameView.statsFragment.gameOver((int)points);
        gameView.setOnGame(false);
        points = 0.0f;
    }

    private void addDamage(){
        //if (!player.shield)
            health--;
    }
}
