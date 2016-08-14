package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private int size = Settings.POPULATION_SIZE;
    private float crossoverRate = Settings.POPULATION_CROSSOVER_RATE;
    private float mutationRate = Settings.POPULATION_MUTATION_RATE;

    public void checkPopulation() {
    }

    /*
     * Creates new random population
     */
    private void populationFromScratch() {

    }


    /*
     * Creates a population based on an old DNA list or another population instance
     */
    private void createPopulation() {

    }

    /*
     * Determines what individuals to mate
     * Based on a list of (fitness, individualNumber) tuples
     * Uses tournament selection
     */
    private List<Integer> determineMatingPairTournament(ArrayList<ArrayList<Number>> individualFitness) {
        int tournamentSize = (int)Math.ceil(individualFitness.size() * Settings.TOURNAMENT_FRACTION);
        if(tournamentSize == 1) tournamentSize  = 2;

        // Create tournament
        ArrayList<ArrayList<Number>> tournament = new ArrayList<>();
        return null;

    }

}
