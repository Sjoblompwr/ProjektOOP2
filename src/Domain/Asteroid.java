package Domain;

import Factory.ComonInterFace;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

import javax.swing.JPanel;

public class Asteroid extends JPanel  implements ComonInterFace {

    private int xPosition;
    private int yPosition;
    private int xSpeed = 7; //5 means -2 to 2
    private int ySpeed = 7;
    private int size;
    private int design;

    public Asteroid(int x, int y, int size,int design) {
        this.xPosition = x;
        this.yPosition = y;
        this.size = size;
        this.design = design;
        setRandomInitialSpeed();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


    

    public void draw(Graphics g2) {
        int[][] points = createPolygon();
        int[] xpoints = points[0];
        int[] ypoints = points[1];
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += xPosition;
            ypoints[i] += yPosition;
        }
        g2.drawPolygon(new Polygon(xpoints, ypoints, xpoints.length));
    }

    //ray casting algorithm
    public boolean isPointInsidePolygon(int x, int y) {
        int[][] points = createPolygon();
        int[] xpoints = points[0];
        int[] ypoints = points[1];
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
            xPos = rand.nextInt(xSpeed) - 2; 
            yPos = rand.nextInt(ySpeed) - 2; 
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

    public Polygon getPolygon() {
        int[][] points = createPolygon();
        int[] xpoints = points[0];
        int[] ypoints = points[1];

        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += xPosition;
            ypoints[i] += yPosition;
        }
        return new Polygon(xpoints, ypoints, xpoints.length);
    }

    public int getDesign(){
        return design;
    }

    public int getSizeOfAsteroid(){
        return size;
    }

    private int[][] createPolygon(){
        int[][] points;
        if (design == 2) {
            int[] x = {0, size, 2 * size, 2 * size, size, 0, -size, -2 * size, -2 * size, -size, 0};
            int[] y = {-size, 0, -size, -2 * size, -3 * size, -4 * size, -3 * size, -2 * size, 0, size, 2 * size};
            points = new int[][]{x, y};
        } else if (design == 4) {
            int[] x = {0, size, 2 * size, 2 * size, size, 0, -size, -2 * size, -2 * size, -size};
            int[] y = {-2 * size, -size, 0, 2 * size, 3 * size, 4 * size, 3 * size, 2 * size, 0, -size};
            points = new int[][]{x, y};
        } else {
            int[] x = {0, 3 * size, 5 * size, 5 * size, 2 * size, 0, -2 * size, -4 * size, -4 * size};
            int[] y = {-5 * size, -4 * size, -2 * size, 0, 4 * size, 5 * size, 4 * size, 0, -2 * size};
            points = new int[][]{x, y};
        }
        return points;
    }
}
