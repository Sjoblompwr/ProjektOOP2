package Domain;

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
    private int size = 15;
    private Polygon character;
    private Point2D movement;
    int aceleration =0;

    private Timer timer;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        // Initialize the timer to update the position every 20 milliseconds

        timer = new Timer(2, this);
        timer.start();

        // Add the KeyListener to the JPanel
        setFocusable(true);
        addKeyListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
     //   draw((Graphics2D) g,xPosition,yPosition);
    }


    public void draw(Graphics g, int targetX, int targetY) {
        // Calculate the angle between the polygon and the target point
        double angle = Math.atan2(targetY - yPosition, targetX - xPosition);

        // Rotate the graphics context by the calculated angle
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle + Math.PI / 2, xPosition, yPosition);
        // Draw the polygon
        int[] xPoints = {xPosition, xPosition + size, xPosition, xPosition - size};
        int[] yPoints = {yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size};
        g.drawPolygon(xPoints, yPoints, xPoints.length);

        // Reset the graphics context
        g2d.rotate(-angle - Math.PI / 2, xPosition, yPosition);
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
    private int acelerationX = 0; // Change in x position per update
    private int acelerationY = 0; // Change in y position per update
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();


        // Set the change in position based on the arrow key pressed
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                //aceleration = aceleration-1;
                acelerationX = -1+acelerationX;
                break;
            case KeyEvent.VK_RIGHT:
                acelerationX = 1+acelerationX;
              //  aceleration = aceleration+1;
                break;
            case KeyEvent.VK_UP:
                acelerationY = -1+acelerationY;
                //aceleration = aceleration-1;
                break;
            case KeyEvent.VK_DOWN:
                acelerationY = 1+acelerationY;
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
