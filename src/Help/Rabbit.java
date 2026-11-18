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
    final int max_steps_without_food = itumulator.world.World.getTotalDayDuration();
    int steps_without_food;
    final int getting_hungry = 5;
    final double chance_of_reproduction = 0.3;
    Burrow burrow;
    Random random;
    Queue<Location> queue;
    boolean movedDuringNight;

    boolean isNight;

    public Rabbit() {
        energy = 1;
        random = new Random();
        movedDuringNight = false;
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
            Location l = world.getLocation(burrow);
            if (burrow == null) {
                Location burrow_location = Helpers.getRandomNearbyOfType(world, world.getLocation(this), Burrow.class, true);

                /*
                Set<Location> neighbours = world.getSurroundingTiles(world.getLocation(this));
                for (Location neighbour : neighbours) {
                    try {
                        Object non_blocking = world.getNonBlocking(neighbour);
                        if (non_blocking instanceof Burrow) {
                            burrow = (Burrow) non_blocking;
                            world.move(this, neighbour);
                            act(world);
                            break;
                        }
                    } catch (IllegalArgumentException e) {}
                }
                */

                // If no burrow is in vicinity
                if (burrow_location == null) {
                    Location current = world.getLocation(this);
                    // Removes grass
                    world.delete(world.getNonBlocking(current));
                    burrow = new Burrow();
                    world.setTile(current, burrow);
                    world.remove(this);
                } else {
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
        } else {
            Location l;
            try {
                l = world.getLocation(this);
            } catch (IllegalArgumentException e) {
                movedDuringNight = false;
                world.setTile(world.getLocation(burrow), this);
                act(world);
                return;
                /*
                Set<Location> burrow_neighbours = world.getEmptySurroundingTiles(world.getLocation(burrow));
                List<Location> list = new ArrayList<>(burrow_neighbours);
                if (list.size() == 0) throw new RuntimeException("No space around burrow");
                world.setTile(list.get(random.nextInt(list.size())), this);
                */
            }
        }

        energy -= 1.0/max_age;
        if (energy == 0 || ++steps_without_food >= max_steps_without_food) {
            world.delete(this);
        }

        if (steps_without_food >= getting_hungry) {
            // Eat grass
            Location current = world.getLocation(this);
            Location nearby_grass = Helpers.getRandomNearbyOfType(world, current, Grass.class, true);
            if (nearby_grass != null) {
                world.move(this, nearby_grass);
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
                world.setTile(empty_tile, new Rabbit());
            } else {
                // Random move
                if (empty_tile != null) world.move(this, empty_tile);
            }
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