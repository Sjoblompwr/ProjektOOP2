package Game;

import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Domain.*;
import Domain.Asteroid;
import Domain.Defender;
import Domain.Missile;
import Factory.Factory;
import MouseHandler.Pointable;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class GameContainer extends JPanel implements Pointable,KeyListener{
    

    List<Asteroid> asteroids = new ArrayList<>();
    List<Integer> replaceAsteroid = new ArrayList<>();
    List<Missile> missiles = new ArrayList<>();
    List<Missile> newMissiles = new ArrayList<>();
    List<Missile> removeMissile = new ArrayList<>();
    List<Asteroid> newAsteroidsFromExplodeAsteroids = new ArrayList<>();
    private List<Missile> newEnamyMissiles = new ArrayList<>();
    private List<Missile> enamyMissiles = new ArrayList<>();
    private Point mousePosition = new Point(0, 0);

    Defender defender = new Defender(350, 350);
    private int score = 0;
    JLabel scoreLabel;
    Factory factory = new Factory();
    Polygon defenderSpaceShip;
    double spawnInterVale = 100;
    Thread moveThread;
    List<SpaceShip> spaceShip = new ArrayList<>();

    List<Integer> replaceSpaceShip = new ArrayList<>();

    long startTime;

    private int index = 0;
    public GameContainer() {
        super();
        this.add(defender);
        addScoreScreen();
        addSpaceShip();
        this.addKeyListener(this);
        this.setFocusable(true);
        defenderSpaceShip = defender.getSpaceShipPolygon();


        moveThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    moveShip();

                    moveAsteroids();
                    moveMissle();
                    moveMissileSpaseShip();
                    moveEmamyMissileSpaceShip();
                    index++;
                    if(index % 12 == 0){
                        enemyShot();
                    }
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

    private void enemyShot(){
        for(SpaceShip s : spaceShip){
            Missile missile = s.shoot(new Point(defender.getXPosition(),defender.getYPosition()));
            if(!(missile == null))
                newEnamyMissiles.add(missile);
        }

    }

    private void addScoreScreen() {
        this.scoreLabel = new JLabel("Score: " + score);
        this.add(scoreLabel);
    }

    public void addAsteroids() {
        for (int i = 0; i < 20; i++) {
            asteroids.add(generateAsteroid());
        }
    }

    public void addSpaceShip() {
        for (int i = 0; i < 1; i++) {
            spaceShip.add(generateSpaceShip());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        defender.draw(g, (int) mousePosition.getX(), (int) mousePosition.getY());
        defenderSpaceShip = defender.getSpaceShipPolygon();
        // error acures randomly no consistency
        try {
            for (Asteroid a : asteroids) {
                a.draw(g);

            }
            defender.draw(g, (int) mousePosition.getX(), (int) mousePosition.getY());
            for (Missile m : missiles) {
                m.draw(g);
            }

            for (SpaceShip m : spaceShip) {
                m.draw(g);
            }
            for (Missile m : enamyMissiles) {
                m.draw(g);
            }
        }catch (Exception e){
        System.out.println("error");
        }



    }

    private void moveShip() {
        boolean reset = false;
        Iterator<SpaceShip> iterator = spaceShip.iterator();
        while (iterator.hasNext()) {
            SpaceShip b = iterator.next();
            b.move();
            Polygon player = b.getPolygon();

            for (int i = 0; i < player.npoints; i++) {
                if (defenderSpaceShip.contains(player.xpoints[i], player.ypoints[i])) {
                    // collision detected
                    reset = true;
                    iterator.remove();
                    break;
                }
            }
            if (b.getX() < -5 || b.getX() > 700 || b.getY() < -5 || b.getY() > 700) {
                startTime = System.currentTimeMillis();
                replaceSpaceShip.add(spaceShip.indexOf(b));
            }
        }
        if (!spaceShip.isEmpty()) {
            for (Integer i : replaceSpaceShip) {
                spaceShip.remove(spaceShip.get(i)); // ta bort element direkt från listan
            }
        }

        if (reset) {
            resetGame();
            replaceSpaceShip.clear();
            startTime = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - startTime > spawnInterVale) {
            if (!replaceSpaceShip.isEmpty()) {
                spaceShip.add(generateSpaceShip());
                replaceSpaceShip.clear();
                startTime = System.currentTimeMillis();
            }
        }
    }

    private void moveAsteroids() {
        boolean reset = false;
        for (Asteroid a : asteroids) {
            a.move();
            Polygon asteroidPolygon = a.getPolygon();
            for (int i = 0; i < asteroidPolygon.npoints; i++) {
                if (defenderSpaceShip.contains(asteroidPolygon.xpoints[i], asteroidPolygon.ypoints[i])) {
                    // collision detected
                    reset = true;
                    break;
                }
            }
            if (a.getXPosition() < -5 || a.getXPosition() > 700 || a.getYPosition() < -5 || a.getYPosition() > 700) {
                replaceAsteroid.add(asteroids.indexOf(a));
            }
        }

        for (Integer i : replaceAsteroid) {
            asteroids.set(i, generateAsteroid());
        }

        replaceAsteroid.clear();
        if (reset) {
            resetGame();
        }
    }

    private void moveMissileSpaseShip() {
        for (Missile m : missiles) {
            m.move();
            for (SpaceShip a : spaceShip) {
                if (a.isPointInsidePolygon(m.getX(), m.getY())) {
                    score = score + 10;
                    scoreLabel.setText("Score: " + score);
                    replaceSpaceShip.add(spaceShip.indexOf(a));
                    removeMissile.add(m);
                    startTime = System.currentTimeMillis();
                }
            }
            if (!m.isVisible()) {
                removeMissile.add(m);
            }

        }
        for (Missile m : removeMissile) {
            missiles.remove(m);
        }
        missiles.addAll(newMissiles);
        newMissiles.clear();
        repaint();
    }


    private void moveEmamyMissileSpaceShip() {
        boolean reset = false;
        Iterator<Missile> iterator = enamyMissiles.iterator();

        while (iterator.hasNext()) {
            Missile m = iterator.next();
            m.move();
            Polygon defenderPolygon = defender.getSpaceShipPolygon();
            for (int i = 0; i < defenderPolygon.npoints; i++) {
                if (defenderPolygon.contains(m.getX(), m.getY())) {
                    // collision detected
                    reset = true;
                    iterator.remove();
                    break;
                }
            }
            if (!m.isVisible()) {
                iterator.remove();
            }
        }
        enamyMissiles.addAll(newEnamyMissiles);
        newEnamyMissiles.clear();
        if (reset) {
            resetGame();
        }
        repaint();

    }

    private void moveMissle() {

        for (Missile m : missiles) {
            m.move();
            for (Asteroid a : asteroids) {
                if (a.isPointInsidePolygon(m.getX(), m.getY())) {
                    score = score + 10;
                    scoreLabel.setText("Score: " + score);
                    if (a.getSizeOfAsteroid() > 3) {
                        newAsteroidsFromExplodeAsteroids.add(
                                factory.createAsteroid(a.getXPosition(), a.getYPosition(), 3, a.getDesign()));
                           //     new Asteroid(a.getXPosition(), a.getYPosition(), 3, a.getDesign()));
                        newAsteroidsFromExplodeAsteroids.add(
                               // new Asteroid(a.getXPosition(), a.getYPosition(), 3, a.getDesign()));
                                factory.createAsteroid(a.getXPosition(), a.getYPosition(), 3, a.getDesign()));
                    }

                    replaceAsteroid.add(asteroids.indexOf(a));
                    removeMissile.add(m);
                }
            }
            for (Asteroid a : newAsteroidsFromExplodeAsteroids) {
                asteroids.add(a);
            }
            newAsteroidsFromExplodeAsteroids.clear();
            if (!m.isVisible()) {
                removeMissile.add(m);
            }

        }
        for (Missile m : removeMissile) {
            missiles.remove(m);
        }
        missiles.addAll(newMissiles);
        newMissiles.clear();
        repaint();
    }

    public Asteroid generateAsteroid() {
        int gameWidth = 650;
        int gameHeight = 650;

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
        if (rand < 0.4)
            rand = 0.45;
        return factory.createAsteroid(xPos, yPos, (int) (5 * rand) + 1, (int) (5 * Math.random()));
        // Create the asteroid with the generated position
      //  return new Asteroid(xPos, yPos, (int) (5 * rand) + 1, (int) (5 * Math.random()));

    }


    private void resetGame() {
        asteroids.clear();
        spaceShip.clear();
        addAsteroids();
        addSpaceShip();
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

        return   factory.createSpaceShip(xPos, yPos, 1500, this.defender);
        // Create the SpaceShip with the generated position
        //SpaceShip newSpaceShip = new SpaceShip(xPos, yPos, 1500, this.defender);
        // Make the SpaceShip shoot a Missile towards the defender

      //  return newSpaceShip;
    }

    @Override
    public void pointerDown(Point point) {
        newMissiles.add(defender.shot(defender.getX(), defender.getY(), (int) point.getX(), (int) point.getY()));
    }


    @Override
    public void pointerMoved(Point point, boolean pointerDown) {
        this.mousePosition = point;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //defender.accelerate(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        defender.accelerate(e,mousePosition);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        defender.accelerate(e,mousePosition);
    }




}
