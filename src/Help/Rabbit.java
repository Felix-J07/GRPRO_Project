package Help;

import itumulator.world.World;

import java.awt.Color;
import java.util.*;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;

public class Rabbit implements Actor, DynamicDisplayInformationProvider {
    final int max_age = 5*itumulator.world.World.getTotalDayDuration();
    double energy;
    final int max_steps_without_food = 5*itumulator.world.World.getTotalDayDuration();
    int steps_without_food;
    final int getting_hungry = 5;
    final double chance_of_reproduction = 0.3;
    Burrow burrow;
    Random random;
    Queue<Location> queue;
    boolean movedDuringNight;
    boolean sleeping;

    boolean isNight;

    public Rabbit() {
        this.energy = 1;
        this.random = new Random();
        this.movedDuringNight = false;
        this.queue = new LinkedList<>();
        this.sleeping = false;
    }

    public Rabbit(Burrow burrow) {
        this();
        this.burrow = burrow;
    }

    @Override 
    public void act(World world) {
        isNight = world.isNight();

        Location next_queue_item = queue.poll();
        if (next_queue_item != null) {
            world.move(this, next_queue_item);
            // if (world.getLocation(this).equals(world.getLocation(burrow)) && !sleeping) {
            //     world.remove(this);
            //     return;
            // }
            act(world);
            return;
        }

        if (world.isNight()) {
            if (!sleeping) {
                // sleeping = true;
                if (burrow == null) {
                    Location current = world.getLocation(this);
                    Location burrow_location = world.getNonBlocking(current) instanceof Burrow ? current : Helpers.getRandomNearbyOfType(world, world.getLocation(this), Burrow.class, true);

                    // If no burrow is in vicinity
                    if (burrow_location == null) {
                        // current = world.getLocation(this);
                        // Removes grass
                        Object non_blocking_object = world.getNonBlocking(current);
                        if (non_blocking_object != null) world.delete(world.getNonBlocking(current));
                        burrow = new Burrow();
                        world.setTile(current, burrow);
                        world.remove(this);
                    } else {
                        burrow = (Burrow) world.getNonBlocking(burrow_location);
                        world.move(this, burrow_location);
                        act(world);
                        return;
                    }
                } else if (world.getLocation(this).equals(world.getLocation(burrow))) {
                    world.remove(this);
                } else {
                    if (!movedDuringNight) {
                        queue = Helpers.getRoute(world, world.getLocation(this), world.getLocation(burrow), getMaxMovesPerStep());
                        movedDuringNight = true;
                        act(world);
                        return;
                    }
                }
                sleeping = true;
            }
        } else {
            sleeping = false;
            movedDuringNight = false;
            try {
                world.getLocation(this);
            } catch (IllegalArgumentException e) {
                world.setTile(world.getLocation(burrow), this);
                world.getLocation(this);
                act(world);
                return;
            }

            if (steps_without_food >= getting_hungry) {
                // Eat grass
                Location current = world.getLocation(this);
                Location nearby_grass = Helpers.getRandomNearbyOfType(world, current, Grass.class, true);
                if (nearby_grass != null) {
                    world.move(this, nearby_grass);
                    world.delete(world.getNonBlocking(nearby_grass));
                    steps_without_food = 0;
                } else {
                    Location empty_tile = Helpers.getRandomEmptySurroundingTile(world, current);
                    if (empty_tile != null) world.move(this, empty_tile);
                }
            } else {
                Location current = world.getLocation(this);
                Location nearby_rabbit = Helpers.getRandomNearbyOfType(world, current, Rabbit.class, false);
                Location empty_tile = Helpers.getRandomEmptySurroundingTile(world, current);

                if (nearby_rabbit != null && empty_tile != null && steps_without_food < getting_hungry && random.nextDouble(1) < chance_of_reproduction) {
                    // Reproduction
                    System.out.println(nearby_rabbit);
                    world.setTile(empty_tile, new Rabbit(burrow));
                } else {
                    // Random move
                    if (empty_tile != null) world.move(this, empty_tile);
                }
            }
        }

        energy -= 1.0/max_age;
        if (energy == 0 || ++steps_without_food >= max_steps_without_food) {
            world.delete(this);
        }        
    }

    public int getMaxMovesPerStep() {
        if (energy > 0.1) return 3;
        return 2;
    }

    @Override 
    public DisplayInformation getInformation() {
        String image_key;
        if (isNight) {
            image_key = "rabbit-small-sleeping";
        } else {
            image_key = "rabbit-small";
        }
        return new DisplayInformation(Color.GRAY, image_key);
    }
}