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
        int imageData[] = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0,bi.getWidth());
        //滤波器大小
        int kx = kernel.length;
        int ky = kernel[0].length;
        //图像大小
        int y = bi.getHeight();
        int x = bi.getWidth();

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
                int oldPixel = allPixel[n+xCenter][m+yCenter];
                int newPixel = generalNewPixel(sPixel, kernel);
                int oldR = (oldPixel & 0xff0000) >> 16;
                int oldG = (oldPixel & 0xff00) >> 8;
                int oldB = (oldPixel & 0xff);
                oldR += newPixel;
                oldG += newPixel;
                oldB += newPixel;
                newPixel = (255 << 24) | (oldR << 16) | (oldG <<8 )| oldB;
                bi.setRGB(n+xCenter, m+yCenter,newPixel);
            }
        }

//        printPixel(allPixel);
//        setNewPxels(bi, allPixel);
        File skinImageOut = new File(destImage);
        try {
            ImageIO.write(bi, "jpg", skinImageOut);
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
        int[][] newPixel = sPixel;
        int x = sPixel.length;
        int y = sPixel[0].length;
        int newPixelKernel = 0;
        for(int xx = 0; xx < x; xx++){
            for(int yy = 0; yy < y; yy++){
                newPixelKernel += sPixel[xx][yy] * kernel[xx][yy];
            }
        }

        return newPixelKernel;
    }

    public static void main(String[] str) throws IOException {
        String imageName = "C:\\Users\\0143932\\Downloads\\xxx.png";
        String destImage = "C:\\Users\\0143932\\Downloads\\xxx2.png";
        File srcfile = new File(imageName);
        if (!srcfile.exists()) {
            System.out.println("文件不存在");
        }
        int[][] kernel = new int[3][3];
        kernel[0] = new int[]{-1,-1,-1};
        kernel[1] = new int[]{-1,9,-1};
        kernel[2] = new int[]{-1,-1,-1};
        BufferedImage im = ImageIO.read(srcfile);
        Convolution.convolution(im,kernel,destImage);
    }
}
