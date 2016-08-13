package ch.muriz.gaface;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/*
 * Creates a new version of an image using instances of the
 * same image which are overlaid and transparent in different levels.
 * Uses genetic algorithms to create the final image.
 */

/**
 * Created by Muriz on 13.08.16.
 */

public class FaceGen {

    public static void main(String[] args) throws IOException {
        Fitness fit = new Fitness();
        ImageUtils utils = new ImageUtils();
        BufferedImage negative = utils.createNegativeImage();
        BufferedImage match = ImageIO.read(new File("single_mask.png"));
        fit.calculateImageSimilarity(negative, match);
    }
}