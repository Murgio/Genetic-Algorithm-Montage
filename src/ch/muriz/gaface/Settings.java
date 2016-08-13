package ch.muriz.gaface;

import java.awt.Color;

public class Settings {
    // Static variables
    // Bases which comprise DNA
    public static int[] INDIVIDUAL_BASE_TYPES = Utils.range(0, 100);
    // [x, y, scale, rotation, opacity]
    public static final int INDIVIDUAL_GENE_LENGTH = 5;
    // Best fitness found in a generation so far
    public static int BEST_FITNESS = 0;

    // File locations
    // Image the individuals are matched against
    public static final String MATCH_FILE = "match.png";
    // Image the individuals are based on
    public static final String INSTANCE_FILE = "instance.png";
    // Sections of image more important than others
    public static final String IMPORTANT_MASK_FILE = "mask.png";
    // Location to store status information
    public static final String STATUS_DIR = "/Users/Muriz/Desktop/face_test";
    // File which holds the last generation's DNA
    public static final String SOURCE_GENERATION_FILE = null;

    // Variables
    // Number new of populations generated
    public static final int GENERATIONS = 10000000;
    // Every so many generations, write/print a status update
    public static final int STATUS_INTERVAL = 10;
    // Maximum number of worker processes to use; if None, will use cpu_count()
    public static final int MAX_PROCESSES = 5;

    public static final int POPULATION_SIZE = 30; // Number of Individuals
    public static final float POPULATION_CROSSOVER_RATE = 0.9f; // [0, infinity)
    public static final float POPULATION_MUTATION_RATE = 0.1f; // [0, infinity)
    // (0, 1] : Fraction of population in tournament
    public static final float TOURNAMENT_FRACTION = 0.6f;

    public static final int INDIVIDUAL_GENES = 250; // Number of images
    public static final int INDIVIDUAL_MIN_OPACITY = 50; // [0, 255]
    public static final float INDIVIDUAL_MIN_SCALE = 0.1f; // (0, 1]
    public static final float INDIVIDUAL_MAX_SCALE = 1.0f; // (0, 1]

    // (R, G, B, A) - (255, 255, 255, 255) for white
    public static final Color BACKGROUND = new Color(255, 255, 255, 255);
    // How much more they add to fitness compared to other areas
    public static final int IMPORTANT_AREAS_SCALE = 3;
}

