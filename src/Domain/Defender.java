package Domain;

import Factory.DefenderInterface;
import Factory.Factory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Defender extends JPanel implements DefenderInterface {
    private int xPosition;
    private int yPosition;
    private double angle;
    private int size = 15;
    private int[] xPoints;
    private int[] yPoints;
    Factory factory = new Factory();

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.angle = 0;
        this.xPoints = new int[] { xPosition, xPosition + size, xPosition, xPosition - size };
        this.yPoints = new int[] { yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size };

        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void draw(Graphics g, int targetX, int targetY) {
        if (xPosition < 0) {
            xPosition = 750;
        }
        if (xPosition > 750) {
            xPosition = 0;
        }
        if (yPosition < 0) {
            yPosition = 750;
        }
        if (yPosition > 750) {
            yPosition = 0;
        }


        double angle = Math.atan2(targetY - yPosition, targetX - xPosition);


        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle + Math.PI / 2, xPosition, yPosition);

        g.drawPolygon(xPoints, yPoints, xPoints.length);
        this.angle = -angle - Math.PI / 2;

        g2d.rotate(this.angle, xPosition, yPosition);
    }

    public Missile shot(int x, int y, int dx, int dy) {
        Missile missile = factory.createMissile(xPosition, yPosition, dx, dy);
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

    public Polygon getSpaceShipPolygon() {

        int[] rotatedXPoints = new int[xPoints.length];
        int[] rotatedYPoints = new int[yPoints.length];


        double sinTheta = -Math.sin(angle);
        double cosTheta = Math.cos(angle);


        for (int i = 0; i < xPoints.length; i++) {
            int dx = xPoints[i] - xPosition;
            int dy = yPoints[i] - yPosition;

            rotatedXPoints[i] = (int) (dx * cosTheta - dy * sinTheta) + xPosition;
            rotatedYPoints[i] = (int) (dx * sinTheta + dy * cosTheta) + yPosition;
        }

        return new Polygon(rotatedXPoints, rotatedYPoints, xPoints.length);
    }


    public boolean isPointInsidePolygon(int x, int y) {
        int[] xpoints = { xPosition, xPosition + size, xPosition, xPosition - size };
        int[] ypoints = { yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size };

        int intersections = 0;
        int n = xpoints.length;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            if ((ypoints[i] + yPosition > y) != (ypoints[j] + yPosition > y) &&
                    x < (xpoints[j] + xPosition - xpoints[i] - xPosition) * (y - ypoints[i] - yPosition)
                            / (ypoints[j] - ypoints[i]) + xpoints[i] + xPosition) {
                intersections++;
            }
        }
        return (intersections % 2 == 1);
    }

    private int velocityX = 0;
    private int velocityY = 0;
    private double thrustPower = 1.5;

    public void accelerate(KeyEvent keyEvent, Point mousePosition) {
        int keyCode = keyEvent.getKeyCode();

        double angle = Math.atan2(mousePosition.getY() - this.yPosition, mousePosition.getX() - this.xPosition);

        switch (keyCode) {
            case KeyEvent.VK_UP:

                this.velocityX += Math.cos(angle) * this.thrustPower;
                this.velocityY += Math.sin(angle) * this.thrustPower;
                break;

            case KeyEvent.VK_DOWN:

                this.velocityX *= 0.9;
                this.velocityY *= 0.9;
                break;
        }

    }

    public void move() {

        this.xPosition += this.velocityX;
        this.yPosition += this.velocityY;

        // Update the points of the polygon based on the new position
        this.xPoints = new int[] { xPosition, xPosition + size, xPosition, xPosition - size };
        this.yPoints = new int[] { yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size };

        repaint();
    }

    public void reset(){
        this.xPosition = 350;
        this.yPosition = 350;
        this.angle = 0;
        this.xPoints = new int[] { xPosition, xPosition + size, xPosition, xPosition - size };
        this.yPoints = new int[] { yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size };
    }

}
