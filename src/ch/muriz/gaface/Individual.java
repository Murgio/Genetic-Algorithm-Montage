package ch.muriz.gaface;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private int[] baseTypes;
    private int geneLength;
    private int DNALength;
    private List<Integer> mDNA;

    Individual() {
        baseTypes = Settings.INDIVIDUAL_BASE_TYPES;
        geneLength = Settings.INDIVIDUAL_GENE_LENGTH;
        // number of images
        DNALength = geneLength * Settings.INDIVIDUAL_GENES;
        if(mDNA == null) {
            mDNA = createDNA();
        }
    }

    public List<Integer> createDNA() {
        // Creates a random string of DNA
        List<Integer> DNA = new ArrayList<Integer>(DNALength);
        for(int i = 0; i < DNALength; i++) {
            DNA.add(Utils.getRandomInt(baseTypes));
        }
        return DNA;
    }

    public List<Integer> getDNA() {
        return mDNA;
    }
}

