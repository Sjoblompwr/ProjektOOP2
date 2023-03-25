package Domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Defender extends JPanel implements KeyListener , ActionListener {
    private int xPosition;
    private int yPosition;
    private int size = 15;
    private int dx = 0; // Change in x position per update
    private int dy = 0; // Change in y position per update
    private Timer timer;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;

        // Initialize the timer to update the position every 20 milliseconds
        timer = new Timer(20, this);
        timer.start();

        // Add the KeyListener to the JPanel
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

    public double getXPosition() {
        return this.xPosition;
    }

    public double getYPosition() {
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

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Set the change in position based on the arrow key pressed
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                dx = -1;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                break;
            case KeyEvent.VK_UP:
                dy = -1;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
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
                dx = 0;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                dy = 0;
                break;
            default:
                break;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the position of the defender
        xPosition += dx;
        yPosition += dy;

        // Repaint the JPanel to show the updated position
        repaint();
    }
}
