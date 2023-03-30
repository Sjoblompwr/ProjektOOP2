package States;

import Game.GameContainer;
import MouseHandler.Pointable;

public abstract class State implements Pointable {
    protected GameContainer gameContainer;
    abstract void initialize();
    abstract void draw();
    abstract void exit();
}
