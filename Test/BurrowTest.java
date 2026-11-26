import Help.Burrow;
import Help.Rabbit;

import static org.junit.jupiter.api.Assertions.*;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Test af krav K1-3b: Dyr kan stå på et kaninhul uden der sker noget.
public class BurrowTest {

    private World world;
    private Location location;

    @BeforeEach
    void setUp() {
        world = new World(5);
        location = new Location(2, 2);
    }

    @Test
    void animalCanStandOnBurrow() {
        Burrow burrow = new Burrow();
        Rabbit rabbit = new Rabbit();

        world.setTile(location, burrow);

        //Må ikke kaste en exception
        assertDoesNotThrow(() -> world.setTile(location, rabbit));
    }

    @Test
    void burrowAndAnimalExistOnSameTile() {
        Burrow burrow = new Burrow();
        Rabbit rabbit = new Rabbit();

        world.setTile(location, burrow);
        world.setTile(location, rabbit);

        //Kontrolér om dyr og hul er på samme tile uden at der sker noget
        assertSame(rabbit, world.getTile(location));
        assertSame(burrow, world.getNonBlocking(location));
        assertEquals(location, world.getLocation(rabbit));
        assertEquals(location, world.getLocation(burrow));
    }
}
