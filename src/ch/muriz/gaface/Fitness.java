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

    private Phenotype phenotypeObject = new Phenotype();
    // How much more they add to fitness compared to other areas
    private final int importantAreasScale = 3;
    private ImageUtils imageUtils = new ImageUtils();
    private double similarityMin;
    private double similarityMax;
    private final BufferedImage source = imageUtils.getSource();

    /*
     * Create image to match fitness against; get max/min fitness values
     */
    public Fitness() {
        BoxBlurFilter blurFilter = new BoxBlurFilter();
        blurFilter.setHRadius(2); blurFilter.setRadius(2); blurFilter.setIterations(1);
        try {
            BufferedImage negativ = imageUtils.createNegativeImage(source);
            BufferedImage blur = blurFilter.filter(source, null);
            similarityMin = calculateImageSimilarity(negativ, source);
            //ImageIO.write(negativ, "png", new File("negativ_java"+ ".png"));
            similarityMax = calculateImageSimilarity(blur, source);
            //ImageIO.write(blur, "png", new File("blur_java"+ ".png"));
            System.out.println("GOOTCHAA");
        } catch(IOException e) {
            e.printStackTrace();
        }
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
     * TODO Take the parameter mask and calculate the histogram with only the mask
     */
    private double simpleImageSimilarity(BufferedImage image, BufferedImage match, BufferedImage mask) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage difference = Utils.imageAbsoluteDifference(image, match);
        // Create histogram with a mask if image is RGBA
        //BufferedImage maskWithAlpha = ImageIO.read(new File("mask.png"));
        int[] histogram = Utils.imageHistogram(difference);
        long sumSquaredValues = 0;
        long square = 0;
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
        BufferedImage importantMask = imageUtils.createImportantMask();
        BufferedImage mask = ImageIO.read(new File("mask.png"));
        BufferedImage maskedImage = new BufferedImage(importantMask.getWidth(),
                importantMask.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.setBackground(Color.WHITE);
        maskedGraphics.clearRect(0, 0, maskedImage.getWidth(), maskedImage.getHeight());
        maskedGraphics.dispose();
        BufferedImage importantMaskRGB = maskedImage;
        maskedImage = Utils.applyGrayscaleMaskToAlpha(image, importantMask);
        Graphics2D importantMaskRGBGraphics = importantMaskRGB.createGraphics();
        importantMaskRGBGraphics.drawImage(mask, 0, 0, null);
        importantMaskRGBGraphics.dispose();
        similarity += simpleImageSimilarity(maskedImage, importantMaskRGB, importantMask) * importantAreasScale;

        return similarity;
    }
}