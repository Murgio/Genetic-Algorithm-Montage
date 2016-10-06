package ch.muriz.gaface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

    private BufferedImage source;
    //private BufferedImage mask;

    // File locations
    // Image the individuals are matched against
    private final String matchFile = "match.png";
    // Sections of image more important than others
    //private final String importantMaskFile = "mask.png";

    public ImageUtils() {
        try {
            source = ImageIO.read(new File(matchFile));
            BufferedImage convertedImg = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImg.getGraphics().drawImage(source, 0, 0, null);
            convertedImg.getGraphics().dispose();
            source = convertedImg;
            //mask = ImageIO.read(new File(importantMaskFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSource() {
        return source;
    }

    /*
     * Creates important areas mask to check for in fitness function
     */
    // TODO Make this work without to read it directly
    // Convert image to an image with only the alpha channel
    public BufferedImage createImportantMask() throws IOException {
        return ImageIO.read(new File("single_mask.png"));
    }

    /*
     * Converts the source image into negative
     */
    public BufferedImage createNegativeImage(BufferedImage negativ) {
        //get image width and height
        int width = negativ.getWidth();
        int height = negativ.getHeight();
        //convert to negative
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = negativ.getRGB(x,y);
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
                negativ.setRGB(x, y, p);
            }
        }
        return negativ;
    }
}