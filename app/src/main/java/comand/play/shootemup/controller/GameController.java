package comand.play.shootemup.controller;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import comand.play.shootemup.MainActivity;
import comand.play.shootemup.model.Bonus;
import comand.play.shootemup.model.BonusBullet;
import comand.play.shootemup.model.DefaultEnemy;
import comand.play.shootemup.model.Enemy;
import comand.play.shootemup.model.EnemyDreadnought;
import comand.play.shootemup.model.SpiralEnemy;
import comand.play.shootemup.model.Point;
import comand.play.shootemup.model.GameObject;
import comand.play.shootemup.model.Player;
import comand.play.shootemup.model.Sprite;
import comand.play.shootemup.model.Star;


/**
 * Класс GameController отвечает за внутриигровую логику.
 * @author Василий Реуков, Бугров Егор
 * @version 2.0
 */
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
//    public float shieldCountdown = 0;
//    public boolean shieldIn = false;
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

    /**
     * Метод addPoints отвечает за контроль начисления очков
     * и увелечение скорости игры.
     * @param points
     */
    public void addPoints(float points){
        this.points += points;
        addBullTimer -= points;
        addBullSpeedTimer -= points;
    }

    /**
     * Конструктор GameController отвечает за привязку к классу GameView и текущей активности.
     * @see GameView
     * @see MainActivity
     * @param gameView
     * @param activity
     */
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

    /**
     * Метод updateDeltaTime обновляет значение поля deltaTime и устанавливает новое время.
     */
    public void updateDeltaTime()
    {
        long newTime = System.nanoTime();
        deltaTimeMultiple = (float) (-1.0f/Math.sqrt(Math.sqrt(1.0f+points/1000.0f)) + 2.0f);
        deltaTime =  ((float)(newTime - time) / 1000000000.0f * deltaTimeMultiple);
        time = newTime;
    }

    /**
     * Метод starTick отвечает, за обработку звезд.
     */
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

    /**
     * Метод aboutUsTick отвечает, за обработку выпадения ссылок в меню "О Нас".
     */
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
    float randomF;

    /**
     * Метод Tick обрабатывает все происхожящие события в игре на каждый тик времени.
     */
    public void Tick() {
        player.Tick(deltaTime);
        addPoints(deltaTime);
        randomF = (float) Math.random() * 0.6f + 0.1f;
//        shieldCountdown -= deltaTime;
//        if (shieldCountdown < 0f){
//            shieldIn = false;
//        }
        event += (Math.sin(points) + 1.0f)*Math.sqrt(points/100)*0.5;
        double backChance = (Math.sin(points/1000) + 1)/3;
        double defaultChance = 0.5*(1-backChance)+backChance;
        if (event >= 31.9f) {
                Double chance = Math.random();
                if (0.1 <= chance && chance <    0.3){
                    enemy.add(new EnemyDreadnought(new Point((float) Math.random() * 0.3f + 0.5f, -0.05f), new Point(1, 2), 2));
                } else if (0.3 <= chance && chance < 0.6){
                    enemy.add(new DefaultEnemy(new Point((float) Math.random() * (0.2f) + 0.5f, -0.05f)));
                    enemy.add(new DefaultEnemy(new Point((float) Math.random() * (0.5f) + 0.8f, -0.05f)));
                } else {
                    enemy.add(new SpiralEnemy(new Point(randomF, 0.1f)));
                    enemy.add(new SpiralEnemy(new Point(randomF + 0.2f, -0.05f)));
                    enemy.add(new SpiralEnemy(new Point(randomF - 0.2f, -0.05f)));
                }
                event = 0;
        }

        Random rnd = new Random();
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
                if (rnd.nextGaussian() > 0.5 && obj.getClass() == EnemyDreadnought.class){
                    bonuses.add(new BonusBullet(obj.location, 0.1f));
                } else if (obj.getClass() == EnemyDreadnought.class){
                    bonuses.add(new Bonus(obj.location, 0.1f));
                }
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

    /**
     * Метод aboutUsOver отвечает за очистку элементов, вызванных на экране "О нас".
     */
    public void aboutUsOver(){
        bonuses.clear();
        event = 1050f;
        gameView.setAboutUs(false);
    }

    /**
     * Метод gameOver отвечает за сброс данных, после проигрыша.
     */
    private void gameOver()
    {
        enemy.clear();
        eBullet.clear();
        bullet.clear();
        bonuses.clear();
        event = 1050f;
        player.bulletCount = 1;
        player.fireRating = 0.45f;
        addBullTimer = BULL_TIMER_INIT;
        gameView.statsFragment.gameOver((int)points);
        gameView.setOnGame(false);
        points = 0.0f;
    }

    /**
     * Метод addDamage отвечает за обработку получения урона игроком.
     */
    private void addDamage(){
//        if (!shieldIn)
            health--;
    }
}
