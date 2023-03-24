package Movers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Domain.Asteroid;
import Domain.Sprite;

public class AsteroidMover extends JPanel implements Runnable {
    private List<Asteroid> asteroids;
    private List<Integer> replaceAsteroid = new ArrayList<>();


    public AsteroidMover(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    @Override
    public void run() {
        while (true) {
            for (Asteroid a : asteroids) {
                a.move();
                if (a.getXPosition() < -5 || a.getXPosition() > 700 || a.getYPosition() < -5 || a.getYPosition() > 700) {
                    replaceAsteroid.add(asteroids.indexOf(a));
                }
            }
            for (Integer i : replaceAsteroid) {
                asteroids.remove(i.intValue());
                asteroids.add(i.intValue(), generateAsteroid());
            }
            replaceAsteroid.clear();
            repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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