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

    public static int getGray(int tr, int tg, int tb){
        int gray = (int) (0.299 * tr + 0.587 * tg + 0.114 * tb);
        return gray;
    }

    /**
     * A convenience method for getting ARGB pixels from an image. This tries to avoid the performance
     * penalty of BufferedImage.getRGB unmanaging the image.
     */
    public static int[] getRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
        int type = image.getType();
        if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
            return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
        return image.getRGB( x, y, width, height, pixels, 0, width );
    }

    /**
     * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
     * penalty of BufferedImage.setRGB unmanaging the image.
     */
    public static void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
        int type = image.getType();
        if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
            image.getRaster().setDataElements( x, y, width, height, pixels );
        else
            image.setRGB( x, y, width, height, pixels, 0, width );
    }
}
