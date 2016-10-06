package ch.muriz.gaface;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * A convenience class which implements those methods of BufferedImageOp which are rarely changed.
 */
public abstract class AbstractBufferedImageOp implements BufferedImageOp {

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel colorModel) {
        ColorModel newColorModel = colorModel;
        if (colorModel == null) newColorModel = src.getColorModel();
        return new BufferedImage(newColorModel, newColorModel.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), newColorModel.isAlphaPremultiplied(), null);
    }

    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    public Point2D getPoint2D( Point2D srcPt, Point2D point2D ) {
        Point2D newPoint2D = point2D;
        if (point2D == null) newPoint2D = new Point2D.Double();
        newPoint2D.setLocation( srcPt.getX(), srcPt.getY() );
        return newPoint2D;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    /**
     * A convenience method for getting ARGB pixels from an image. This tries to avoid the performance
     * penalty of BufferedImage.getRGB unmanaging the image.
     */
    public int[] getRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
        int type = image.getType();
        if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
            return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
        return image.getRGB( x, y, width, height, pixels, 0, width );
    }

    /**
     * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
     * penalty of BufferedImage.setRGB unmanaging the image.
     */
    public void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
        int type = image.getType();
        if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
            image.getRaster().setDataElements( x, y, width, height, pixels );
        else
            image.setRGB( x, y, width, height, pixels, 0, width );
    }
}
