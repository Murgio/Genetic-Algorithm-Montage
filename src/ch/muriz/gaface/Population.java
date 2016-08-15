package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

public class Population {
    private int size = Settings.POPULATION_SIZE;
    private float crossoverRate = Settings.POPULATION_CROSSOVER_RATE;
    private float mutationRate = Settings.POPULATION_MUTATION_RATE;
    Random rand = new Random();

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
    public List<Float> determineMatingPairTournament(List<ArrayList<Float>> individualFitness) {
        int tournamentSize = (int)Math.ceil(individualFitness.size() * Settings.TOURNAMENT_FRACTION);
        if(tournamentSize == 1) tournamentSize = 2;

        // Create tournament
        int listCount = individualFitness.size();
        List<ArrayList<Float>> tournament = new ArrayList<>();
        while(tournament.size() < tournamentSize) {
            int chosenIndividual = rand.nextInt(listCount);
            if(!tournament.contains(individualFitness.get(chosenIndividual))) {
                tournament.add(individualFitness.get(chosenIndividual));
            }
        }
        // Return two most fit individuals from tournament
        Collections.sort(tournament, new Comparator<List<Float>>() {
            public int compare(List<Float> list1, List<Float> list2) {
                //Simple comparison here
                return list1.get(0).compareTo(list2.get(0));
            }
        });
        Collections.reverse(tournament);
        List<Float> floatList1 = tournament.get(0);
        List<Float> floatList2 = tournament.get(1);
        List<Float> resultList = new ArrayList<>();
        resultList.add(floatList1.get(1)); resultList.add(floatList2.get(1));
        return resultList;
    }

    /*
     * Crosses over two pieces of mating DNA
     */
    private List<Integer> crossOver(List<Integer> matinDNA) {
        int pivot = 0;
        int[] pivotArray = Utils.range(0, matinDNA.get(0));
        int randomPivot = new Random().nextInt(pivotArray.length);
        pivot = pivotArray[randomPivot];
        
        return null;
    }
}
