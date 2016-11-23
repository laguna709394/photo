/**
 * Created by 0143932 on 2016/11/23.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Convolution2 {

    public static void main(String[] args) {
        Convolution2 test = new Convolution2();
        try {
            test.filter("C:\\Users\\0143932\\Downloads\\leaf.jpg", "C:\\Users\\0143932\\Downloads\\xxx2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(String imageName, String desImageName)
            throws IOException {
        File imageFile = new File(imageName);
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int newImageData[] = new int[width * height];
        int imageData[] = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

        int rData[] = new int[width * height];
        int gData[] = new int[width * height];
        int bData[] = new int[width * height];

        for (int i = 0; i < imageData.length; i++) {
            rData[i] = (imageData[i] & 0xff0000) >> 16;
            gData[i] = (imageData[i] & 0xff00) >> 8;
            bData[i] = (imageData[i] & 0xff);
        }

        // 进行3*3矩阵的滤波 ，每个数字是1/9
        for (int v = 1; v <= height - 2; v++) {
            for (int u = 1; u <= width - 2; u++) {
                int sum1 = 0;
                int sum2 = 0;
                int sum3 = 0;
                int pr = 0;
                int pg = 0;
                int pb = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        pr = rData[(v + j) * width + (u + i)];
                        pg = gData[(v + j) * width + (u + i)];
                        pb = bData[(v + j) * width + (u + i)];
                        sum1 = sum1 + pr;
                        sum2 = sum2 + pg;
                        sum3 = sum3 + pb;
                    }
                }
                int q1 = (int) (sum1 / 9.0);
                int q2 = (int) (sum2 / 9.0);
                int q3 = (int) (sum3 / 9.0);

                newImageData[v * width + u] = (255 << 24) | (q1 << 16) | (q2 << 8) | q3;//新图

                //newImageData[v * width + u] = imageData[v * width + u];//原图

                System.out.println((255 << 24) | (q1 << 16) | (q2 << 8) | q3);
            }
        }


        writeImage(desImageName, newImageData, width, height);
    }

    public void writeImage(String desImageName, int[] imageData, int width, int height) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(desImageName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, imageData, 0, width);
        img.flush();
        try {
            ImageIO.write(img, "jpg", fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
