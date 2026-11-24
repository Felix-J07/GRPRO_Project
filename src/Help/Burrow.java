package Help;

import itumulator.executable.DisplayInformation;
import itumulator.world.NonBlocking;
import java.awt.Color;

public class Burrow implements NonBlocking, Entity {
    @Override
    public boolean getNonBlockingStatus() {
        return false;
    }
}
