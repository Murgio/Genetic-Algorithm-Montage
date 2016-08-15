package ch.muriz.gaface;

public class Population {

    Individual[] individuals;

    public Population(boolean initialise) {
        individuals = new Individual[Settings.POPULATION_SIZE];
        checkPopulation(initialise);
    }

    private void checkPopulation(boolean initialise) {
        if(initialise) {
            // Create new random population
            populationFromScratch();
        }
    }

    /*
     * Creates new random population
     */
    private void populationFromScratch() {
        for(int i = 0; i < size(); i++) {
            Individual newIndividual = new Individual(null);
            saveIndividual(i, newIndividual);
        }
    }

    /*
     * Save individual
     */
    private void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    // Get population size
    public int size() {
        return individuals.length;
    }
}
