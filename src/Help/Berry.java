package Help;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.World;

import java.awt.Color;

//Berry-klassen består bl.a. af metoderne eatBerries() og hasBerries(), som kaldes af bjørnen.

public abstract class Berry implements Actor, DynamicDisplayInformationProvider {
    private boolean hasBerries;
    private int stepsSinceEaten;
    private int regrowTime;
    private int energyValue;
    private String berryType;

    public Berry(String berryType, int regrowTime, int energyValue) {
        this.berryType = berryType;
        this.regrowTime = regrowTime;
        this.energyValue = energyValue;
        this.hasBerries = true;
        this.stepsSinceEaten = 0;
    }

    //stepsSinceEaten sammenlignes med regrowTime, for at kontrollerer hvornår busken har bær igen.
    @Override
    public void act(World world) {
        if (!hasBerries) {
            stepsSinceEaten++;
            if (stepsSinceEaten >= regrowTime) {
                hasBerries = true;
                stepsSinceEaten = 0;
            }
        }
    }

    //Bær fjernes fra busken (hvis den har bær), og giver Bjørnen energi.
    public int eatBerries() {
        if (!hasBerries) {
            return 0;
        } else {
            hasBerries = false;
            stepsSinceEaten = 0;
            return energyValue;
        }
    }

    //Get-metode så bjørnen kan se om busken har bær
    public boolean hasBerries() {
        return hasBerries;
    }

    @Override
    public DisplayInformation getInformation() {
        String image_key;
        if (hasBerries) {
            image_key = "bush-berries";
        } else {
            image_key = "bush";
        }
        return new DisplayInformation(Color.GREEN, image_key);
    }
}
