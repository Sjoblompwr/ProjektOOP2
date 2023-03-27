package Domain;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

public class Defender extends JPanel{

    private int xPosition;
    private int yPosition;
    private double angle;
    private int size = 15;
    private int[] xPoints;
    private int[] yPoints;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.angle = 0;
        this.xPoints = new int[] {xPosition, xPosition + size, xPosition, xPosition - size};
        this.yPoints = new int[] {yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size};
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



}
