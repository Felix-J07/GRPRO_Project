package Help;
import itumulator.world.World;
import itumulator.world.Location;
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

        Location location;
        do {
            int randomTileX = r.nextInt(0,worldSize);
            int randomTileY = r.nextInt(0,worldSize);
            location = new Location(randomTileX, randomTileY);
        }
        while(!w.isTileEmpty(location));
        return location;
    }
    
    public Queue<Location> getRoute(World world, Location from, Location to, int steps) {
        Queue<Location> route = new LinkedList<>();
        if (from.equals(to)) return route;
        Location next = new Location(from.getX(), from.getY());
        int to_x = to.getX();
        int to_y = to.getY();
        
        for (int i = 0; i < steps && next.getX() != to_x && next.getY() != to_y; i++) {
            int x_distance = to_x - next.getX();
            int y_distance = to_y - next.getY();
            
            int tmp_x = 0;
            int tmp_y = 0;
            if (x_distance < 0) tmp_x = -1;
            else if (x_distance > 0) tmp_x = 1;

            if (y_distance < 0) tmp_y = -1;
            else if (y_distance > 0) tmp_y = 1;

            if (world.isTileEmpty(new Location(tmp_x, tmp_y))) next = new Location(tmp_x, tmp_y);
            else return route;

            route.add(next);
        }

        return route;
    }
}
