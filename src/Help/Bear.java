package Help;

import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

public class Bear extends Animal {
    private boolean isNight;
    private Location center;

    public Bear(Location center) {
        super(energy, max_age, max_steps_without_food, getting_hungry);
        if (center == null) throw new RuntimeException("Bear teritorium center not defined");
        this.center = center;
    }

    @Override 
    public void act(World world) {
        isNight = world.isNight();
    
        return;
    }


    @Override 
    public DisplayInformation getInformation() {
        String image_key;
        if (isNight) {
            image_key = "wolf-small-sleeping";
        } else {
            image_key = "wolf-small";
        }
        return new DisplayInformation(Color.GRAY, image_key);
    }
}
