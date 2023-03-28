package Builder;

import Game.GameContainer;
import MouseHandler.MouseHandler;

public class GameBuilder {
    private final GameContainer gameContainer;

    public GameBuilder() {
        gameContainer = new GameContainer();
        setMouseHandler(new MouseHandler(gameContainer));
    }

    private GameBuilder setMouseHandler(MouseHandler mouseHandler) {
        gameContainer.addMouseListener(mouseHandler);
        gameContainer.addMouseMotionListener(mouseHandler);
        return this;
    }

    public GameBuilder setAsteroidCount(int count) {
        gameContainer.getAsteroids().clear();
        for (int i = 0; i < count; i++) {
            gameContainer.getAsteroids().add(gameContainer.generateAsteroid());
        }
        return this;
    }

    /*
     * public GameBuilder setEnemiesCount(int count) { gameContainer.getEnemies().clear(); 
     * for (int i = 0; i < count; i++) {
     * gameContainer.getEnemies().add(gameContainer.generateEnemy()); 
     * } 
     * return this; 
     * }
     */

    public GameContainer build() {
        return gameContainer;
    }
}
