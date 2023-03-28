package Game;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

    public GameContainer(){      
        super();
        this.add(defender);  
        addScoreScreen();
        defenderSpaceShip = defender.getSpaceShipPolygon();


        moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        defender.draw(g,(int) mousePosition.getX(), (int)mousePosition.getY());
        defenderSpaceShip = defender.getSpaceShipPolygon();
        for(Asteroid a : asteroids){
            a.draw(g);

        }
        for(Missile m : missiles){
            m.draw(g);
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
            asteroids.set(i, generateAsteroid());
            
        }      
        replaceAsteroid.clear();
        if(reset){
            resetGame();
        }
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
