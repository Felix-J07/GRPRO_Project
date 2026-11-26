package Help;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;
public abstract class Animal implements Actor, DynamicDisplayInformationProvider, Entity {
    protected int energy;
    protected int max_age;
    protected int age;
    protected int max_steps_without_food;
    protected int getting_hungry;
    protected boolean sleeping;
    protected int energy_use_per_day;

    public Animal(int energy, int max_age, int max_steps_without_food) {
        this.energy = energy;
        this.max_age = max_age;
        this.max_steps_without_food = max_steps_without_food;
        this.sleeping = false;
        this.age = 0;
        this.energy_use_per_day = energy/max_steps_without_food;
        this.getting_hungry = 3*energy_use_per_day;
    }

    protected void animalAging(World world) {
        age++;
        energy -= energy_use_per_day;
        if (energy <= 0 || age >= max_age) {
            world.delete(this);
        }
    }

    protected void eatObject(World world, Class<?> type) {
        Location current = world.getLocation(this);
        Location nearby_object = Helpers.getRandomNearbyOfType(world, current, type, true, true);

        if (nearby_object != null && world.isTileEmpty(nearby_object)) {
            world.move(this, nearby_object);
            world.delete(world.getNonBlocking(nearby_object));
        } else {
            Location empty_tile = Helpers.getRandomEmptySurroundingTile(world, current);
            moveToLocation(world, empty_tile);
        }
    }

    protected void moveToLocation(World world, Location empty_tile) {
        if (empty_tile != null) world.move(this, empty_tile);
    }

    @Override
    public boolean getNonBlockingStatus() {
        return false;
    }

    /* @Override 
    public DisplayInformation getInformation() {
        String image_key;
        return new DisplayInformation(Color.GRAY, image_key);
    } */
    
}
