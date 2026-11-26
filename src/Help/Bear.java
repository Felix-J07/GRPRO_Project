package Help;

import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

public class Bear extends Animal {
    private boolean isNight;
    private Location center;

    public Bear(Location center) {
        super(200, 10*itumulator.world.World.getTotalDayDuration(), 5*itumulator.world.World.getTotalDayDuration());
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
