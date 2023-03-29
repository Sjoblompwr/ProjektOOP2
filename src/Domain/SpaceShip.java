package Domain;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;
public class SpaceShip extends JPanel {
    private int xPosition, yPosition,  health, size;
    double speed;
    private Color color;
    private Random rand;
    private Timer timer;
    private int shotInterval;
    private Missile missile;
    public SpaceShip( int x, int y,int shotInterval) {
        this.xPosition = x;
        this.yPosition = y;
        this.speed = 1.2;
        this.health = 3;
        this.size = 30;
        this.color = Color.RED;
        this.rand = new Random();
        this.shotInterval = shotInterval;
        setFocusable(true);
    }

    public void startShooting(Defender player) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                shot(player);
            }
        }, 0, shotInterval);
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
        // TODO: Add missile to game world*/
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

    public void draw(Graphics g) {
        g.setColor(this.color);
       /* // Skapa en polygon som representerar skeppet
        Polygon p = new Polygon();
        p.addPoint(this.xPosition  + this.size/2, this.yPosition );
        p.addPoint(this.xPosition , this.yPosition + this.size);
        p.addPoint(this.xPosition  + this.size/2, this.yPosition  + this.size/2);
        p.addPoint(this.xPosition + this.size, this.yPosition  + this.size);
        g.drawPolygon(p);*/

        int[] xpoints = {0, 2*size, 2*size, 2*size};
        int[] ypoints = {-2*size, -2*size, -2*size, 0};
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += xPosition;
            ypoints[i] += yPosition;
        }
        g.drawPolygon(new Polygon(xpoints, ypoints, xpoints.length));
        if (missile != null) {
            missile.draw(g);
        }
    }
    public boolean isPointInsidePolygon(int x, int y) {
        int[] xpoints =  {0, 2*size, 2*size, 2*size};
        int[] ypoints = {-2*size, -2*size, -2*size, 0};


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

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            // TODO: Remove enemy ship from game world
        }
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