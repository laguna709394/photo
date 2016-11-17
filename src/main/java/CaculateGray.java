import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by 0143932 on 2016/11/17.
 */
public class CaculateGray {

    public static int[] calculate(BufferedImage bi) {
        int sgray[] = new int[256];
        double tgray[] = new double[256];
        for(int i=0; i<256; i++) {
            sgray[i] = 0;
        }

        double sum = 0;

        int width = bi.getWidth();
        int height = bi.getHeight();

        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                int rgb = bi.getRGB(i, j);

                /*应为使用getRGB(i,j)获取的该点的颜色值是ARGB，
                而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，
                即bufImg.getRGB(i, j) & 0xFFFFFF。*/
                int r = (rgb & 0xff0000) >> 16;
                int g = (rgb & 0xff00) >> 8;
                int b = (rgb & 0xff);
                int gray = (int)(r * 0.3 + g * 0.59 + b * 0.11);    //计算灰度值
                sgray[gray] ++;
            }
        }

        double avg = 0;
        for(int i=0; i<256; i++) {
            if(sgray[i] != 0) {
                double p = sgray[i] * 1.0 / (width * height);   //每一灰度值出现的概率
//                sum += p * (Math.log(1/p) / Math.log(2));       //熵
                avg += p;
                tgray[i] = p;
            }
        }
        avg = avg/sgray.length;
        System.out.println("平均值："+avg);
        int[] result = new int[sgray.length];
        for(int i=0; i<256; i++) {
            if(tgray[i] > avg) {
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
//        String result = "该图片的灰度值的熵为：" + sum;
//        System.out.println(result);
        return result;
    }

    public static void comparePhoto(String photo1Path,String photo2Path,String photo1Name,String photo2Name) throws IOException {
        String iutputFolder1 = photo1Path + photo1Name;
        String outputFolder1 = photo1Path + "temp" + photo1Name;
        String iutputFolder2 = photo2Path + photo2Name;
        String outputFolder2 = photo2Path + "temp" + photo2Name;
        NarrowImage.writeHighQuality(NarrowImage.zoomImage(iutputFolder1, 16, 16), outputFolder1);
        NarrowImage.writeHighQuality(NarrowImage.zoomImage(iutputFolder2, 16, 16), outputFolder2);
        BufferedImage img1 = ColorToRGB.convertColor(outputFolder1);
        BufferedImage img2 = ColorToRGB.convertColor(outputFolder2);
        int[] result1 = CaculateGray.calculate(img1);
        int[] result2 = CaculateGray.calculate(img2);
        int result3 = 0;
        for(int n = 0; n < result1.length; n++){
            System.out.println("result1:"+result1[n]+",result2:"+result2[n]);
            if(result1[n] != result2[n]){
                result3++;
            }
        }
        System.out.println("result:"+result3);
    }

    public static void main(String[] args) throws IOException {
        CaculateGray.comparePhoto("C:\\Users\\0143932\\Downloads\\","C:\\Users\\0143932\\Downloads\\","kkkk.jpg","kk.jpg");
    }
}
