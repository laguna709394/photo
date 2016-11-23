import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Arrays;

/**
 * Created by 0143932 on 2016/11/23.
 */
public class MedianFilter {
    /**
     * 中值滤波
     * @param srcPath 图片的存储位置
     * @param destPath 图像要保存的存储位置
     * @param format 图像要保存的存储位置
     */
    public static void medianFiltering(String srcPath, String destPath, String format) {
        BufferedImage img = Common.readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pix = new int[w*h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        int newpix[] = medianFiltering(pix, w, h);
        img.setRGB(0, 0, w, h, newpix, 0, w);
        Common.writeImg(img, format, destPath);
    }
    /**
     * 中值滤波
     * @param pix 像素矩阵数组
     * @param w 矩阵的宽
     * @param h 矩阵的高
     * @return 处理后的数组
     */
    public static int[] medianFiltering(int pix[], int w, int h) {
        int newpix[] = new int[w*h];
        int[] temp = new int[9];
        ColorModel cm = ColorModel.getRGBdefault();
        int r=0;
        for(int y=0; y<h; y++) {
            for(int x=0; x<w; x++) {
                if(x!=0 && x!=w-1 && y!=0 && y!=h-1) {
                    //g = median[(x-1,y-1) + f(x,y-1)+ f(x+1,y-1)
                    //  + f(x-1,y) + f(x,y) + f(x+1,y)
                    //  + f(x-1,y+1) + f(x,y+1) + f(x+1,y+1)]
                    temp[0] = cm.getRed(pix[x-1+(y-1)*w]);
                    temp[1] = cm.getRed(pix[x+(y-1)*w]);
                    temp[2] = cm.getRed(pix[x+1+(y-1)*w]);
                    temp[3] = cm.getRed(pix[x-1+(y)*w]);
                    temp[4] = cm.getRed(pix[x+(y)*w]);
                    temp[5] = cm.getRed(pix[x+1+(y)*w]);
                    temp[6] = cm.getRed(pix[x-1+(y+1)*w]);
                    temp[7] = cm.getRed(pix[x+(y+1)*w]);
                    temp[8] = cm.getRed(pix[x+1+(y+1)*w]);
                    Arrays.sort(temp);
                    r = temp[4];
                    newpix[y*w+x] = 255<<24 | r<<16 | r<<8 |r;
                } else {
                    newpix[y*w+x] = pix[y*w+x];
                }
            }
        }
        return newpix;
    }
}
