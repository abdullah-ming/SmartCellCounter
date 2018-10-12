package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/9.
 */

public class Gray {

    public int[][] greyProcessing(Bitmap bi)throws Exception {
        int[] rgb = new int[3];
//        BufferedImage bi = null;
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = 0;
        int miny = 0;
        int alpha = 0xFF << 24;
        int Gray[][] = new int[height][width];

        //System.out.println("width="+width+",height="+height+".");
        //System.out.println("minx="+minx+",miny="+miny+".");
        for(int i = minx;i<height;i++){
            for(int j = miny;j<width;j++){

                int pixel = bi.getPixel(j,i);
                rgb[0] = (pixel & 0xff0000)>>16;
                rgb[1] = (pixel & 0xff00)>>8;
                rgb[2] = (pixel & 0xff);

                Gray[i][j] = (int) (0.299*rgb[0]+0.587*rgb[1]+0.114*rgb[2]);
//                Gray[i][j] = alpha | (Gray[i][j] << 16) | (Gray[i][j] << 8) | Gray[i][j];

            }
        }
        return Gray;
    }

//	public int[][] greyProcessing(int[][] image)throws Exception
//	{
//		int sizex = image.length;
//		int sizey = image[0].length;
//		BufferedImage bi = new BufferedImage(sizex,sizey,BufferedImage.TYPE_INT_RGB);
//
//		int[][] Gray = new int[sizex][sizey];
//		for(int i = 0; i < sizex; i++)
//			for(int j = 0; j < sizey; j++)
//			{
//
//			}
//
//	}

    //归一化
    public double[][] MinMaxNormalization(int Grey[][])
    {
        int sizey = Grey.length;
        int sizex = Grey[0].length;
        double Min,Max;
        Min = Max = Grey[0][0];
        double Norm[][] = new double [sizey][sizex];
        for(int i = 0;i < sizey; i++)
            for(int j = 0;j < sizex; j++)
            {
                if(Grey[i][j]>Max)
                    Max = Grey[i][j];
                if(Grey[i][j]<Min)
                    Min = Grey[i][j];
            }
        for(int i = 0;i < sizey; i++)
            for(int j = 0;j < sizex; j++)
            {
                Norm[i][j] = (double)(Grey[i][j] - Min)/(Max - Min);
            }

        return Norm;
    }

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

    public Bitmap ToGrayImage(int gray[][])
    {
        int rgb=0;
        int sizey = gray.length;
        int sizex = gray[0].length;
        Bitmap bi = Bitmap.createBitmap(sizex, sizey, Bitmap.Config.RGB_565);;
//        int Grey[][] = new int[sizex][sizey];
//
//        int[][] inverseImage = new int[sizex][sizey];

        for(int i = 0;i < sizey; i++)
            for(int j = 0;j < sizex; j++)
            {
                rgb = gray[i][j];
                int newPixel = colorToRGB(255, rgb, rgb, rgb);
                bi.setPixel(j,i,newPixel);
            }

        return bi;
    }

    public int[][] InverseImage(int gray[][])
    {
        int rgb=0;
        int sizey = gray.length;
        int sizex = gray[0].length;

//        BufferedImage bi = new BufferedImage(sizex,sizey,BufferedImage.TYPE_INT_RGB);

//        int[][] bi = new int[sizey][sizex];
        int Grey[][] = new int[sizey][sizex];

//        int[][] inverseImage = new int[sizex][sizey];

//        for(int i = 0;i < sizey; i++)
//			for(int j = 0;j < sizex; j++)
//			{
//				rgb = gray[i][j];
//				int newPixel = colorToRGB(255, 255-rgb, 255-rgb, 255-rgb);
//				bi.setRGB(j, i, newPixel);
//
//			}
        for(int i = 0;i < sizey; i++)
            for(int j = 0;j < sizex; j++)
            {
                Grey[i][j] = 255-gray[i][j];

            }

        return Grey;
    }

}

