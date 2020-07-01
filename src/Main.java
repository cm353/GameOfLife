import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * Uses GifSequenceWriter
 * Uses GameOfLife
 *
 */

public class Main {

	static File path;
	static {
		path = new File("G:/images");
		path.mkdirs();
	}

	public static void main(String[] args) throws Exception {

		int column = 1024;
		int rows = 768;
		int numberOfIterations = 100;
		
		GameOfLife.execute(column, rows, numberOfIterations);

		GifSequenceWriter writer=null;
		ImageOutputStream output=null;

			String pathname = "gol" + 0 + ".gif";

			// grab the output image type from the first image in the sequence
			BufferedImage firstImage = ImageIO.read(new File(path,pathname));

			// create a new BufferedOutputStream with the last argument
			 output =
					new FileImageOutputStream(new File(path, "Test.gif"));
			// create a gif sequence with the type of the first image, 1 second
			// between frames, which loops continuously
			 writer =
					new GifSequenceWriter(output, firstImage.getType(), 1, false);

			// write out the first image to our sequence...
			writer.writeToSequence(firstImage);
			for (int i = 1; i <= numberOfIterations; i++) {
				pathname = "gol" + i + ".gif";
				BufferedImage nextImage = ImageIO.read(new File(path,pathname));
				writer.writeToSequence(nextImage);
				System.out.println("Gif Sequence item No.:" + i);
			}
		writer.close();
		output.close();
	}
}
