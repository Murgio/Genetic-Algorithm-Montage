package ch.muriz.gaface;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by Muriz on 13.08.16.
 */

public class Fitness {

    /*
     * Calculates the difference between two images
     * Uses root mean squared analysis
     * If specified, mask is used for the histogram
     */
    public double simpleImageSimilarity(BufferedImage image, BufferedImage match, BufferedImage mask) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage difference = Utils.imageAbsoluteDifference(image, match);
        // Create histogram with a mask if image is RGBA
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
     * If no mask is available
     */
    public double simpleImageSimilarity(BufferedImage image, BufferedImage match) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage difference = Utils.imageAbsoluteDifference(image, match);
        // Create histogram with a mask if image is RGBA
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
    public double calculateImageSimilarity(BufferedImage image, BufferedImage match) throws IOException {
        double similarity = simpleImageSimilarity(image, match);
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage importantMask = imageUtils.createImportantMask();
        BufferedImage mask = ImageIO.read(new File("mask.png"));
        BufferedImage maskedImage = new BufferedImage(importantMask.getWidth(),
                importantMask.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.setBackground(Color.WHITE);
        maskedGraphics.clearRect(0, 0, maskedImage.getWidth(), maskedImage.getHeight());
        BufferedImage importantMaskRGB = maskedImage;
        maskedImage = Utils.applyGrayscaleMaskToAlpha(image, importantMask);
        Graphics2D importantMaskRGBGraphics = importantMaskRGB.createGraphics();
        importantMaskRGBGraphics.drawImage(mask, 0, 0, null);
        similarity += simpleImageSimilarity(maskedImage, importantMaskRGB, importantMask) * Settings.IMPORTANT_AREAS_SCALE;

        return similarity;
    }

}

