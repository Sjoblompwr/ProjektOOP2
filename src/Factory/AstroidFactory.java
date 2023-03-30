package Factory;

import Domain.Asteroid;

public class AstroidFactory {
    public static Asteroid generateAsteroid() {
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
        // Create the asteroid with the generated position
        return new Asteroid(xPos, yPos, (int) (5 * rand) + 1, (int) (5 * Math.random()));
    }
}
