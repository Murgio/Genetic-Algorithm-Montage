package ch.muriz.gaface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/*
 * The phenotype is the modified source based on DNA data
 * The source is RGBA and a predefined variable; the returned image is RGB
 */

public class Phenotype {

    private ImageUtils imageUtils;
    private final int individualMinOpacity = 50; // [0, 255]

    public BufferedImage createPhenotype(List<Integer> DNA) throws IOException{
        // Create a list with the image instance properties out of the DNA
        // in the form [x, y, scale, rotation, opacity]
        List<List<Integer>> genes = Utils.choppedList(DNA, Individual.INDIVIDUAL_GENE_LENGTH);

        // Create instances of the instance image, and add to the final phenotype
        imageUtils = new ImageUtils();
        BufferedImage source = imageUtils.init("source");
        int w = source.getWidth(), h = source.getHeight();
        BufferedImage phenotype = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = phenotype.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, phenotype.getWidth(), phenotype.getHeight());

        // Creat previously for more speed
        List<BufferedImage> allSizes = imageUtils.createAllSizes();

        // [0, 1,   2,      3,        4]
        // [x, y, scale, rotation, opacity]
        for(List<Integer> gene : genes) {
            // Size
            BufferedImage instance = allSizes.get(gene.get(2));

            // Rotation
            double angle = Math.round(3.6 * gene.get(3));
            instance = Utils.rotateImage(instance, angle);

            // Location
            int dimw = instance.getWidth(), dimh = instance.getHeight();
            int x = ((gene.get(0) * (w + dimw)) / 100) - dimw;
            int y = ((gene.get(1) * (h + dimh)) / 100) - dimh;

            // Opacity
            float opacity = ((((255 - individualMinOpacity) * gene.get(4)) / 100.0f)
                    + individualMinOpacity) / 255.0f;
            instance = Utils.makeImageTranslucent(instance, opacity);
            // Paste finished product into correct location
            graphics2D.drawImage(instance, x, y, null);
        }

        graphics2D.dispose();
        return phenotype;
    }
}
