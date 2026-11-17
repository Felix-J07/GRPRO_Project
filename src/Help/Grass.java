package Help;
import itumulator.world.World;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import java.util.*;
import itumulator.world.NonBlocking;
import java.awt.*;

public class Grass implements NonBlocking, Actor {
    DisplayInformation di = new DisplayInformation(Color.green, "grass");

    public Grass() {

    }
    @Override 
    public void act(World world) {
        Random random = new Random();
        if(random.nextBoolean()) {
            spread(world);
        }
    } 

    public Location spread(World world) {
        Location current = world.getLocation(this);
        Set<Location> neighbors = world.getSurroundingTiles(current);
        ArrayList<Location> neighborsArray = new ArrayList<Location>(neighbors);
        Random r = new Random();
        
        int randomNeighbor;
        do{
            randomNeighbor = r.nextInt(neighborsArray.size());
        }
        while(!world.isTileEmpty(neighborsArray.get(randomNeighbor)));
        
        return neighborsArray.get(randomNeighbor);
        }
 }

