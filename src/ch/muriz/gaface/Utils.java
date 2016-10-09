package ch.muriz.gaface;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public final class Utils {

    // Generates an array with numbers from 0 to 100 (100 excluded)
    public static int[] range(int start, int stop) {
        int[] result = new int[stop-start];

        for(int i=0;i<stop-start;i++)
            result[i] = start+i;

        return result;
    }

    // = random.choice()
    public static int getRandomInt(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // chops a list into sublists of length L
    public static <T> List<List<T>> choppedList(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static boolean isAlpha(BufferedImage image, int x, int y) {
        Color pixel = new Color(image.getRGB(x, y), true);
        return pixel.getAlpha() > 0;
    }

    // TODO Change setRGB and getRGB, it is now too slow :(
    // @see http://stackoverflow.com/questions/8218072/faster-way-to-extract-histogram-from-an-image
    public static BufferedImage imageAbsoluteDifference(BufferedImage img1, BufferedImage img2) {
        BufferedImage result = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions are not same");
            System.exit(1);
        }
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int argb0 = img1.getRGB(x, y);
                int argb1 = img2.getRGB(x, y);

                int a0 = (argb0 >> 24) & 0xFF;
                int r0 = (argb0 >> 16) & 0xFF;
                int g0 = (argb0 >>  8) & 0xFF;
                int b0 = (argb0      ) & 0xFF;

                int a1 = (argb1 >> 24) & 0xFF;
                int r1 = (argb1 >> 16) & 0xFF;
                int g1 = (argb1 >>  8) & 0xFF;
                int b1 = (argb1      ) & 0xFF;

                int aDiff = Math.abs(a1 - a0);
                int rDiff = Math.abs(r1 - r0);
                int gDiff = Math.abs(g1 - g0);
                int bDiff = Math.abs(b1 - b0);

                int diff =
                        (aDiff << 24) | (rDiff << 16) | (gDiff << 8) | bDiff;
                result.setRGB(x, y, diff);
            }
        }
        return result;
    }

    // Return an ArrayList containing histogram values for separate R, G, B channels
    public static int[] imageHistogram(BufferedImage input) {
        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];
        int[] result = new int[256];

        for(int i=0; i<rhistogram.length; i++) rhistogram[i] = 0;
        for(int i=0; i<ghistogram.length; i++) ghistogram[i] = 0;
        for(int i=0; i<bhistogram.length; i++) bhistogram[i] = 0;

        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {

                int red = new Color(input.getRGB (i, j)).getRed();
                int green = new Color(input.getRGB (i, j)).getGreen();
                int blue = new Color(input.getRGB (i, j)).getBlue();

                // Increase the values of colors
                rhistogram[red]++; ghistogram[green]++; bhistogram[blue]++;
            }
        }
        for(int n = 0; n < 256; n++) {
            result[n] = rhistogram[n] + ghistogram[n] + bhistogram[n];
        }
        return result;
    }

    /*
     * It reads all the pixels into an array at the beginning,
     * thus requiring only one for-loop.
     * Also, it directly shifts the blue byte to the alpha (of the mask color).
     * Like the other methods, it assumes both images have the same dimensions.
     */
    public static BufferedImage applyGrayscaleMaskToAlpha(BufferedImage image, BufferedImage mask) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);
        int[] maskPixels = mask.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < imagePixels.length; i++) {
            int color = imagePixels[i] & 0x00ffffff; // Mask preexisting alpha
            int alpha = maskPixels[i] << 24; // Shift blue to alpha
            imagePixels[i] = color | alpha;
        }
        image.setRGB(0, 0, width, height, imagePixels, 0, width);
        return image;
    }

    /*
     * Rotates an image
     */
    public static BufferedImage rotateImage(BufferedImage img, double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math
                .abs(Math.cos(Math.toRadians(angle)));
        int w = img.getWidth(null), h = img.getHeight(null);
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math
                .floor(h * cos + w * sin);
        BufferedImage bimg = new BufferedImage(neww, newh,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(img, null);
        g.dispose();
        return bimg;
    }

    public static BufferedImage makeImageTranslucent(BufferedImage source, float alpha) {
        BufferedImage target = new BufferedImage(source.getWidth(),
                source.getHeight(), java.awt.Transparency.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = target.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Draw the image into the prepared reciver image
        g.drawImage(source, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return target;
    }
}

