package Help;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
public abstract class Animal implements Actor, DynamicDisplayInformationProvider, Entity {
    protected double energy;
    protected int max_age;
    protected int max_steps_without_food;
    protected int getting_hungry;
    protected boolean sleeping;

    Animal(int energy, int max_age, int max_steps_without_food, int getting_hungry) {
        this.energy = energy;
        this.max_age = max_age;
        this.max_steps_without_food = max_steps_without_food;
        this.getting_hungry = getting_hungry;
        this.sleeping = false;

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
