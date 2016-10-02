package ch.muriz.gaface;

import java.io.File;

/*
 * Creates a new version of an image using instances of the
 * same image which are overlaid and transparent in different levels.
 * Uses genetic algorithms to create the final image.
 */

/**
 * Created by Muriz on 13.08.16.
 */

public class FaceGen {

    public static void main(String[] args) {
        // Location to store status information
        final String statusDirection = "/Users/Muriz/Desktop/face_test";

        // Number of new populations generated
        final int generations = 100;

        // Every so many generations, write/print a status update
        final int statusInterval = 1;

        //Clear out the status directory
        File dir = new File(statusDirection);
        purgeDirectory(dir);

        //region Future Update
        /*// Create the intitial population
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
            if(oldDNA.size() == Algorithm.POPULATION_SIZE) {
                System.out.println("Creating population based on source generation file...");

            } else {
                System.out.println("Source generation file failed. Creating population from scratch...");
                Population population = new Population(true);
            }
        } else {
            System.out.println("Creating population from scratch...");
            Population population = new Population(true);
        }*/
        //endregion

        //  ( ͡° ͜ʖ ͡°) Woohoo, first Population!
        Population population = new Population(true);

        // Create new populations
        // Every so many generations or the last generation, request a status update
        for(int i = 0; i < generations; i++) {
            long startTime = System.currentTimeMillis();
            //          Update Interval   || Maximum of generations created
            if((i % statusInterval == 0) || i == (generations-1))
                population = Algorithm.evolvePopulation(population, true, i); // Save the population and the picture
            else population = Algorithm.evolvePopulation(population, false, 0);
            long endTime = System.currentTimeMillis();
            System.out.println("Generation " + i + ": " + ((endTime-startTime)/1000L) + " seconds");
        }
    }

    private static void purgeDirectory(File dir) {
        for(File file: dir.listFiles()) if (!file.isDirectory()) file.delete();
    }
}