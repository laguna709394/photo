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

    public static void convolution(BufferedImage bi, float[][] kernel, String destImage, boolean retainBoarder){
        printPixel(kernel);
        overTurn(kernel);
        printPixel(kernel);
        filte(bi,kernel,destImage,retainBoarder);
    }

    public static void filte(BufferedImage bi, float[][] kernel, String destImage, boolean retainBoarder){
        //滤波器大小
        int kx = kernel.length;
        int ky = kernel[0].length;
        int hn = kx/2;
        int wn = ky/2;
        //图像大小
        int height = bi.getHeight();
        int width = bi.getWidth();

        int imageData[] = new int[height*width];

        bi.getRGB(0,0,width,height,imageData,0,width);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                double weightSum = 0.0;
                double redSum = 0,blueSum = 0,greenSum = 0;
                //遍历中心店周围所有像素
                for(int m = -hn; m <= hn; m++){
                    int ri = i + m;
                    //边界判断
                    if(ri < 0){
                        //是否复制边缘
                        if(retainBoarder){
                            ri = i;
                        }else{
                            ri = 0;
                        }
                    }else if(ri >= height){
                        if(retainBoarder){
                            ri = height - 1;
                        }else{
                            ri = 0;
                        }
                    }
                    for(int n = -wn; n <= wn; n++){
                        int rj = j + n;
                        int ki = hn + m;
                        int kj = wn + n;

                        if(rj < 0){
                            if(retainBoarder){
                                rj = j;
                            }else{
                                rj = 0;
                            }
                        }else if(rj >= width){
                            if(retainBoarder){
                                rj = width - 1;
                            }else{
                                rj = 0;
                            }
                        }
//                        System.out.println("i:"+i+",j:"+j+",ri:"+ri+",rj:"+rj+"ki:"+ki+",kj:"+kj);
                        //获取图片和卷积核的像素
                        int pixel = 0;
                        pixel = imageData[ri * width + rj];
                        float kpixel = kernel[ki][kj];
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = pixel & 0xff;
                        redSum += r * kpixel;
                        greenSum += g * kpixel;
                        blueSum += b * kpixel;
                        weightSum += kpixel;
//                        pixelSum += pixel * kpixel;
//                        System.out.println("pixel:" + pixel + ",kpixel:"+kpixel+",sum:"+pixelSum);
                    }
                }
                int red = (int)(redSum / weightSum);
                int green = (int)(greenSum / weightSum);
                int blue = (int)(blueSum / weightSum);
                int pixel = (255 << 24) | (red << 16) | (green <<8 )| blue;
                imageData[i * width + j] = pixel;
            }
        }

        BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, imageData, 0, width);
        img.flush();
        File skinImageOut = new File(destImage);
        try {
            ImageIO.write(img, "jpg", skinImageOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printPixel(float[][] sPixel){
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

    /**
     * 卷积把模板旋转180度
     * @param kernel
     * @return
     */
    public static float[][] overTurn(float[][] kernel){
        int kx = kernel.length;
        int ky = kernel[0].length;
        int ckx = kx/2;
        int tky = ky;
        for(int i = 0; i <= ckx; i++){
            if(i == ckx){
                tky = tky/2;
            }
            for(int j = 0; j < tky; j++){
                int x = kx - i - 1;
                int y = ky - j - 1;
                float sPixel = kernel[i][j];
                float tPixel = kernel[x][y];
                kernel[x][y] = sPixel;
                kernel[i][j] = tPixel;
            }
        }
        return kernel;
    }

    public static void main(String[] str) throws IOException {
//        String imageName = "e:\\test\\wo1.jpg";
//        String destImage1 = "e:\\test\\xxx1.png";
        String imageName     = "D:\\test\\leaf.jpg";
        String destImage1 = "D:\\test\\leafc.jpg";
//        AvrFilter.avrFiltering(imageName, destImage1, "jpg");
//        MedianFilter.medianFiltering(imageName, destImage2, "jpg");
//        SnnFilter.snnFiltering(imageName, destImage3, "jpg");
//        int[][] kernel = new int[5][5];
//        kernel[0] = new int[]{1,2,3,4,5};
//        kernel[1] = new int[]{6,7,8,9,10};
//        kernel[2] = new int[]{11,12,13,14,15};
//        kernel[3] = new int[]{16,17,18,19,20};
//        kernel[4] = new int[]{21,22,23,24,25};
        BufferedImage im = Common.readImg(imageName);
//        im = ColorToRGB.convertColor(im);
//        Common.writeImg(im,"jpg","D:\\test\\leafa.jpg");
        Convolution.convolution(im, Gaussian.get2DKernalData(1,1), destImage1, false);
//        Common.setRGB(im,0, 0, im.getWidth(), im.getHeight(), Gaussian.gaussian(im,3) );
        //TODO 差归一化
//        Common.writeImg(im,"jpg",destImage1);
    }
}
