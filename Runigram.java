// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {		
		// Tests the reading and printing of an image:
		System.out.println("Tests the reading and printing of an image:");	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);
		System.out.println();

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		System.out.println("Tests the horizontal flipping of an image:");	
		imageOut = flippedHorizontally(tinypic);
		System.out.println();
		print(imageOut);
		System.out.println();

		// Tests the vertical flipping of an image:
		System.out.println("Tests the vertical flipping of an image:");
		imageOut = flippedVertically(tinypic);
		System.out.println();
		print(imageOut);
		System.out.println();

		// Tests greyscailing of an image:
		System.out.println("Tests greyscailing of an image:");
		imageOut = grayScaled(tinypic);
		System.out.println();
		print(imageOut);
		System.out.println();

		// Tests scayling of an image:
		System.out.println("Tests scayling of an image:");
		imageOut = scaled(tinypic, 3, 5);
		System.out.println();
		print(imageOut);
		System.out.println();

		// Tests blending of colors:
		System.out.println("Tests blending of colors:");
		Color pixel1 = tinypic[1][1];
		Color pixel2 = tinypic[2][2];
		Color blended = blend(pixel1, pixel2, 0.5);
		print(pixel1);
		System.out.println();
		print(pixel2);
		System.out.println();
		System.out.println("-----------------");
		print(blended);

		// Tests morphing function:
		Color[][] im1 = read("ironman.ppm");
		Color[][] im2 = read("thor.ppm");
		morph(im1, im2, 50);
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
		public static Color[][] read(String fileName) {
		In in = new In(fileName);

		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();

		// Creates the image array
		Color[][] image = new Color[numRows][numCols];

		// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				System.out.print("(");
				System.out.printf("%3s,", image[i][j].getRed());   // Prints the red component of the pixel [i][j]
				System.out.printf("%3s,", image[i][j].getGreen()); // Prints the green component of the pixel [i][j]
        		System.out.printf("%3s",  image[i][j].getBlue());  // Prints the blue component of the pixel [i][j]
        		System.out.print(")  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		// Creates empty array of pixels using the given array size
		Color[][] flipped = new Color[image.length][image[0].length];

		// Writes the given array to a new one with flipped columns
		for (int i = 0; i < flipped.length; i++) {
			for (int j = 0; j < flipped[0].length; j++) {
				flipped[i][j] = image[i][image[0].length - (j+1)];
			}
		}		
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		// Creates empty array of pixels using the given array size
		Color[][] flipped = new Color[image.length][image[0].length];

		// Writes the given array to a new one with flipped rows
		for (int i = 0; i < flipped.length; i++) {
			for (int j = 0; j < flipped[0].length; j++) {
				flipped[i][j] = image[image.length - (i+1)][j];
			}
		}		
		return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int lum = (int)(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color grey = new Color(lum, lum, lum);
		return grey;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		// Creates empty array of pixels using the given array size
		Color[][] greyScaled = new Color[image.length][image[0].length];

		// Writes the given array to a new one with greyscaled colours
		for (int i = 0; i < greyScaled.length; i++) {
			for (int j = 0; j < greyScaled[0].length; j++) {
				greyScaled[i][j] = luminance(image[i][j]);
			}
		}		
		return greyScaled;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		// Creates empty array of pixels using the given array size
		Color[][] scaled = new Color[height][width];

		// Creates to ratio variables
		double iRatio = (double)image.length / (double)height;
		double jRatio = (double)image[0].length / (double)width;
		
		// Writes the given array to a new one using ratio variables
		for (int i = 0; i < scaled.length; i++) {
			for (int j = 0; j < scaled[0].length; j++) {
				int iTarget = (int)(i * iRatio);
				int jTarget = (int)(j * jRatio);

				scaled[i][j] = image[iTarget][jTarget];			
			}
		}
		return scaled;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int blendedRed = (int)((alpha * c1.getRed()) + ((1 - alpha) * c2.getRed()));
		int blendedGreen = (int)((alpha * c1.getGreen()) + ((1 - alpha) * c2.getGreen()));
		int blendedBlue = (int)((alpha * c1.getBlue()) + ((1 - alpha) * c2.getBlue()));
		return new Color(blendedRed, blendedGreen, blendedBlue);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		// Creates empty array of pixels using the given array size
		Color[][] blended = new Color[image1.length][image1[0].length];

		// Writes the given array to a new one using blend method
		for (int i = 0; i < blended.length; i++) {
			for (int j = 0; j < blended[0].length; j++) {
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		// Checks if two images are the same size.
		// If not - scales the target image to the size of the source
		if ((source.length != target.length) || (source[0].length != target[0].length)) {
			target = scaled(target, source[0].length, source.length);
		}

		setCanvas(source);
		Color[][] morph = new Color[source.length][source[0].length];
		double alpha;
		// Displays each iteration a stage in morphing image using the amount of steps
		for (int i = 0; i <= n; i++) {
			alpha = ((double)(n - i) / (double)n);
			morph = blend(source, target, alpha);
			display(morph);
			StdDraw.pause(1000/n); 
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

