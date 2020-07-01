import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    static File path;
    static {
        path = new File("G:/images");
        path.mkdirs();
    }

    public static void writeSingleImageToFile(BufferedImage img , int iter){
        String pathname = "gol" + iter + ".gif";
        File file = new File(pathname);
        try {
            ImageIO.write(img, "gif", new File(path,pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
