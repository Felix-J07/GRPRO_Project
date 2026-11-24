package Help;
import java.util.*;
import itumulator.world.World;
import itumulator.executable.DisplayInformation;
import java.awt.Color;

public class Wolf extends Animal{
    int pack_id; //Jeg tænker at det skal besluttes fra hvor end "new Animal()" bliver kaldt, hvilket ID de skal assignes, da der ikke inde fra denne klasse, vil kunne ses hvilke ulve der hører sammen?
    boolean isNight;
    Random random;

    Wolf(int energy, int max_age, int max_steps_without_food, int getting_hungry) {
        super(energy, max_age,max_steps_without_food, getting_hungry);
    
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
