package Domain;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.*;

public class Defender extends JPanel implements ActionListener,KeyListener {
    private int xPosition;
    private int yPosition;
    private double angle;
    private int size = 15;
    private int[] xPoints;
    private int[] yPoints;
    private Timer timer;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.angle = 0;
        this.xPoints = new int[] {xPosition, xPosition + size, xPosition, xPosition - size};
        this.yPoints = new int[] {yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size};

        timer = new Timer(2, this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }



    public void draw(Graphics g, int targetX, int targetY) {
        // Calculate the angle between the polygon and the target point
        double angle = Math.atan2(targetY - yPosition, targetX - xPosition);

        // Rotate the graphics context by the calculated angle
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle + Math.PI/2, xPosition, yPosition);
        // Draw the polygon
        g.drawPolygon(xPoints, yPoints, xPoints.length);
        this.angle = -angle - Math.PI/2;
        // Reset the graphics context
        g2d.rotate(this.angle, xPosition, yPosition);
    }
        

    

    public Missile shot(int x, int y, int dx, int dy) {
        //x and y position of the defender to reach the point where the missile is shot
        Missile missile = new Missile(xPosition, yPosition, dx, dy);
        return missile;
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void setXPosition(int x) {
        this.xPosition = x;
    }

    public void setYPosition(int y) {
        this.yPosition = y;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public Polygon getSpaceShipPolygon(){
        // create a new array to store the rotated points
        int[] rotatedXPoints = new int[xPoints.length];
        int[] rotatedYPoints = new int[yPoints.length];

        // calculate sine and cosine of the angle
        double sinTheta = -Math.sin(angle);
        double cosTheta = Math.cos(angle);

        // rotate each point around the center of the polygon
        for (int i = 0; i < xPoints.length; i++) {
            int dx = xPoints[i] - xPosition;
            int dy = yPoints[i] - yPosition;

            rotatedXPoints[i] = (int) (dx * cosTheta - dy * sinTheta) + xPosition;
            rotatedYPoints[i] = (int) (dx * sinTheta + dy * cosTheta) + yPosition;
        }
        // create and return the rotated polygon
        return new Polygon(rotatedXPoints, rotatedYPoints, xPoints.length);
    }



    private double acelerationX = 0; // Change in x position per update
    private double acelerationY = 0; // Change in y position per update
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();



        int ac = 1;
        // Set the change in position based on the arrow key pressed
        switch(keyCode) {

            case KeyEvent.VK_LEFT:
                //aceleration = aceleration-1;
                acelerationX = -ac+acelerationX;
               // angle -= 5;
                break;
            case KeyEvent.VK_RIGHT:
                acelerationX = ac+acelerationX;
              //  aceleration = aceleration+1;
             //   angle += 5;
                break;
            case KeyEvent.VK_UP:
                acelerationY = -ac+acelerationY;
                //aceleration = aceleration-1;
                break;
            case KeyEvent.VK_DOWN:
                acelerationY = ac+acelerationY;
                //aceleration = aceleration+1;

                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Reset the change in position when the arrow key is released
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                acelerationX = 0;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                acelerationY = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the position of the defender
        xPosition += acelerationX;
        yPosition += acelerationY;

        // Repaint the JPanel to show the updated position
        repaint();
    }
}
