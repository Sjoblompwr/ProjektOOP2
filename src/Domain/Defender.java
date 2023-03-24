package Domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Defender extends JPanel{

    private int xPosition;
    private int yPosition;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            draw((Graphics2D) g);
        }
    }

    public void draw(Graphics g) {
        int[] xPoints = {xPosition, xPosition + 5, xPosition + 10}; // X coordinates of the triangle vertices
        int[] yPoints = {yPosition, yPosition - 10, yPosition}; // Y coordinates of the triangle vertices
        int numPoints = 3; // Number of vertices in the polygon
        g.drawPolygon(xPoints, yPoints, numPoints);
    }
    

    public Missile shot(int x, int y, int dx, int dy) {
        System.out.println("Defender: " + this.xPosition + " " + this.yPosition);
        //x and y position of the defender to reach the point where the missile is shot
        Missile missile = new Missile(xPosition, yPosition, dx, dy);
        return missile;
       //return  new Missile(x, y,dx,dy);
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



}
