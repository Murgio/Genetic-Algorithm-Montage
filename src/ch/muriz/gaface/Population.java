package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

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

    public List<List<Integer>> getDNAList() {
        List<List<Integer>> DNAList = new ArrayList<>();
        if(individuals.length != 0) {
            for(Individual indiv : individuals) {
                DNAList.add(indiv.getDNA());
            }
        } else {
            System.out.println("There are no individuals");
        }
        return DNAList;
    }

    public List<List<Integer>> getSpecificDNAList(int index) {
        List<List<Integer>> specificDNAList = new ArrayList<>();
        if(individuals.length != 0) {
            specificDNAList.add(individuals[index].getDNA());
        } else {
            System.out.println("The specific individual doesn't exist");
        }
        return specificDNAList;
    }

    // Get population size
    public int size() {
        return individuals.length;
    }
}
