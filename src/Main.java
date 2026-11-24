import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import java.io.FileNotFoundException;

import Help.Grass;
import Help.Burrow;
import Help.Input;

public class Main {

    public static void main(String[] args) {
        Input input = new Input("tf1-1.txt");
        try {
            input.readFromFile();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        int size = input.getWorldSize();

        Program p = new Program(size, 800, 100);
        World w = p.getWorld();

        input.loadEntities(w);

        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.GREEN, "grass"));
        p.setDisplayInformation(Burrow.class, new DisplayInformation(Color.GREEN, "hole"));

        p.show();
    }
}