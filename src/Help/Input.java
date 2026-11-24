package Help;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import itumulator.world.World;

public class Input {
    private String[] file_names = {"t1-1a.txt", 
                           "t1-1b.txt", 
                           "t1-1c.txt", 
                           "t1-2a.txt", 
                           "t1-2b.txt", 
                           "t1-2cde.txt", 
                           "t1-2fg.txt", 
                           "t1-3a.txt", 
                           "t1-3b.txt", 
                           "tf1-1.txt"};
    private String input_file;

    private int world_size;

    private record Entry(String name, int amount) {}
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
            entities.add(new Entry(s.next(), parseAmount(s.nextLine().trim())));
        }
        s.close();
    }

    public int getWorldSize() {
        return world_size;
    }

    private int parseAmount(String amount) {
        String[] amounts = amount.split("-");
        if (amounts.length > 1) {
            int min = Integer.parseInt(amounts[0]);
            int max = Integer.parseInt(amounts[1]);
            return random.nextInt(min, max+1);
        }
        else return Integer.parseInt(amounts[0]);
    }

    public void loadEntities(World world) {
        for (Entry entry : entities) {
            for (int i = 0; i < entry.amount; i++) {
                Entity entity = entityNameToObject(entry.name);
                itumulator.world.Location empty_tile = Helpers.getRandomEmptyTile(world, entity.getNonBlockingStatus());
                if (empty_tile == null) throw new RuntimeException("More entities in file than space on map");
                world.setTile(empty_tile, entity);
            }
        }
    }

    private Entity entityNameToObject(String name) {
        switch (name) {
            case "rabbit":
                return new Rabbit();
            case "grass":
                return new Grass();
            case "burrow":
                return new Burrow();
            default:
                throw new IllegalArgumentException("Entity name " + name + " not recognised");
        }
    }
}