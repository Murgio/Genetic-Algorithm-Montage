package ch.muriz.gaface;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtils {

    // Image the individuals are matched against
    private BufferedImage source;
    private final String matchFile = "match.png";

    public ImageUtils() {
        try {
            source = ImageIO.read(new File(matchFile));
            BufferedImage convertedImg = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImg.getGraphics().drawImage(source, 0, 0, null);
            convertedImg.getGraphics().dispose();
            source = convertedImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSource() {
        return source;
    }

    /*
     * Converts the source image into negative
     */
    public BufferedImage createNegativeImage(BufferedImage original) {
        BufferedImage negativImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        //get image width and height
        int width = original.getWidth();
        int height = original.getHeight();
        //convert to negative
        for(int y = 0; y < height; y++) for(int x = 0; x < width; x++) {
                int p = original.getRGB(x,y);
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
                negativImage.setRGB(x, y, p);
        }
        return negativImage;
    }
}