package Test;

import Help.Grass;
import Help.Helpers;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.*;
import itumulator.world.*;

public class GrassTest {
    World w;
    Grass g;
    Location startLocation;


    @BeforeEach 
    public void setUp() {
        w = new World(3);
        g = new Grass();
        startLocation = new Location(1,1);
        w.setTile(startLocation, g);

    }

    
    @Test 
    public void spreadTest() {
        Grass new_grass = g.spread(w);
        if(Help.Helpers.getRandomEmptySurroundingNonBlockingLocation(w, startLocation) != null) {
            Location newLocation = w.getLocation(new_grass);
            assertFalse(startLocation.equals(newLocation));
        } else {
            assertNull(new_grass);
        }

    }
}
