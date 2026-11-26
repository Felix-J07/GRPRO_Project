import Help.Burrow;
import Help.Input;
import Help.Rabbit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//assertTrue kontrollerer om en condition er true
//assertDoesNotThrow kontrollerer om der bliver kastet en exception eller ej
//assertEqual tjekker om det ene værdi og det andet værdi er ens

//Test af krav K1-3b og K1-3a: Dyr kan stå på et kaninhul uden der sker noget.
public class BurrowTest {

    private World world;
    private Location location;

    @BeforeEach
    void setUp() {
        world = new World(5);
        location = new Location(2, 2);
    }

    //Test 1 af K1-b
    @Test
    void animalCanStandOnBurrow() {
        Burrow burrow = new Burrow();
        Rabbit rabbit = new Rabbit();

        world.setTile(location, burrow);

        //Må ikke kaste en exception
        assertDoesNotThrow(() -> world.setTile(location, rabbit));
    }

    //Test 2 af K1-b
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

    //Test af K1-a ved brug af t1-3a.txt
    @Test
    void burrowsArePlacedRandomly() throws FileNotFoundException {
        Input input = new Input("t1-3a.txt");
        input.readFromFile();

        Set<Location> observedLocations = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            World world = new World(input.getWorldSize());
            input.loadEntities(world);

            int burrowCount = 0;
            for (var entry : world.getEntities().entrySet()) {
                if (entry.getKey() instanceof Burrow) {
                    burrowCount++;
                    assertTrue(world.isOnTile(entry.getKey()),
                            "The burrow should be placed on the map");
                    observedLocations.add(entry.getValue());
                }
            }
            assertEquals(1, burrowCount, "Each world should contain one burrow");
        }
        assertTrue(observedLocations.size() > 1,
                "The burrow appeared on the same tile in all five worlds");
    }
}
