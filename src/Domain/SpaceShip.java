package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SpaceShip extends JPanel {

    private int xPosition;
    private int yPosition;
    private int speed;
    private int width;
    private int height;
    private int health;

    public SpaceShip(int xPosition, int yPosition, int speed) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
        this.health = health;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void move() {
        // implementera hur fienden ska röra sig
    }

    public void checkCollision() {
        // implementera hur kollision med andra objekt ska hanteras
    }
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D.Double shape = new Rectangle2D.Double(xPosition, yPosition, width, height);
        g2d.fill(shape);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
