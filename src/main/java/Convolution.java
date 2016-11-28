import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 0143932 on 2016/11/22.
 */
public class Convolution {

    private int[][] kernel;

    public Convolution(int[][] kernel) {
        this.kernel = kernel;
    }

    public static void convolution(BufferedImage bi, int[][] kernel, String destImage){

    }

    public static void filte(BufferedImage bi, int[][] kernel, String destImage){
        //滤波器大小
        int kx = kernel.length;
        int ky = kernel[0].length;
        //图像大小
        int height = bi.getHeight();
        int width = bi.getWidth();

        int imageData[] = new int[height*width];

        for(int i = 0; i < height; i++){
            int fi = i - 1;
            //边界判断
            if(fi < 0){
                fi = 0;
            }
            for(int j = 0; j < width; j++){
                int fj = j - 1;
                //边界判断
                if(fj < 0){
                    fj = 0;
                }

            }
        }

//        BufferedImage img = new BufferedImage(x, y,BufferedImage.TYPE_INT_RGB);
//        img.setRGB(0, 0, x, y, imageData, 0, x);
//        img.flush();
        File skinImageOut = new File(destImage);
//        try {
//            ImageIO.write(img, "jpg", skinImageOut);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void printPixel(int[][] sPixel){
        int x = sPixel.length;
        int y = sPixel[0].length;
        StringBuffer sb = new StringBuffer();
        for(int xx = 0; xx < x; xx++){
            sb.append("\n");
            for(int yy = 0; yy < y; yy++){
                sb.append(sPixel[xx][yy]);
                sb.append(",");
            }
        }
        System.out.println(sb.toString());
    }

    //计算卷积核
    public static int generalNewPixel(int[][] sPixel, int[][] kernel){
        int x = sPixel.length;
        int y = sPixel[0].length;
        int newPixelKernel = 0;
        int r = 0,g = 0, b = 0;
        for(int xx = 0; xx < x; xx++){
            for(int yy = 0; yy < y; yy++){
                int oldPixel = sPixel[xx][yy];
                int kPixel = kernel[xx][yy];
                int oldR = kPixel * (oldPixel & 0xff0000) >> 16;
                int oldG = kPixel * (oldPixel & 0xff00) >> 8;
                int oldB = kPixel * (oldPixel & 0xff);
                r += oldR;
                g += oldG;
                b += oldB;
            }
        }
        newPixelKernel = (255 << 24) | (r << 16) | (g <<8 )| b;
//        if(newPixelKernel > 255){
//            newPixelKernel = 255;
//        }
//        if(newPixelKernel < 0){
//            newPixelKernel = Math.abs(newPixelKernel);
//        }
        return newPixelKernel;
    }

    public static void main(String[] str) throws IOException {
        String imageName = "e:\\test\\wo1.jpg";
        String destImage1 = "e:\\test\\xxx1.png";
//        String destImage2 = "C:\\Users\\0143932\\Downloads\\xxx2.png";
//        String destImage3 = "C:\\Users\\0143932\\Downloads\\xxx3.png";
//        AvrFilter.avrFiltering(imageName, destImage1, "jpg");
//        MedianFilter.medianFiltering(imageName, destImage2, "jpg");
//        SnnFilter.snnFiltering(imageName, destImage3, "jpg");
        int[][] kernel = new int[5][5];
        kernel[0] = new int[]{-1,-1,-1};
        kernel[1] = new int[]{-1,4,-1};
        kernel[2] = new int[]{-1,-1,-1};
//        kernel[3] = new int[]{0,0,0,-2,0};
//        kernel[4] = new int[]{0,0,0,0,-1};
        BufferedImage im = Common.readImg(imageName);
//        Convolution.convolution(im,kernel,destImage1);
        Common.setRGB(im,0, 0, im.getWidth(), im.getHeight(), Gaussian.gaussian(im,3) );
        //TODO 差归一化
        Common.writeImg(im,"jpg",destImage1);
    }
}
