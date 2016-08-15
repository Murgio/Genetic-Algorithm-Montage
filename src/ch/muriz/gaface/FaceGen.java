package ch.muriz.gaface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Creates a new version of an image using instances of the
 * same image which are overlaid and transparent in different levels.
 * Uses genetic algorithms to create the final image.
 */

/**
 * Created by Muriz on 13.08.16.
 */

public class FaceGen {

    public static void main(String[] args) throws FileNotFoundException{
        /*//Clear out the status directory
        File dir = new File(Settings.STATUS_DIR);
        purgeDirectory(dir);

        // Create the intitial population
        if(Settings.SOURCE_GENERATION_FILE != null) {
            System.out.println("Loading source generation file...");
            Scanner scanner = new Scanner(new File(Settings.SOURCE_GENERATION_FILE));
            List<Integer> oldDNA = new ArrayList<Integer>();
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    oldDNA.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
            scanner.close();
            if(oldDNA.size() == Settings.POPULATION_SIZE) {
                System.out.println("Creating population based on source generation file...");

            } else {
                System.out.println("Source generation file failed. Creating population from scratch...");
                Population population = new Population();
                population.checkPopulation();
            }
        } else {
            System.out.println("Creating population from scratch...");
            //  ( ͡° ͜ʖ ͡°) Woohoo, new Population!
            Population population = new Population();
            population.checkPopulation();
        }*/



    }

    private static void purgeDirectory(File dir) {
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}