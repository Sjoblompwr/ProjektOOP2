package Domain;

import java.awt.Graphics;
import java.awt.Graphics2D;

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
        this.speed = 2;
        this.visible = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            draw((Graphics2D) g);
        }
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }
    
    public void move() {
        double distance = Math.sqrt(Math.pow(dx - x, 2) + Math.pow(dy - y, 2)); // calculate distance between endpoints
        double percentage = speed / distance; // calculate percentage of distance to travel each frame
        x += (int) Math.round((dx - x) * percentage); // move x coordinate based on percentage
        y += (int) Math.round((dy - y) * percentage); // move y coordinate based on percentage
        this.setLocation(x, y);
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
