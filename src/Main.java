import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import Help.Burrow;
import Help.Helpers;

public class Main {

    public static void main(String[] args) {
        int size = 15; //midlertidig size til Burrow
        Program p = new Program(size, 800, 500);

        World w = p.getWorld();

        // w.setTile(new Location(0, 0), new <MyClass>());

        // p.setDisplayInformation(<MyClass>.class, new DisplayInformation(<Color>, "<ImageName>"));

        //                       --- Midlertidig Random() Burrow logik ---
        // Tilføj Burrow ved en tilfældig plads
        int amount = 1; // Midlertidig værdi for mængden af Burrows
        
        for(int i = 0; i < amount; i++) {
            Location l = Helpers.getRandomEmptyTile(w);

            w.setTile(l, new Burrow());
        }

        p.show();
    }
}