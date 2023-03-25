package Domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Defender extends JPanel{

    private int xPosition;
    private int yPosition;
    private int size = 15;

    public Defender(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
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
            int[] xPoints = {xPosition, xPosition + size, xPosition, xPosition - size};
            int[] yPoints = {yPosition, yPosition + 2 * size, yPosition + size, yPosition + 2 * size};
            g.drawPolygon(xPoints, yPoints, xPoints.length);
            
            // Reset the graphics context
            g2d.rotate(-angle - Math.PI/2, xPosition, yPosition);
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



}
