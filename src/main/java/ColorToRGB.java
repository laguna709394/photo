import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 0143932 on 2016/11/17.
 */
public class ColorToRGB {
    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }

    public static BufferedImage convertColor(String path) throws IOException {
        BufferedImage bufferedImage
                = ImageIO.read(new File(path));
        BufferedImage grayImage =
                new BufferedImage(bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        bufferedImage.getType());


        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                final int color = bufferedImage.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);;
//                System.out.println(i + " : " + j + " " + gray);
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayImage.setRGB(i, j, newPixel);
            }
        }
//        File newFile = new File(path);
//        ImageIO.write(grayImage, "jpg", newFile);
        return grayImage;
    }

    public static void main(String[] args) throws IOException {

//        CaculateGray.calculate(grayImage);
    }
}
