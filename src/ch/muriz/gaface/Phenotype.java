package ch.muriz.gaface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * The phenotype is the modified source based on DNA data
 * The source is RGBA and a predefined variable; the returned image is RGB
 */

public class Phenotype {

    private final int individualMinOpacity = 50; // [0, 255]
    private final float individualMinScale = 0.1f; // (0, 1]
    private final float individualMaxScale = 1.0f; // (0, 1]

    private List<BufferedImage> alphaSourceSizes = new ArrayList<>();

    /*
     * Image the individuals are based on
     */
    private BufferedImage getAlphaSource() throws IOException {
        BufferedImage alphaSource = ImageIO.read(new File("instance.png"));
        if (alphaSource.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage convertedImg = new BufferedImage(alphaSource.getWidth(), alphaSource.getHeight(), BufferedImage.TYPE_INT_ARGB);
            convertedImg.getGraphics().drawImage(alphaSource, 0, 0, null);
            convertedImg.getGraphics().dispose();
            alphaSource = convertedImg;
            return alphaSource;
        } else {
            System.out.println("Phenotype.java: We got an exception over here mister, pls fix this.");
            return alphaSource;
        }
    }

    /*
     * Create all the possible sizes of images for speeding up
     */
    public List<BufferedImage> createAllSizes() throws IOException {
        BufferedImage alphaSource = getAlphaSource();
        for(int n : Individual.INDIVIDUAL_BASE_TYPES) {
            float scale = n/100.0f;
            scale = (scale * (individualMaxScale - individualMinScale))
                    + individualMinScale;
            BufferedImage instance = Utils.resize(alphaSource, Math.round((alphaSource.getWidth()) * scale),
                    Math.round((alphaSource.getWidth()) * scale));
            alphaSourceSizes.add(instance);
        }
        return alphaSourceSizes;
    }

    public BufferedImage createPhenotype(List<Integer> DNA) throws IOException {
        ImageUtils imageUtils = new ImageUtils();
        // Create a list with the image instance properties out of the DNA
        // in the form [x, y, scale, rotation, opacity]
        List<List<Integer>> genes = Utils.choppedList(DNA, Individual.INDIVIDUAL_GENE_LENGTH);

        // Create instances of the instance image, and add to the final phenotype
        BufferedImage source = imageUtils.getSource();
        int w = source.getWidth(), h = source.getHeight();
        BufferedImage phenotype = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = phenotype.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, phenotype.getWidth(), phenotype.getHeight());

        // Creat previously for more speed
        List<BufferedImage> allSizes = createAllSizes();

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
