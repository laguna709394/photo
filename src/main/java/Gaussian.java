import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ksy on 2016/11/25.
 * ��˹ģ��
 */
public class Gaussian {

    /**
     * һά��˹����
     * @param n
     * @param sigma
     * @return
     */
    public static float[] get1DKernalData(int n, float sigma){
        float sigma2Square = 2 * sigma * sigma;
        float pi2 = (float)Math.PI * 2;
        //2pi ���ų���sigma
        float pi2SqrtSigma = (float)Math.sqrt(pi2) * sigma;
        //��������С
        int size = 2 * n + 1;
        float[] kernel = new float[size];
        for(int i = -n, ki = 0; i <= n; i++, ki++){
            float nSquare = i * i;
            kernel[ki] = (float)Math.exp(-nSquare / sigma2Square)/pi2SqrtSigma;
            System.out.println("kernel:"+kernel[ki]);
        }
        return kernel;
    }

    /**
     * ��ά��˹����
     * @param n
     * @param sigma
     * @return
     */
    public static float[][] get2DKernalData(int n, float sigma){
        float sigma2Square = 2 * sigma * sigma;
        float pi2 = (float)Math.PI * 2;
        //2pi ���ų���sigma
        float pi2SqrtSigma = (float)Math.sqrt(pi2) * sigma;
        //��������С
        int size = 2 * n + 1;
        //��ά������
        float[][] kernel = new float[size][size];
        for(int i = -n, ki = 0; i <= n; i++, ki++){
            for(int j = -n, kj = 0; j <= n; j++, kj++){
                float kSquare = i * i;
                float jSquare = j * j;
                kernel[ki][kj] = (float)Math.exp(-(kSquare + jSquare) / sigma2Square)/pi2SqrtSigma;
                System.out.println("kernel:"+kernel[ki][kj]);
            }
        }
        return kernel;
    }

    //��˹���
    public static int[] gaussian(BufferedImage bi, int n){
        float[][] kernel = get2DKernalData(n,1);
        int height = bi.getHeight();
        int width = bi.getWidth();
        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        Common.getRGB(bi, 0, 0, width, height, inPixels);
        int index = 0;
        // ��˹ģ�� -�Ҷ�ͼ��
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                double weightSum = 0.0;
                double redSum = 0,blueSum = 0,greenSum = 0;
                for(int subRow=-n; subRow<=n; subRow++)
                {
                    int nrow = row + subRow;
                    if(nrow >= height || nrow < 0)
                    {
                        nrow = 0;
                    }
                    for(int subCol=-n; subCol<=n; subCol++)
                    {
                        int ncol = col + subCol;
                        if(ncol >= width || ncol <=0)
                        {
                            ncol = 0;
                        }
                        int index2 = nrow * width + ncol;
                        int tr1 = (inPixels[index2] >> 16) & 0xff;
                        int tr2 = (inPixels[index2] >> 8) & 0xff;
                        int tr3 = inPixels[index2] & 0xff;
                        redSum += tr1*kernel[subRow+n][subCol+n];
                        greenSum += tr2*kernel[subRow+n][subCol+n];
                        blueSum += tr3*kernel[subRow+n][subCol+n];
                        weightSum += kernel[subRow+n][subCol+n];
                    }
                }
                int red = (int)(redSum / weightSum);
                int green = (int)(greenSum / weightSum);
                int blue = (int)(blueSum / weightSum);
                int pixel = (255 << 24) | (red << 16) | (green <<8 )| blue;
                outPixels[index] = pixel;
            }
        }
        
        return outPixels;
    }
}
