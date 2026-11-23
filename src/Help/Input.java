package Help;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//IOExceptions indgår dér hvor en fil-læsning kan fejle
//Klassen indlæser alt information fra en tekst-fil, så det er parat til at bruges i simulationen

public class Input {

    //Opretter en ny entry, i form af én linje fra .txt fil pr. new Entry(...)
    public static class Entry {
        private String type;
        private int amount;

        public Entry(String type, int amount) {
            this.type = type;
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public int getAmount() {
            return amount;
        }
    }

    private int worldSize;

    //Gemmer alle entries i en liste
    private List<Entry> entries = new ArrayList<>();
    private Random random = new Random();

    //Indlæser filen og kører read()
    public Input(String path) throws IOException {
        read(path);
    }

    //Indlæser tekstfilen til en ny Input-objekt
    public void read(String path) throws IOException {
        //Læser filen og gemmer det i en liste med én streng pr. linje.
        List<String> lines = Files.readAllLines(Path.of(path));
        boolean sizeRead = false;

        for (String index : lines) {
            String line = index.trim();
            //continue hopper videre til næste index, hvis line er tom
            if (line.isEmpty()) continue;

            //Parser den første linje (world size) til en int
            if (!sizeRead) {
                worldSize = Integer.parseInt(line);
                sizeRead = true;
                continue;
            }

            //Håndterer de næste par linjer f.eks. "Rabbit 2-4"
            String[] parts = line.split("\\s+");
            if (parts.length < 2) {
                throw new IOException("Invalid line in: " + path);
            }
            //Gemmer en ny entry i entries-listen
            //parseAmount() kaldes på tallene for at lave dem om til int
            entries.add(new Entry(parts[0].toLowerCase(), parseAmount(parts[1])));
        }

        //Hvis nu filen var tom ti at starte med
        if (!sizeRead) throw new IOException("Empty input file: " + path);
    }

    
    public int parseAmount(String text) {
        //Hvis text indeholder et interval, så vælg en tilfældig tal fra intervallet
        if (text.contains("-")) {
            String[] range = text.split("-");
            int min = Integer.parseInt(range[0]);
            int max = Integer.parseInt(range[1]);
            return min + random.nextInt(max - min + 1);
        }
        return Integer.parseInt(text);
    }

    public int getWorldSize() {
        return worldSize;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}