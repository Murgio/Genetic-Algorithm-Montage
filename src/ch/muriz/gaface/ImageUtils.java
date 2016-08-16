package ch.muriz.gaface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtils {

    BufferedImage source;
    BufferedImage alphaSource;
    BufferedImage mask;
    public List<BufferedImage> ALPHA_SOURCE_SIZES;

    // File locations
    // Image the individuals are matched against
    public static final String MATCH_FILE = "match.png";
    // Image the individuals are based on
    public static final String INSTANCE_FILE = "instance.png";
    // Sections of image more important than others
    public static final String IMPORTANT_MASK_FILE = "mask.png";

    public static final float INDIVIDUAL_MIN_SCALE = 0.1f; // (0, 1]
    public static final float INDIVIDUAL_MAX_SCALE = 1.0f; // (0, 1]

    ImageUtils() {
        try {
            source = ImageIO.read(new File(MATCH_FILE));
            alphaSource = ImageIO.read(new File(INSTANCE_FILE));
            mask = ImageIO.read(new File(IMPORTANT_MASK_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Create the alphaSouce image which the individuals will be created from
     * and the source image which individuals will be matched against
     */
    public BufferedImage init(String picture) throws IOException {
        if(picture.equals("source") || picture.equals("Source")) {
            if (source.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage convertedImg = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
                convertedImg.getGraphics().drawImage(source, 0, 0, null);
                convertedImg.getGraphics().dispose();
                source = convertedImg;
                return source;
            } else System.out.println("Image source is already type RGB");
        }
        if(picture.equals("alpha") || picture.equals("Alpha")) {
            if (alphaSource.getType() != BufferedImage.TYPE_INT_ARGB) {
                BufferedImage convertedImg = new BufferedImage(alphaSource.getWidth(), alphaSource.getHeight(), BufferedImage.TYPE_INT_ARGB);
                convertedImg.getGraphics().drawImage(alphaSource, 0, 0, null);
                convertedImg.getGraphics().dispose();
                alphaSource = convertedImg;
                return alphaSource;
            } else System.out.println("Image alphaSource is already type RGBA");
        } else System.out.println("Init failed. Only acceptable Source or Alpha");

        return null;
    }

    /*
     * Create all the possible sizes of images for speeding up
     */
    public List<BufferedImage> createAllSizes() {
        ALPHA_SOURCE_SIZES = new ArrayList<BufferedImage>();
        for(int n : Individual.INDIVIDUAL_BASE_TYPES) {
            float scale = n/100.0f;
            scale = (scale * (INDIVIDUAL_MAX_SCALE - INDIVIDUAL_MIN_SCALE))
                    + INDIVIDUAL_MIN_SCALE;
            BufferedImage instance = Utils.resize(alphaSource, Math.round((alphaSource.getWidth()) * scale),
                    Math.round((alphaSource.getWidth()) * scale));
            ALPHA_SOURCE_SIZES.add(instance);
        }
        return ALPHA_SOURCE_SIZES;
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