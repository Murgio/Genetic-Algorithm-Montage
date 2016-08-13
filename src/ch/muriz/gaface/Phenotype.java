package ch.muriz.gaface;

import java.awt.image.BufferedImage;
import java.util.List;

/*
 * The phenotype is the source modified based on DNA data
 * The source is RGBA and a predefined variable; the returned image is RGB
 */

public class Phenotype {

    public BufferedImage createPhenotype(List<Integer> DNA) {
        // Create a list with the image instance properties out of the DNA
        // in the form [x, y, scale, rotation, opacity]
        List<List<Integer>> parts = Utils.choppedList(DNA, Settings.INDIVIDUAL_GENE_LENGTH);

        // Create instances of the instance image, and add to the final phenotype
        return null;
    }
}
