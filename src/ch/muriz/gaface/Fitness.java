package ch.muriz.gaface;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Created by Muriz on 13.08.16.
 */

public class Fitness {

    private Phenotype phenotypeObject;
    private ImageUtils imageUtils;
    // How much more they add to fitness compared to other areas
    private final int importantAreasScale = 3;
    private double similarityMin;
    private double similarityMax;
    private BufferedImage source;
    private BufferedImage blackWhiteMask;
    // Sections of image more important than others
    private BufferedImage mask;

    /*
     * Create image to match fitness against; get max/min fitness values
     */
    public Fitness() {
        long start = System.currentTimeMillis();
        phenotypeObject = new Phenotype();
        imageUtils = new ImageUtils();
        BoxBlurFilter blurFilter = new BoxBlurFilter();
        blurFilter.setHRadius(2); blurFilter.setRadius(2); blurFilter.setIterations(1);
        try {
            source = imageUtils.getSource();
            mask = ImageIO.read(new File("mask.png"));
            blackWhiteMask = createBlackWhiteMask();

            similarityMin = calculateImageSimilarity(imageUtils.createNegativeImage(source), source);
            similarityMax = calculateImageSimilarity(blurFilter.filter(source, null), source);
        } catch(IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("FitnessConstructor: "+ (end-start)/1000f);
    }

    /*
     * Determines the fitness of the individual by creating and matching
     * the individual's phenotype with the source phenotype (a predefined image)
     */
    public double calculateFitness(List<Integer> DNA) throws IOException {
        BufferedImage phenotype = phenotypeObject.createPhenotype(DNA);
        double similarity = calculateImageSimilarity(phenotype, source);
        return ((similarity - similarityMin) / (similarityMax - similarityMin))*100;
    }

    /*
     * Calculates the difference between two images
     * Uses root mean squared analysis
     * If specified, mask is used for the histogram
     */
    private double simpleImageSimilarity(BufferedImage image, BufferedImage match, BufferedImage mask) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage difference = Utils.imageAbsoluteDifference(image, match);

        // Create histogram with a mask if image is RGBA
        if(mask != null) {
            // TODO Take the parameter mask and calculate the histogram with the mask
        }
        int[] histogram = Utils.imageHistogram(difference);
        long sumSquaredValues = 0;
        long square;
        for(long n : histogram) {
            square = n*n;
            sumSquaredValues += square;
        }
        return 1/ (Math.sqrt(sumSquaredValues / (w*h)));
    }

    /*
     * Uses simpleImageSimilarity() and any masks
     * importantMask = Single Layer Mask, only the alpha channel.
     * importantMaskRGB = mask.png drew over top of a white, blank image
     */
    private double calculateImageSimilarity(BufferedImage image, BufferedImage match) throws IOException {
        double similarity = simpleImageSimilarity(image, match, null);
        blackWhiteMask = null;
        if(blackWhiteMask != null) {
            BufferedImage maskedImage = new BufferedImage(blackWhiteMask.getWidth(),
                    blackWhiteMask.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D maskedGraphics = maskedImage.createGraphics();
            maskedGraphics.setBackground(Color.WHITE);
            maskedGraphics.clearRect(0, 0, maskedImage.getWidth(), maskedImage.getHeight());
            maskedGraphics.dispose();
            BufferedImage importantMaskRGB = maskedImage;
            maskedImage = Utils.applyGrayscaleMaskToAlpha(image, blackWhiteMask);
            Graphics2D importantMaskRGBGraphics = importantMaskRGB.createGraphics();
            importantMaskRGBGraphics.drawImage(mask, 0, 0, null);
            importantMaskRGBGraphics.dispose();
            similarity += simpleImageSimilarity(maskedImage, importantMaskRGB, blackWhiteMask) * importantAreasScale;
        }
        return similarity;
    }

    /*
     * Creates important areas mask to check for in fitness function
     * Returns the image with only the alpha channel
     */
    public BufferedImage createBlackWhiteMask() throws IOException {
        BufferedImage alphaChannel = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < alphaChannel.getWidth(); x++) for (int y = 0; y < alphaChannel.getHeight(); y++) {
                // blue = pix & 0xFF; green = (pix>>8) & 0xFF; red = (pix>>16) & 0xFF; alpha = (pix>>24) & 0xFF;
                int alpha = (mask.getRGB(x, y) >> 24) & 0xff;
                // Every transparent pixel is now black
                if (alpha == 0) alphaChannel.setRGB(x, y, Color.black.getRGB());
                // Every ohter pixel is now white
                else alphaChannel.setRGB(x, y, Color.white.getRGB());
        }
        return alphaChannel;
    }
}