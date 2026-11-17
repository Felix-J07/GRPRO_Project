package Help;
import itumulator.world.World;
import itumulator.simulator.Actor;
public class Grass implements Actor {

    @Override 
    public void act(World world) {
        System.out.println("Grass is being placed");
    }
}
