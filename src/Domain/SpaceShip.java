package Domain;

import Factory.ComonInterFace;
import Factory.Factory;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SpaceShip extends JPanel implements ComonInterFace {
    private int xPosition, yPosition, size;
    double speed;
    private Color color;
    private Random rand;
    private Missile missile;
    private Long spawnTime;
    Factory factory = new Factory();

    public SpaceShip(int x, int y, int shotInterval, Defender defender) {
        this.xPosition = x;
        this.yPosition = y;
        this.speed = 1.8;
        this.size = 10;
        this.color = Color.RED;
        this.spawnTime = System.currentTimeMillis();
        this.rand = new Random();
        setFocusable(true);
    }





    public void move() {

        double angle = Math.random() * 2 * Math.PI;

        double magnitude = rand.nextGaussian() * 1 + 1;

        int dx = (int) (magnitude * speed * Math.cos(angle));
        int dy = (int) (magnitude * speed * Math.sin(angle));

        this.xPosition += dx + this.speed;
        this.yPosition += dy + this.speed;

        int gameWidth = 650;
        int gameHeight = 650;


        if (missile != null) {
            missile.move();
            if (missile.getX() < 0 || missile.getX() > gameWidth || missile.getY() < 0 || missile.getY() > gameHeight) {
                missile = null;
            }
        }
    }


    public Missile shoot(Point point) {
        if(System.currentTimeMillis() - spawnTime > 1000)
          return   missile = factory.createMissile(xPosition, yPosition, (int)point.getX(),(int) point.getY());
        else
            return null;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        int[][] points = createPolygon();
        int[] xpoints = points[0];
        int[] ypoints = points[1];
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += xPosition;
            ypoints[i] += yPosition;
        }
        g.drawPolygon(new Polygon(xpoints, ypoints, xpoints.length));
        if (missile != null) {
            missile.draw(g);
        }
    }
    private int[][] createPolygon() {
        int[][] points = new int[2][4];
        int halfSize = size / 2;

        points[0][0] = 0;
        points[0][1] = size;
        points[0][2] = size * 2;
        points[0][3] = size;

        points[1][0] = halfSize;
        points[1][1] = 0;
        points[1][2] = halfSize;
        points[1][3] = size * 2;

        return points;
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

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
    }

    @Override
    public int getX() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public int getY() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}