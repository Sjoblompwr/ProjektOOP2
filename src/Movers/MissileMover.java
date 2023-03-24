package Movers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Domain.Missile;

public class MissileMover extends JPanel implements Runnable {
    private List<Missile> missiles;
    private List<Missile> newMissiles = new ArrayList<>();
    private List<Missile> removeMissile = new ArrayList<>();

    public MissileMover(List<Missile> missiles) {
        this.missiles = missiles;
    }

    @Override
    public void run() {
        while (true) {
            for (Missile m : missiles) {
                m.move();
                if (!m.isVisible()) {
                    removeMissile.add(m);
                }
                repaint();
            }
            for (Missile m : removeMissile) {
                missiles.remove(m);
            }
            missiles.addAll(newMissiles);
            newMissiles.clear();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}