package Help;

import itumulator.world.World;

import java.awt.Color;
import java.util.*;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;

public class Rabbit extends Animal {
    //final int max_age = 5*itumulator.world.World.getTotalDayDuration();
    // double energy;
    //final int max_steps_without_food = itumulator.world.World.getTotalDayDuration();
    //final int getting_hungry = 5;
    private final int chance_of_reproduction = 20;
    private Burrow burrow;
    private Random random;
    private Queue<Location> queue;
    private boolean movedDuringNight;
    private boolean sleeping;

    private boolean isNight;

    public Rabbit() {
        super(100, 5*itumulator.world.World.getTotalDayDuration(), itumulator.world.World.getTotalDayDuration());
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
            act(world);
            return;
        }

        if (world.isNight()) {
            if (!sleeping) {
                if (burrow == null) {
                    findNewBurrow(world);
                    act(world);
                    return;
                } else if (world.getLocation(this).equals(world.getLocation(burrow))) {
                    // Removing rabbit from world to burrow
                    world.remove(this);
                } else {
                    if (!movedDuringNight) {
                        moveToBurrow(world);
                        act(world);
                        return;
                    }
                }
                sleeping = true;
            }
        } else {
            // It is day
            sleeping = false;
            movedDuringNight = false;

            if (!world.isOnTile(this)) {
                moveFromBurrowToWorldTiles(world);
                act(world);
                return;
            }

            if (energy <= getting_hungry) {
                eatObject(world, Grass.class);
            } else {
                Location current = world.getLocation(this);
                Location nearby_rabbit = Helpers.getRandomNearbyOfType(world, current, Rabbit.class, false, false);
                Location empty_tile = Helpers.getRandomEmptySurroundingTile(world, current);

                if (nearby_rabbit != null && empty_tile != null && energy > getting_hungry && random.nextInt(100) < chance_of_reproduction) {
                    rabbitReproduce(world, empty_tile);
                } else {
                    moveToLocation(world, empty_tile);
                }
            }
        }

        animalAging(world);
    }

    private int getMaxMovesPerStep() {
        if (age > max_age*0.1) return 3;
        return 2;
    }

    private void findNewBurrow(World world) {
        Location current = world.getLocation(this);
        Location new_burrow_location = world.getNonBlocking(current) instanceof Burrow ? current : Helpers.getRandomNearbyOfType(world, world.getLocation(this), Burrow.class, true, true);

        // If no burrow is in vicinity
        if (new_burrow_location == null) {
            // current = world.getLocation(this);
            // Removes grass
            Object non_blocking_object = world.getNonBlocking(current);
            if (non_blocking_object != null) world.delete(world.getNonBlocking(current));
            burrow = new Burrow();
            world.setTile(current, burrow);
            // world.remove(this);
        } else {
            burrow = (Burrow) world.getNonBlocking(new_burrow_location);
            world.move(this, new_burrow_location);
            // act(world);
            // return;
        }
    }

    private void moveToBurrow(World world) {
        queue = Helpers.getRoute(world, world.getLocation(this), world.getLocation(burrow), getMaxMovesPerStep());
        movedDuringNight = true;
    }

    private void moveFromBurrowToWorldTiles(World world) {
        Location burrow_location = world.getLocation(burrow);
        if (world.isTileEmpty(burrow_location)) {
            world.setTile(world.getLocation(burrow), this);
        } else {
            Location surrounding_location = Helpers.getRandomEmptySurroundingTile(world, world.getLocation(burrow));
            if (surrounding_location == null) {
                Location empty_tile = Helpers.getRandomEmptyTile(world, false);
                if (empty_tile == null) return;
                world.setTile(empty_tile, this);
            }
            else world.setTile(surrounding_location, this);
        }
    }

    // private void eatGrass(World world) {
    //     Location current = world.getLocation(this);
    //     Location nearby_grass = Helpers.getRandomNearbyOfType(world, current, Grass.class, true, true);

    //     if (nearby_grass != null && world.isTileEmpty(nearby_grass)) {
    //         world.move(this, nearby_grass);
    //         world.delete(world.getNonBlocking(nearby_grass));
    //     } else {
    //         Location empty_tile = Helpers.getRandomEmptySurroundingTile(world, current);
    //         moveToLocation(world, empty_tile);
    //     }
    // }

    // private void moveToRandomLocation(World world, Location empty_tile) {
    //     if (empty_tile != null) world.move(this, empty_tile);
    // }

    private void rabbitReproduce(World world, Location empty_tile) {
        world.setTile(empty_tile, new Rabbit(burrow));
    }

    // private void rabbitAging(World world) {
    //     age++;
    //     energy -= energy_use_per_day;
    //     if (energy <= 0) {
    //         world.delete(this);
    //     }
    // }

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