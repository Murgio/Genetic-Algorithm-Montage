package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private int[] baseTypes;
    private int geneLength;
    private int DNALength;
    private List<Integer> mDNA;

    // [x, y, scale, rotation, opacity]
    public static final int INDIVIDUAL_GENE_LENGTH = 5;
    // DNA Base
    public static final int[] INDIVIDUAL_BASE_TYPES = Utils.range(0, 100);

    private final int individualGenes = 250; // Number of images


    public Individual(List<Integer> DNA) {
        baseTypes = INDIVIDUAL_BASE_TYPES;
        geneLength = INDIVIDUAL_GENE_LENGTH;
        // number of images
        DNALength = geneLength * individualGenes;
        if(DNA == null) mDNA = createDNA();
        else mDNA = DNA;
    }

    private List<Integer> createDNA() {
        // Creates a random string of DNA
        List<Integer> DNA = new ArrayList<>(DNALength);
        for(int i = 0; i < DNALength; i++) {
            DNA.add(Utils.getRandomInt(baseTypes));
        }
        return DNA;
    }

    public List<Integer> getDNA() {
        return mDNA;
    }
}

