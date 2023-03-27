package Domain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DefenderController implements KeyListener {
    private Defender defender;
    private Missile missile;
    private  SpaceShip spaceShip;
    private int targetX;
    private int targetY;
    public DefenderController(Defender defender) {
        this.defender = defender;
        missile = null;
        targetX = 0;
        targetY = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            defender.setXPosition(defender.getXPosition() - 10);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            defender.setXPosition(defender.getXPosition() + 10);
        } else if (keyCode == KeyEvent.VK_SPACE && missile == null) {
            int enemyX = spaceShip.getxPosition();
            int enemyY = spaceShip.getyPosition();
            missile = defender.shot(defender.getXPosition(), defender.getYPosition(), enemyX, enemyY);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void updateMissile() {
        if (missile != null) {
            missile.move();
            if (missile.getY() < 0) {
                missile = null;
            }
        }
    }

    public Missile getMissile() {
        return missile;
    }
}
