package Help;
import Help.Wolf;
import java.util.*;
import itumulator.world.World;
import itumulator.world.Location;

class WolfPack {
    Wolf wolf;
    protected int amount;
    protected ArrayList<Wolf> wolves;
    protected int energy = 1;
    protected int max_age = 2;
    protected int max_steps_without_food = 5;
    protected int getting_hungry = 3;
    Random random = new Random();
    protected int pack_id;

    WolfPack(int amount) {
        this.amount = amount;
        this.pack_id = random.nextInt(500000);
        this.wolves = new ArrayList<Wolf>();
        for(int i = 0; i < amount; i++) {
            Wolf wolfie = new Wolf(energy, max_age, max_steps_without_food, getting_hungry, this);
            wolves.add(wolfie);
        }

    }

    public List<Wolf> getWolfPack() {
        return wolves;
    }

    public Wolf getAlphaWolfFromPack() {
        return wolves.get(0);
    }


}