import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

import Domain.Asteroid;
import Domain.Defender;
import Domain.Sprite;

import java.awt.Graphics;

public class GameContainer extends JPanel{
    

    List<Asteroid> asteroids = new ArrayList<>();
    List<Integer> replaceAsteroid = new ArrayList<>();
    Defender defender = new Defender(350, 350);

    public GameContainer(){
        super();
        addAsteroids();
        this.add(defender);
        Timer timer = new Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() {
                move();
            }
        };
        timer.schedule(move, 0, 500);
    }

    public void addAsteroids(){
        for(int i = 0; i < 10; i++){
            asteroids.add(generateAsteroid());
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Asteroid a : asteroids){
            a.draw(g);
            defender.draw(g);
        }
    }

    private void move() {
        
        System.out.println("Asteroid size: " + asteroids.size());
        for(Asteroid a : asteroids){
            a.move();
            if(a.getXPosition() < -5 || a.getXPosition() > 700 || a.getYPosition() < -5 || a.getYPosition() > 700){
                replaceAsteroid.add(asteroids.indexOf(a));
            }
        }
        for(Integer i : replaceAsteroid){
            this.remove(asteroids.get(i));
            asteroids.remove(asteroids.get(i));
            asteroids.add(i,generateAsteroid());
            
        }      
        System.out.println("Asteroid size: " + asteroids.size());
        replaceAsteroid.clear();
        repaint();
    }



    private Asteroid generateAsteroid() {
        int gameWidth = 650;
        int gameHeight = 650;

        // Generate a random number between 0 and 1
        double rand;

        int xPos, yPos;
        for(Asteroid a : asteroids){}
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

        // Create the asteroid with the generated position
       return new Asteroid(xPos, yPos, 50, new Sprite(null));

    }




}
