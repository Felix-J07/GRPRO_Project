package Help;

import itumulator.world.World;

import itumulator.simulator.Actor;
import itumulator.world.Location;

public class Rabbit implements Actor {
    int max_age = 5;
    double energy;
    int steps_without_food = 20;
    Burrow burrow;

    public Rabbit() {
        energy = 1;
    }

    @Override 
    public void act(World world) {
        if (world.isNight()) {
            Location l = world.getLocation(burrow);
            if (burrow == null) {
                // Get hole in 1 radius or create new
                // move to hole and remove
            }
            // TODO: Go to hole radius 3 and remove/sleep
        } else {
            Location l;
            try {
                l = world.getLocation(this);
            } catch (IllegalArgumentException e) {
                // TODO: Bring rabbit on the map again
            }

        }
    }
}