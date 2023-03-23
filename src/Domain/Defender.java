package Domain;

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
        int[] xPoints = {100, 150, 200}; // X coordinates of the triangle vertices
        int[] yPoints = {200, 100, 200}; // Y coordinates of the triangle vertices
        int numPoints = 3; // Number of vertices in the polygon
    
        g.drawPolygon(xPoints, yPoints, numPoints);
    }
    

    public void shot() {
        System.out.println("Defender: " + this.xPosition + " " + this.yPosition);
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
