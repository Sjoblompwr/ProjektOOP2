package Domain;

import java.awt.*;

import javax.swing.JPanel;

public class Missile extends JPanel{
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private int speed;
    private boolean visible;

    
    public Missile(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = 4;
        this.height = 4;
        this.speed = 10;
        this.visible = true;
    }
    public Rectangle getBounds(){
       return new Rectangle(x,y,dx,dy);
    }
    public Polygon getPolygon() {
        int[] xPoints = { x, x + width, x + width, x };
        int[] yPoints = { y, y, y + height, y + height };
        return new Polygon(xPoints, yPoints, 4);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            draw((Graphics2D) g);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
    
    public void move() {
        Point thirdPoint = calculateThirdPoint(new Point(x, y), new Point(dx, dy)); // calculate the third point
        dx = (int) thirdPoint.getX(); // set the new x coordinate
        dy = (int) thirdPoint.getY(); // set the new y coordinate
        double distance = Math.sqrt(Math.pow(dx - x, 2) + Math.pow(dy - y, 2)); // calculate distance between endpoints
        double percentage = speed / distance; // calculate percentage of distance to travel each frame
        x += (int) Math.round((dx - x) * percentage); // move x coordinate based on percentage
        y += (int) Math.round((dy - y) * percentage); // move y coordinate based on percentage
        this.setLocation(x, y);

        if(x < 0 || x > GameSize.SMALL.getWidth() || y < 0 || y > GameSize.SMALL.getHeight()) {
            this.setVisible(false);
        }
    }
    public static Point calculateThirdPoint(Point p1, Point p2) {
        double distance = p1.distance(p2); // calculate distance between the two points
        double dx = (p2.getX() - p1.getX()) / distance; // calculate the x distance ratio
        double dy = (p2.getY() - p1.getY()) / distance; // calculate the y distance ratio
        double newX = p2.getX() + dx * GameSize.SMALL.getWidth() ; // calculate the new x coordinate that is 700 pixels away
        double newY = p2.getY() + dy * GameSize.SMALL.getHeight(); // calculate the new y coordinate that is 700 pixels away
        return new Point((int) newX, (int) newY); // create a new point and return it
    }
    
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
