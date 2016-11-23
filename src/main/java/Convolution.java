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
        //滤波器大小
        int kx = kernel.length;
        int ky = kernel[0].length;
        //图像大小
        int y = bi.getHeight();
        int x = bi.getWidth();

        int imageData[] = new int[x*y];

        int xCenter = kx/2;
        int yCenter = ky/2;

        //图片所有像素转化为2D数组
        int[][] allPixel = new int[x][y];
        for(int i=0; i<x; i++) {
            for(int j=0; j<y; j++) {
                allPixel[i][j] = bi.getRGB(i, j);
            }
        }

        //获取每个像素点的卷积核
        for(int n = 0; n < x - kx; n++){
            for(int m = 0; m < y - ky; m++){
                //取出像素块
                int[][] sPixel = new int[kx][ky];
                for(int kn = 0; kn < kx; kn++){
                    for(int km = 0; km < ky; km++){
                        sPixel[kn][km] = allPixel[n+kn][m+km];
                    }
                }
                int newPixel = generalNewPixel(sPixel, kernel);
                imageData[n+xCenter+(m+yCenter)*x] = newPixel;
            }
        }

//        printPixel(allPixel);
//        setNewPxels(bi, allPixel);
//        bi.setRGB(n+xCenter, m+yCenter,newPixel);
        BufferedImage img = new BufferedImage(x, y,BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, x, y, imageData, 0, x);
        img.flush();
        File skinImageOut = new File(destImage);
        try {
            ImageIO.write(img, "jpg", skinImageOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                int kenerlValue = kernel[xx][yy];
                int oldR = (oldPixel & 0xff0000) >> 16;
                int oldG = (oldPixel & 0xff00) >> 8;
                int oldB = (oldPixel & 0xff);
                r += oldR * kenerlValue;
                g += oldG * kenerlValue;
                b += oldB * kenerlValue;
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
        String imageName = "C:\\Users\\0143932\\Downloads\\xxx.png";
        String destImage1 = "C:\\Users\\0143932\\Downloads\\xxx1.png";
        String destImage2 = "C:\\Users\\0143932\\Downloads\\xxx2.png";
        String destImage3 = "C:\\Users\\0143932\\Downloads\\xxx3.png";
        AvrFilter.avrFiltering(imageName, destImage1, "jpg");
        MedianFilter.medianFiltering(imageName, destImage2, "jpg");
        SnnFilter.snnFiltering(imageName, destImage3, "jpg");
//        int[][] kernel = new int[5][5];
//        kernel[0] = new int[]{-1,0,0,0,0};
//        kernel[1] = new int[]{0,-2,0,0,0};
//        kernel[2] = new int[]{0,0,6,0,0};
//        kernel[3] = new int[]{0,0,0,-2,0};
//        kernel[4] = new int[]{0,0,0,0,-1};
//        BufferedImage im = Common.readImg(imageName);
//        Convolution.convolution(im,kernel,destImage);
    }
}
