package Help;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import itumulator.world.Location;
import itumulator.world.World;

public class Input {
    private String[] file_names = {
        "t1-1a.txt", 
        "t1-1b.txt", 
        "t1-1c.txt", 
        "t1-2a.txt", 
        "t1-2b.txt", 
        "t1-2cde.txt", 
        "t1-2fg.txt", 
        "t1-3a.txt", 
        "t1-3b.txt", 
        "tf1-1.txt",
        "t2-1ab.txt",
        "t2-1c.txt",
        "t2-2a.txt",
        "t2-3a.txt",
        "t2-4a.txt",
        "t2-4b.txt",
        "t2-5a.txt",
        "t2-6a.txt",
        "t2-7a.txt",
        "tf2-1.txt",
        "tf2-2.txt"
    };
    private String input_file;

    private int world_size;

    private record Entry(String name, int amount, Location center) {}
    private List<Entry> entities;

    private Random random = new Random();

    public Input() {
        input_file = file_names[random.nextInt(file_names.length)];
        System.out.println(input_file);
    }

    public Input(String input_file) {
        this.input_file = input_file;
    }

    public void readFromFile() throws FileNotFoundException {
        File file = new File("./data/" + input_file);
        Scanner s;
        s = new Scanner(file);

        world_size = s.nextInt();
        s.nextLine();
        entities = new ArrayList<>();
        while(s.hasNext()) {
            String[] line = s.nextLine().trim().split(" ");
            Location centerLocation = null;
            if (line.length == 3) {
                centerLocation = parseCoordinates(line[2]);
            }
            entities.add(new Entry(line[0], parseAmount(line[1]), centerLocation));
        }
        s.close();
    }

    public int getWorldSize() {
        return world_size;
    }

    private int parseAmount(String amount) {
        String[] amounts = amount.split("-");
        if (amounts.length > 1) {
            try {
                int min = Integer.parseInt(amounts[0]);
                int max = Integer.parseInt(amounts[1]);
                return random.nextInt(min, max+1);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        else return Integer.parseInt(amounts[0]);
    }

    private Location parseCoordinates(String coordinate_string) {
        coordinate_string = coordinate_string.substring(1, coordinate_string.length()-1);
        String[] coordinate_string_split = coordinate_string.split(",");
        try {
            return new Location(Integer.parseInt(coordinate_string_split[0]), Integer.parseInt(coordinate_string_split[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void loadEntities(World world) {
        for (Entry entry : entities) {
            int loops_amount = entry.name.equals("wolf") ? 1 : entry.amount;
        
            for (int i = 0; i < loops_amount; i++) {
                Entity entity = entityNameToObject(world, entry.name, entry.center, entry.amount);
                itumulator.world.Location empty_tile = Helpers.getRandomEmptyTile(world, entity.getNonBlockingStatus());
        
                if (empty_tile == null) throw new RuntimeException("More entities in file than space on map");
        
                world.setTile(empty_tile, entity);
            }
        }
    }

    private Entity entityNameToObject(World world, String name, Location center, int amount) {
        switch (name) {
            case "rabbit":
                return new Rabbit();
            case "grass":
                return new Grass();
            case "burrow":
                return new Burrow();
            case "wolf":
                return new WolfPack(amount);
            case "bear":
                if (center == null) center = Helpers.getRandomEmptyTile(world, false);
                return new Bear(center);
            default:
                throw new IllegalArgumentException("Entity name " + name + " not recognised");
        }
    }
}