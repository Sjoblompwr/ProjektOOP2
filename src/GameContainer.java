import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JPanel;
import java.util.Timer;

import Domain.Asteroid;
import Domain.Defender;
import Domain.Missile;
import Domain.Sprite;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameContainer extends JPanel implements MouseListener, MouseMotionListener{
    

    List<Asteroid> asteroids = new ArrayList<>();
    List<Integer> replaceAsteroid = new ArrayList<>();
    List<Missile> missiles = new ArrayList<>();
    List<Missile> newMissiles = new ArrayList<>();
    List<Missile> removeMissile = new ArrayList<>();
    private Point mousePosition = new Point(0, 0);
    Defender defender = new Defender(350, 350);
    

    public GameContainer(){      
        super();
        addMouseListener(this);
        addMouseMotionListener(this);

        addAsteroids();
        this.add(defender);
        // Timer timer = new Timer();
        // TimerTask move = new TimerTask() {
        //     @Override
        //     public void run() {
        //         move();
        //     }
        // };

        // TimerTask moveMissiles = new TimerTask() {
        //     @Override
        //     public void run() {
        //         moveMissle();
        //     }
        // };
        // timer.schedule(move, 0, 500);
        // timer.schedule(moveMissiles, 0, 100);
         // Create threads for move() and moveMissle()


         
         Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    move();
                    moveMissle();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Thread moveMissilesThread = new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         while (true) {
        //             moveMissle();
        //             try {
        //                 Thread.sleep(10);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //         }
        //     }
        // });

        // Start the threads
        moveThread.start();
        // moveMissilesThread.start();
    }

    public void addAsteroids(){
        for(int i = 0; i < 20; i++){
            asteroids.add(generateAsteroid());
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Asteroid a : asteroids){
            a.draw(g);

        }
        defender.draw(g,(int) mousePosition.getX(), (int)mousePosition.getY());
        for(Missile m : missiles){
            m.draw(g);
        }
    }

    private void move() {
        for(Asteroid a : asteroids){
            a.move();
            if(a.getXPosition() < -5 || a.getXPosition() > 700 || a.getYPosition() < -5 || a.getYPosition() > 700){
                replaceAsteroid.add(asteroids.indexOf(a));
            }
        }
        for(Integer i : replaceAsteroid){
            System.out.println("Replace asteroid");
            //this.remove(asteroids.get(i));
            // asteroids.remove(asteroids.get(i));
            // asteroids.add(i,generateAsteroid());
            asteroids.set(i, generateAsteroid());
            
        }      
        replaceAsteroid.clear();
        repaint();
    }

    private void moveMissle() {
        for(Missile m : missiles){
            m.move();
            for(Asteroid a : asteroids){
                if(a.isPointInsidePolygon(m.getX(), m.getY())){
                    System.out.println("Hit");
                    replaceAsteroid.add(asteroids.indexOf(a));
                    removeMissile.add(m);
                }
            }

            if(!m.isVisible()){
                removeMissile.add(m);
            }

        }
        for(Missile m : removeMissile){
            missiles.remove(m);
        }
        missiles.addAll(newMissiles);
        newMissiles.clear();
        repaint();
        
       // System.out.println("Missile moved " + missiles.size());
    }



    private Asteroid generateAsteroid() {
        int gameWidth = 650;
        int gameHeight = 650;

        // Generate a random number between 0 and 1
        double rand;

        int xPos, yPos;
      
        rand = Math.random();
      
        // Determine which side the asteroid should appear from based on the random number
        if (rand < 0.25) {
            // Left side of the game window
            xPos = 0;
            yPos = (int) (Math.random() * gameHeight);
        } else if (rand < 0.5) {
            // Top side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = 0;
        } else if (rand < 0.75) {
            // Right side of the game window
            xPos = gameWidth;
            yPos = (int) (Math.random() * gameHeight);
        } else {
            // Bottom side of the game window
            xPos = (int) (Math.random() * gameWidth);
            yPos = gameHeight;
        }
        if(rand < 0.4)
            rand = 0.45;
        // Create the asteroid with the generated position
       return new Asteroid(xPos, yPos, (int) (5 * rand) + 1);

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println("Pew pew");
        // newMissiles.add(defender.shot(defender.getX(),defender.getY(),e.getX(), e.getY()));
        // this.mousePosition = e.getPoint();
    }

    @Override
    public void mousePressed(MouseEvent e) {      
          System.out.println("Pew pew");
    newMissiles.add(defender.shot(defender.getX(),defender.getY(),e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePosition = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mousePosition = e.getPoint();
    }




}
