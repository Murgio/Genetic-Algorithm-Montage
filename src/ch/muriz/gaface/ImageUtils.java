package ch.muriz.gaface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtils {

    private BufferedImage source;
    private BufferedImage alphaSource;
    private BufferedImage mask;
    private List<BufferedImage> alphaSourceSizes;

    // File locations
    // Image the individuals are matched against
    private final String matchFile = "match.png";
    // Image the individuals are based on
    private final String instanceFile = "instance.png";
    // Sections of image more important than others
    private final String importantMaskFile = "mask.png";

    private final float individualMinScale = 0.1f; // (0, 1]
    private final float individualMaxScale = 1.0f; // (0, 1]

    public ImageUtils() {
        try {
            source = ImageIO.read(new File(matchFile));
            alphaSource = ImageIO.read(new File(instanceFile));
            mask = ImageIO.read(new File(importantMaskFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Create the alphaSource image which the individuals will be created from
     * and the source image which individuals will be matched against
     */
    public BufferedImage init(String picture) throws IOException {
        if("source".equals(picture) || "Source".equals(picture)) {
            if (source.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage convertedImg = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
                convertedImg.getGraphics().drawImage(source, 0, 0, null);
                convertedImg.getGraphics().dispose();
                source = convertedImg;
                return source;
            } else return source;
        }
        else if("alpha".equals(picture) || "Alpha".equals(picture)) {
            if (alphaSource.getType() != BufferedImage.TYPE_INT_ARGB) {
                BufferedImage convertedImg = new BufferedImage(alphaSource.getWidth(), alphaSource.getHeight(), BufferedImage.TYPE_INT_ARGB);
                convertedImg.getGraphics().drawImage(alphaSource, 0, 0, null);
                convertedImg.getGraphics().dispose();
                alphaSource = convertedImg;
                return alphaSource;
            } else return alphaSource;
        }
        else System.out.println("Init failed. Only acceptable Source or Alpha");

        return null;
    }

    /*
     * Create all the possible sizes of images for speeding up
     */
    public List<BufferedImage> createAllSizes() {
        alphaSourceSizes = new ArrayList<>();
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

    /*
     * Creates important areas mask to check for in fitness function
     */
    // TODO Make this work without to read it directly
    // Convert image to an image with only the alpha channel
    public BufferedImage createImportantMask() throws IOException {
        BufferedImage singleMask = ImageIO.read(new File("single_mask.png"));
        return singleMask;
    }

    /*
     * Converts the source image into negative
     */
    public BufferedImage createNegativeImage() {
        BufferedImage img = source;
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();
        //convert to negative
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = img.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
}