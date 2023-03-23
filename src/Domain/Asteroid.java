package Domain;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Asteroid extends JPanel {

    private int xPosition;
    private int yPosition;
    private int xSpeed;
    private int ySpeed;
    private int size;
    private Sprite sprite;

    public Asteroid(int x, int y, int size, Sprite sprite) {
        this.xPosition = x;
        this.yPosition = y;
        this.size = size;
        this.sprite = sprite;
        setRandomInitialSpeed();
        Timer timer = new Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() {
                move();
            }
        };
        timer.schedule(move, 0, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            draw((Graphics2D) g);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(), xPosition, yPosition, size, size, null);
    }

    public void move() {
        yPosition += xSpeed;
        xPosition += ySpeed;
        this.setLocation(xPosition, yPosition);
    }

 
    public void setRandomInitialSpeed(){
        Random rand = new Random();
        int xPos = 0;
        int yPos = 0;
        while (xPos == 0 || yPos == 0) {
            xPos = rand.nextInt(5) - 2; // Generate a random number between -2 and 2
            yPos = rand.nextInt(5) - 2; // Generate a random number between -2 and 2
        }
        xSpeed = xPos;
        ySpeed = yPos;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

}
