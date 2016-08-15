package ch.muriz.gaface;

import java.io.IOException;
import java.util.*;

/**
 * Created by Muriz on 15.08.16.
 */
public class Algorithm {
    private static int size = Settings.POPULATION_SIZE;
    private static float crossoverRate = Settings.POPULATION_CROSSOVER_RATE;
    private static float mutationRate = Settings.POPULATION_MUTATION_RATE;
    static Random rand = new Random();
    static Fitness fitness;
    static List<Individual> individuals;

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        fitness = new Fitness();
        Population newPopulation = new Population(false);
        List<List<Double>> DNAList = pop.getDNAList();
        List<Double> fitnessList = new ArrayList<Double>();
        for(List<Double> list : DNAList) {
            try {
                fitnessList.add(fitness.calculateFitness(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<List<Double>> individualFitness = new ArrayList<>();
        List<Double> index = new ArrayList<Double>();
        List<Double> fitness = new ArrayList<Double>();
        List<Double> mergedList = new ArrayList<Double>();
        for(int i = 0; i < fitnessList.size(); i++) {
            index.add((double)i);
            fitness.add(fitnessList.get(i));
        }
        for(double index1 : index) {
            for(double index2 : fitness) {
                mergedList.add(index2);
                mergedList.add(index1);
            }
        }
        individualFitness = Utils.choppedList(mergedList, 2);
        Collections.sort(individualFitness, new Comparator<List<Double>>() {
            public int compare(List<Double> list1, List<Double> list2) {
                //Simple comparison here
                return list1.get(0).compareTo(list2.get(0));
            }
        });
        Collections.reverse(mergedList);
        for(int i = 0; i < (size/2); i++) {
            List<List<Double>> matingDNA = new ArrayList<>();
            List<Double> matingPair = tournamentSelection(individualFitness);
            for(double individualNumber : matingPair) {
                matingDNA.add(pop.getDNAList().get((int)individualNumber));
            }

            //Crossover the mating pair's DNA so many times according to crossover rate
            int crossovers = 0;
            if(crossoverRate > 1.0f) {
                while(crossoverRate - crossovers > 1) {
                    matingDNA = crossover(matingDNA);
                    crossovers++;
                }
            }
            if(Math.random() <= crossoverRate - crossovers) {
                matingDNA = crossover(matingDNA);
            }

            // Mutate each mate's DNA so many times accorind to mutation rate
            for(int n = 0; n < 2; n++) {
                int mutations = 0;
                if(mutationRate > 1) {
                    while(mutationRate - mutations > 1) {
                        matingDNA.set(n, mutate(matingDNA.get(n)));
                        mutations++;
                    }
                }
                if(Math.random() <= mutationRate-mutations) {
                    matingDNA.set(n, matingDNA.get(n));
                }
            }

            // Add a new individual based on the newly mutated/crossed over DNA to the population
            for(List<Double> DNA : matingDNA) {
                individuals.add(new Individual(DNA));
            }
        }
        return newPopulation;
    }

    /*
     * Determines what individuals to mate
     * Based on a list of (fitness, individualNumber) tuples
     * Uses tournament selection
     */
    private static List<Double> tournamentSelection(List<List<Double>> individualFitness) {
        int tournamentSize = (int)Math.ceil(individualFitness.size() * Settings.TOURNAMENT_FRACTION);
        if(tournamentSize == 1) tournamentSize = 2;

        // Create tournament
        int listCount = individualFitness.size();
        List<List<Double>> tournament = new ArrayList<>();
        while(tournament.size() < tournamentSize) {
            int chosenIndividual = rand.nextInt(listCount);
            if(!tournament.contains(individualFitness.get(chosenIndividual))) {
                tournament.add(individualFitness.get(chosenIndividual));
            }
        }
        // Return two most fit individuals from tournament
        Collections.sort(tournament, new Comparator<List<Double>>() {
            public int compare(List<Double> list1, List<Double> list2) {
                //Simple comparison here
                return list1.get(0).compareTo(list2.get(0));
            }
        });
        Collections.reverse(tournament);
        List<Double> floatList1 = tournament.get(0);
        List<Double> floatList2 = tournament.get(1);
        List<Double> resultList = new ArrayList<>();
        resultList.add(floatList1.get(1)); resultList.add(floatList2.get(1));
        return resultList;
    }

    /*
     * Crosses over two pieces of mating DNA
     */
    private static List<List<Double>> crossover(List<List<Double>> matingDNA) {
        int pivot = rand.nextInt(matingDNA.get(0).size());
        List<List<Double>> result = new ArrayList<>();
        List<Double> firstMatingDNA = matingDNA.get(0);
        List<Double> secondMatingDNA = matingDNA.get(1);
        List<Double> firstPart = firstMatingDNA.subList(0, firstMatingDNA.size()-pivot);
        List<Double> secondPart = secondMatingDNA.subList(secondMatingDNA.size()-pivot, secondMatingDNA.size()+1);
        ArrayList<Double> firstResult = new ArrayList<Double>(firstPart);
        firstResult.addAll(secondPart);
        result.add(firstResult);

        List<Double> firstPartList2 = secondMatingDNA.subList(0, secondMatingDNA.size()-pivot);
        List<Double> secondPartList2 = firstMatingDNA.subList(firstMatingDNA.size()-pivot, firstMatingDNA.size()+1);
        ArrayList<Double> secondResult = new ArrayList<Double>(firstPartList2);
        secondResult.addAll(secondPartList2);
        result.add(secondResult);
        return result;
    }

    /*
     * Randomly mutates a piece of DNA
     */
    private static List<Double> mutate(List<Double> DNA) {
        int chosenBase = rand.nextInt(DNA.size());
        int chosenBaseType = Settings.INDIVIDUAL_BASE_TYPES[rand.nextInt(Settings.INDIVIDUAL_BASE_TYPES.length)];
        DNA.set(chosenBase, (double)chosenBaseType);
        return DNA;
    }
}
