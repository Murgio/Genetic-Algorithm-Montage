package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

public class Population {

    static List<Individual> individuals;

    public Population(boolean initialise) {
        individuals = new ArrayList<Individual>();
        if(initialise) populationFromScratch(); // Create new random population
    }

    /*
     * Creates new random population
     */
    private void populationFromScratch() {
        for(int i = 0; i < getPopulationSize(); i++) {
            Individual newIndividual = new Individual(null);
            saveIndividual(newIndividual);
        }
    }

    /*
     * Save individual
     */
    private void saveIndividual(Individual indiv) {individuals.add(indiv);}

    public List<List<Integer>> getDNAList() {
        List<List<Integer>> DNAList = new ArrayList<>();
        if(individuals.size() != 0)
            for(Individual indiv : individuals)
                DNAList.add(indiv.getDNA());
        else System.out.println("There are no individuals");

        return DNAList;
    }

    public List<List<Integer>> getSpecificDNAList(int index) {
        List<List<Integer>> specificDNAList = new ArrayList<>();
        if(individuals.size() != 0) specificDNAList.add(individuals.get(index).getDNA());
        else System.out.println("The specific individual doesn't exist");

        return specificDNAList;
    }

    // Get population size
    public int getPopulationSize() {return Algorithm.POPULATION_SIZE;}
}
