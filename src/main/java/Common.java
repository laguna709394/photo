import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 0143932 on 2016/11/23.
 */
public class Common {
    public static BufferedImage readImg(String path){
        File srcfile = null;
        BufferedImage bi = null;
        try {
            srcfile = new File(path);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }
            bi = ImageIO.read(srcfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bi;
    }

    public static void writeImg(BufferedImage image, String format, String path){
        File skinImageOut = null;
        try {
            skinImageOut = new File(path);
            ImageIO.write(image, format, skinImageOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
