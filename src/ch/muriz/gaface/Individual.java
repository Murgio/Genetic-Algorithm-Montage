package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    // [x, y, scale, rotation, opacity]
    public static final int INDIVIDUAL_GENE_LENGTH = 5;
    // DNA Base
    public static final int[] INDIVIDUAL_BASE_TYPES = Utils.range(0, 100);

    // Number of images
    private final int DNALength = 1250; // INDIVIDUAL_GENE_LENGTH * individualGenes;
    private List<Integer> mDNA;

    public Individual(List<Integer> DNA) {
        if(DNA == null) mDNA = createDNA();
        else mDNA = new ArrayList<>(DNA);
    }

    private List<Integer> createDNA() {
        // Creates a random string of DNA
        List<Integer> DNA = new ArrayList<>();
        for(int i = 0; i < DNALength; i++)
            DNA.add(Utils.getRandomInt(INDIVIDUAL_BASE_TYPES));
        return DNA;
    }

    public List<Integer> getDNA() {
        return mDNA;
    }
}

