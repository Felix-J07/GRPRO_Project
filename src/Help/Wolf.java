package Help;
import java.util.*;
import itumulator.world.World;
import itumulator.executable.DisplayInformation;
import java.awt.Color;
import itumulator.world.Location;

public class Wolf extends Animal{
    private boolean isNight;
    private Random random = new Random();
    private WolfPack pack;
    public Wolf(int energy, int max_age, int max_steps_without_food, int getting_hungry, WolfPack pack) {
        super(energy, max_age, max_steps_without_food, getting_hungry);
        this.pack = pack;
    }


    @Override 
    public void act(World world) {
        isNight = world.isNight();
        
        moveAlpha(world);
        moveTowardAlpha(world.getLocation(this), world);


        return;
    }

    public Location getAlphaWolfLocation(World world) {
        Wolf randomWolf = pack.getAlphaWolfFromPack();
        Location location = world.getLocation(randomWolf);

        return location;
    }

    public void moveTowardAlpha(Location currentLocation, World world) {
        if(this == pack.wolves.get(0)) return;


        List<Location> LegalFreeTiles = new ArrayList<Location>();
        Set<Location> surroundingTiles = world.getSurroundingTiles(getAlphaWolfLocation(world));
        for (Location location : surroundingTiles) {
            Location legalTile = Helpers.getRandomEmptySurroundingNonBlockingLocation(world, location);
            
            if(legalTile != null) {
                LegalFreeTiles.add(legalTile);
            }
        }

        if(!LegalFreeTiles.isEmpty()) {
            world.move(this, LegalFreeTiles.get(random.nextInt(LegalFreeTiles.size())));
        }
    }

    public void moveAlpha(World world) {
        Boolean isAlpha = this == pack.wolves.get(0);
        Location empty_tile = Helpers.getRandomEmptySurroundingNonBlockingLocation(world, world.getLocation(this));
        
        if(isAlpha && empty_tile != null) {
            world.move(this, empty_tile);
        } else { 
            return;
        }

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
