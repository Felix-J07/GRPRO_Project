package Help;
import itumulator.world.World;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import java.util.*;
import itumulator.world.NonBlocking;

public class Grass implements NonBlocking, Actor, Entity {
    public Grass() {

    }
    @Override 
    public void act(World world) {
        Random random = new Random();
        if(random.nextBoolean() && world.isDay()) {
            spread(world);
        }
    } 

    // public Location spread(World world) {
    public Grass spread(World world) {
        Location empty_tile = Helpers.getRandomEmptySurroundingNonBlockingLocation(world, world.getLocation(this));
        if (empty_tile != null) {
            Grass grass_new = new Grass();
            world.setTile(empty_tile, grass_new);
            return grass_new;
        }
        return null;

        /*
        Location current = world.getLocation(this);
        Set<Location> neighbours = world.getSurroundingTiles(current);
        ArrayList<Location> neighboursArray = new ArrayList<Location>(neighbours);
        Random r = new Random();
         
        int randomNeighbour;
        do{
            randomNeighbour = r.nextInt(neighboursArray.size());
        }
        while(!world.isTileEmpty(neighboursArray.get(randomNeighbour)));
        
        return neighboursArray.get(randomNeighbour);
        */
    }

    @Override
    public boolean getNonBlockingStatus() {
        return true;
    }
 }

