package com.soundcloud.android.crop.example;

/**
 * Created by abudu on 2018/9/25.
 */

public class DeadCells {
    public static double cellsLevel(double[][] input1,double[][] input2)
    {
        double sum1 = 0.0;
        double sum2 = 0.0;
        if((input1.length!=input2.length)||(input1[0].length!=input2[0].length)){
            System.out.println("有猫饼");
            return 0;
        }
        else{
            double[][]c = new double[input1.length][input1[0].length];
            for(int i = 0;i<c.length;i++)
                for(int j = 0;j<c[0].length;j++)
                {
                    c[i][j] = input1[i][j]*(255 - input2[i][j]);
                    sum1 += c[i][j];
                    sum2 += input1[i][j];
                }
			System.out.println(sum1+"sum1");
			System.out.println(sum2+"sum2");
            return sum1/sum2;
        }
    }

    public static int[][] binarize(int[][] input,double threshold1,int threshold2)
    {

        int sizey = input.length;
        int sizex = input[0].length;
        double thd = 1.18*threshold1;
        int[][] binarized = new int[sizey][sizex];

        for(int i = 0; i < sizey; i++)
            for(int j = 0;j < sizex; j++)//二值化
            {
                if(input[i][j] > thd)
                {
                    binarized[i][j] = 255;
                }
//                else if(input[i][j] < threshold2)
//                {
//                    binarized[i][j] = 255;
//                }
                else
                    binarized[i][j] = 0;
            }
        return binarized;
    }


}
