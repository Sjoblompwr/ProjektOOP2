import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Timer;

import Domain.*;
import Domain.Asteroid;
import Domain.Defender;
import Domain.Missile;
import MouseHandler.Pointable;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
public class GameContainer extends JPanel implements Pointable{
    

    List<Asteroid> asteroids = new ArrayList<>();
    List<Integer> replaceAsteroid = new ArrayList<>();
    List<Missile> missiles = new ArrayList<>();
    List<Missile> newMissiles = new ArrayList<>();
    List<Missile> removeMissile = new ArrayList<>();
    List<Asteroid> newAsteroidsFromExplodeAsteroids = new ArrayList<>();
    private Point mousePosition = new Point(0, 0);
    Defender defender = new Defender(350, 350);
    private int score = 0;
    JLabel scoreLabel;

    Polygon defenderSpaceShip;
    Thread moveThread;
    List<SpaceShip> spaceShip = new ArrayList<>();

    List<Integer> replaceSpaceShip = new ArrayList<>();
    long startTime;

    public GameContainer(){      
        super();
        this.add(defender);
        addScoreScreen();
        defenderSpaceShip = defender.getSpaceShipPolygon();


        moveThread = new Thread(new Runnable() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addSpaceShip();
        addAsteroids();
        this.add(defender);

         Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    move();
                   moveShip();

                    moveAsteroids();
                    moveMissle();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Start the threads
        moveThread.start();
    }

    private void addScoreScreen(){
        this.scoreLabel = new JLabel("Score: " + score);
        this.add(scoreLabel);
    }

    public void addAsteroids(){
        for(int i = 0; i < 20; i++){
            asteroids.add(generateAsteroid());
        }
    }
    public void addSpaceShip(){
        for (int i = 0; i < 1 ; i++){
            spaceShip.add(generateSpaceShip());
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        defender.draw(g,(int) mousePosition.getX(), (int)mousePosition.getY());
        defenderSpaceShip = defender.getSpaceShipPolygon();
        for(Asteroid a : asteroids){
            a.draw(g);

        }
        defender.draw(g,(int) mousePosition.getX(), (int)mousePosition.getY());
        for(Missile m : missiles){
            m.draw(g);
        }

        for(SpaceShip m : spaceShip){
            m.draw(g);
        }


    }
    private void moveShip(){
        Timer timer = new Timer();
        boolean replace = false;
        for (SpaceShip b : spaceShip){
            b.move();
            if (b.getX() < -5 || b.getX() > 700 || b.getY() < -5 || b.getY() > 700) {
                startTime = System.currentTimeMillis();
                replaceSpaceShip.add(spaceShip.indexOf(b));
            }
        }
        if(!spaceShip.isEmpty()) {
            for (Integer i : replaceSpaceShip) {
                spaceShip.remove(spaceShip.get(replaceSpaceShip.get(i)));
            }
        }


       if(System.currentTimeMillis() - startTime > 6000){
           if(!replaceSpaceShip.isEmpty()) {
               spaceShip.add(generateSpaceShip());
               replaceSpaceShip.clear();
           }

       }

    private void moveAsteroids() {
        boolean reset = false;
        for(Asteroid a : asteroids){
            a.move();
            Polygon asteroidPolygon = a.getPolygon();
            for (int i = 0; i < asteroidPolygon.npoints; i++) {
                if (defenderSpaceShip.contains(asteroidPolygon.xpoints[i], asteroidPolygon.ypoints[i])) {
                    // collision detected
                    reset = true;
                    break;
                }
            }
            if(a.getXPosition() < -5 || a.getXPosition() > 700 || a.getYPosition() < -5 || a.getYPosition() > 700){
                replaceAsteroid.add(asteroids.indexOf(a));
            }
        }

        for(Integer i : replaceAsteroid){
            System.out.println("Replace asteroid");
            //this.remove(asteroids.get(i));
            // asteroids.remove(asteroids.get(i));
            // asteroids.add(i,generateAsteroid());
            asteroids.set(i, generateAsteroid());

        }

        replaceAsteroid.clear();
        if(reset){
            resetGame();
        }
    }
    private void startGeneratingSpaceships() {
        Timer timer = new Timer();

        // Skapa en TimerTask som genererar rymdskepp och lägger till dem i spelet
        TimerTask generateSpaceships = new TimerTask() {
            @Override
            public void run() {
                SpaceShip newSpaceShip = generateSpaceShip();
                spaceShip.add(newSpaceShip);
            }
        };

        // Schedule TimerTask-objektet att köras varje sekund
        timer.scheduleAtFixedRate(generateSpaceships, 0, 1000);
    }

    private void moveMissileSpa(){
        for (Missile m : missiles){
            m.move();
            for (SpaceShip a : spaceShip){
                if (a.isPointInsidePolygon(m.getX(),m.getY())){
                    System.out.println("Hit  spaceShip");
                    replaceSpaceShip.add(spaceShip.indexOf(a));
                    removeMissile.add(m);
                    startTime = System.currentTimeMillis();
                }
            }
            if(!m.isVisible()){
                removeMissile.add(m);
            }

        }
        for(Missile m : removeMissile){
            missiles.remove(m);
        }
        missiles.addAll(newMissiles);
        newMissiles.clear();
        repaint();


    }
    private void moveMissle() {

        for(Missile m : missiles){
            m.move();
            for(Asteroid a : asteroids){
                if(a.isPointInsidePolygon(m.getX(), m.getY())){
                    System.out.println("Hit");
                    score = score + 10;
                    scoreLabel.setText("Score: " + score);
                    if(a.getSizeOfAsteroid() > 3){
                        newAsteroidsFromExplodeAsteroids.add(
                            new Asteroid(a.getXPosition(), a.getYPosition(),3, a.getDesign()));
                        newAsteroidsFromExplodeAsteroids.add(
                            new Asteroid(a.getXPosition(), a.getYPosition(),3, a.getDesign()));
                    }

                    replaceAsteroid.add(asteroids.indexOf(a));
                    removeMissile.add(m);
                }
            }
            for(Asteroid a : newAsteroidsFromExplodeAsteroids){
                asteroids.add(a);
            }
            newAsteroidsFromExplodeAsteroids.clear();
            if(!m.isVisible()){
                removeMissile.add(m);
            }

        }
        for(Missile m : removeMissile){
            missiles.remove(m);
        }
        missiles.addAll(newMissiles);
        newMissiles.clear();
        repaint();

       // System.out.println("Missile moved " + missiles.size());
    }

    public Asteroid generateAsteroid() {
        // The width and height of the game window where the asteroids can appear
        int gameWidth = 650;
        int gameHeight = 650;

        // Generate a random number between 0 and 1
        double rand;

        int xPos, yPos;
      
        rand = Math.random();
      
        // Determine which side the asteroid should appear from based on the random number
        if (rand < 0.25) {
            // Left side of the game window
            xPos = 0;
            yPos = (int) (Math.random() * gameHeight);
        } else if (rand < 0.5) {
            // Top side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = 0;
        } else if (rand < 0.75) {
            // Right side of the game window
            xPos = gameWidth;
            yPos = (int) (Math.random() * gameHeight);
        } else {
            // Bottom side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = gameHeight;
        }
        if(rand < 0.4)
            rand = 0.45;

        // Create the asteroid with the generated position
       return new Asteroid(xPos, yPos, (int) (5 * rand) + 1, (int) (5*Math.random()));

    }


    private void resetGame(){
        asteroids.clear();
        addAsteroids();
        score = 0;
        scoreLabel.setText("Score: " + score);
        repaint();
    }
    private SpaceShip generateSpaceShip() {

        int gameWidth = 650;
        int gameHeight = 650;
        // Generate a random number between 0 and 1
        double rand = Math.random();

        int xPos, yPos;

        // Determine which side the SpaceShip should appear from based on the random number
        if (rand < 0.25) {
            // Left side of the game window
            xPos = 0;
            yPos = (int) (Math.random() * gameHeight);
        } else if (rand < 0.5) {
            // Top side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = 0;
        } else if (rand < 0.75) {
            // Right side of the game window
            xPos = gameWidth;
            yPos = (int) (Math.random() * gameHeight);
        } else {
            // Bottom side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = gameHeight;
        }

        // Create the SpaceShip with the generated position
        SpaceShip newSpaceShip = new SpaceShip(xPos, yPos, 1500);

        // Make the SpaceShip shoot a Missile
        newSpaceShip.startShooting(defender);

        return newSpaceShip;
    }
    @Override
    public void pointerDown(Point point) {
        newMissiles.add(defender.shot(defender.getX(),defender.getY(),(int)point.getX(), (int)point.getY()));
    }



    @Override
    public void pointerMoved(Point point, boolean pointerDown) {
        this.mousePosition = point;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }




}
