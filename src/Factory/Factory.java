package Factory;

import Domain.Asteroid;
import Domain.Defender;
import Domain.Missile;
import Domain.SpaceShip;

public class Factory {
    public Asteroid createAsteroid(int x, int y, int size,int design) {
        return new Asteroid(x, y, size, design);
    }

        public Defender createDefender(int x, int y) {
            return new Domain.Defender(x,y);
        }

        public Missile createMissile(int x, int y, int dx, int dy) {
            return new Domain.Missile(x,y,dx,dy);
        }

        public SpaceShip createSpaceShip(int x, int y, int shotInterval, Domain.Defender defender) {
            return new SpaceShip(x,y,shotInterval,defender);
        }
}
