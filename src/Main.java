import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import java.io.FileNotFoundException;

import Help.Grass;
import Help.Burrow;
//import Help.Input;

public class Main {

    public static void main(String[] args) {
        
        int size = 5;

        Program p = new Program(size, 800, 100);
        World w = p.getWorld();



        p.setDisplayInformation(Grass.class, new DisplayInformation(Color.GREEN, "grass"));
        p.setDisplayInformation(Burrow.class, new DisplayInformation(Color.GREEN, "hole"));

        p.show();
    }
}