package Domain;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class SpaceShip extends JPanel {
    private int xPosition, yPosition, health, size;
    double speed;
    private Color color;
    private Random rand;
    private Timer timer;
    private int shotInterval;
    private Defender defender;
    private Missile missile;

    public SpaceShip(int x, int y, int shotInterval,Defender defender) {
        this.xPosition = x;
        this.yPosition = y;
        this.speed = 1.8;
        this.health = 3;
        this.size = 10;
        this.color = Color.RED;
        this.defender = defender;

        this.rand = new Random();
        this.shotInterval = shotInterval;
        setFocusable(true);
    }



    public void stopShooting() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void move() {
        // Generate random angle
        double angle = Math.random() * 2 * Math.PI;
        // Generate random magnitude
        double magnitude = rand.nextGaussian() * 1 + 1;
        // Calculate movement vector
        int dx = (int) (magnitude * speed * Math.cos(angle));
        int dy = (int) (magnitude * speed * Math.sin(angle));
        // Update position
        this.xPosition += dx + this.speed;
        this.yPosition += dy + this.speed;
        // Randomize movement direction
        int gameWidth = 650;
        int gameHeight = 650;


        if (missile != null) {
            missile.move();
            if (missile.getX() < 0 || missile.getX() > gameWidth || missile.getY() < 0 || missile.getY() > gameHeight) {
                missile = null;
            }
        }
    }

    public Missile shot(Defender player) {

       /* int dx = player.getX() - this.xPosition;
        int dy = player.getY() - this.yPosition;
        double direction = Math.atan2(dy, dx);
        double deviation = Math.toRadians(rand.nextInt(20) - 10);
        direction += deviation;
        int speed = 5;
        int missileX = (int) (this.xPosition + Math.cos(direction) * this.size / 2);
        int missileY = (int) (this.yPosition + Math.sin(direction) * this.size / 2);
        Missile missile = new Missile(missileX, missileY, (int) (speed * Math.cos(direction)), (int) (speed * Math.sin(direction)));
    */
        int dx = player.getX() - this.xPosition;
        int dy = player.getY() - this.yPosition;
        double direction = Math.atan2(dy, dx);
        double deviation = Math.toRadians(rand.nextInt(20) - 10);
        direction += deviation;
        int speed = 5;
        int missileX = (int) (this.xPosition + Math.cos(direction) * this.size / 2);
        int missileY = (int) (this.yPosition + Math.sin(direction) * this.size / 2);
        missile = new Missile(missileX, missileY, (int) (speed * Math.cos(direction)), (int) (speed * Math.sin(direction)));
        return missile;
    }
    private long lastShotTime = 0;
    public Missile shoot(Point point) {

        return new Missile(xPosition, yPosition, (int)point.getX(),(int) point.getY());
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
       /* // Skapa en polygon som representerar skeppet
        Polygon p = new Polygon();
        p.addPoint(this.xPosition  + this.size/2, this.yPosition );
        p.addPoint(this.xPosition , this.yPosition + this.size);
        p.addPoint(this.xPosition  + this.size/2, this.yPosition  + this.size/2);
        p.addPoint(this.xPosition + this.size, this.yPosition  + this.size);
        g.drawPolygon(p);*/

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

    public int getSizeOfAsteroid() {
        return size;
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
        //   draw((Graphics2D) g,xPosition,yPosition);
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