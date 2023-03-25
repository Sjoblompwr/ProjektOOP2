package Domain;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Asteroid extends JPanel {

    private int xPosition;
    private int yPosition;
    private int xSpeed = 7; //5 means -2 to 2
    private int ySpeed = 7;
    private int size;

    public Asteroid(int x, int y, int size) {
        this.xPosition = x;
        this.yPosition = y;
        this.size = size;
        setRandomInitialSpeed();
        // Timer timer = new Timer();
        // TimerTask move = new TimerTask() {
        //     @Override
        //     public void run() {
        //         move();
        //     }
        // };
        // timer.schedule(move, 0, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            draw((Graphics2D) g);
        }
    }


    

    public void draw(Graphics g2) {
        int[] xpoints = {0, 3*size, 5*size, 5*size, 2*size, 0, -2*size, -4*size, -4*size};
        int[] ypoints = {-5*size, -4*size, -2*size, 0, 4*size, 5*size, 4*size, 0, -2*size};
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += xPosition;
            ypoints[i] += yPosition;
        }
        g2.drawPolygon(new Polygon(xpoints, ypoints, xpoints.length));
    }
    //ray casting algorithm
    public boolean isPointInsidePolygon(int x, int y) {
        int[] xpoints = {0, 3*size, 5*size, 5*size, 2*size, 0, -2*size, -4*size, -2*size};
        int[] ypoints = {-5*size, -4*size, -2*size, 0, 4*size, 5*size, 4*size, 0, -2*size};
        int intersections = 0;
        int n = xpoints.length;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            if ((ypoints[i] + yPosition > y) != (ypoints[j] + yPosition > y) &&
                    x < (xpoints[j] + xPosition - xpoints[i] - xPosition) * (y - ypoints[i] - yPosition) / (ypoints[j] - ypoints[i]) + xpoints[i] + xPosition) {
                intersections++;
            }
        }
        return (intersections % 2 == 1);
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
            xPos = rand.nextInt(xSpeed) - 2; // Generate a random number between -2 and 2
            yPos = rand.nextInt(ySpeed) - 2; // Generate a random number between -2 and 2
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
