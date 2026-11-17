package Help;
import itumulator.world.World;
import itumulator.executable.Program;
import itumulator.world.Location;
import java.util.*;
public class Helpers {


    public static Set<Location> getAllTiles(int worldSize) {
        Set<Location> tile = new HashSet<>();
        for(int x = 0; x < worldSize; x++) {
            for(int y=0; y < worldSize; y++) {
                tile.add(new Location(x,y));
            }
        }
        return tile;
    }


    public static Location getRandomEmptyTile(World w) {
        int worldSize = w.getSize();

        Random r = new Random();

        int randomTileX = r.nextInt(0,worldSize);
        int randomTileY = r.nextInt(0,worldSize);
        return new Location(randomTileX,randomTileY);
    }
    
}
