package Factory;

import Domain.Asteroid;

public class AstroidFactory {
    public static Asteroid generateAsteroid() {
        int gameWidth = 650;
        int gameHeight = 650;

        double rand;

        int xPos, yPos;

        rand = Math.random();


        if (rand < 0.25) {

            xPos = 0;
            yPos = (int) (Math.random() * gameHeight);
        } else if (rand < 0.5) {

            xPos = (int) (Math.random() * gameWidth);
            yPos = 0;
        } else if (rand < 0.75) {

            xPos = gameWidth;
            yPos = (int) (Math.random() * gameHeight);
        } else {

            xPos = (int) (Math.random() * gameWidth);
            yPos = gameHeight;
        }
        if (rand < 0.4)
            rand = 0.45;
        return new Asteroid(xPos, yPos, (int) (5 * rand) + 1, (int) (5 * Math.random()));
    }
}
