package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abudu on 2018/9/10.
 */

public class bwLabel {
    private int image[][];
    private int liveImage[][];
//    Bitmap CaptureImageGray2;
    private Bitmap colorImage = null;
    private Bitmap colorImage2 = null;
    private int totalCell=0;
    private int liveCell=0;
    private int deadlCell=0;
    private String text = "1";
    private int a = 0;
    private int b = 0;
    private whiteEdge whiteedge = new whiteEdge();
    private Map<Integer,Integer> hashmap = new HashMap<>();
    bwAreaOpen AreaOpen =new bwAreaOpen();
    private static int sData[]={
            0,0,0,
            0,0xff63b1ff,0,
            0,0xff63b1ff,0xff63b1ff
    };

    public bwLabel(int[][] grayImageData,int[][] Live) {
        // TODO Auto-generated constructor stub
        colorImage = Bitmap.createBitmap(grayImageData[0].length,grayImageData.length, Bitmap.Config.RGB_565);

        image = new int[grayImageData.length][grayImageData[0].length];
        liveImage = new int[grayImageData.length][grayImageData[0].length];
        for (int i = 0; i < grayImageData.length; i++) {
            for (int j = 0; j < grayImageData[0].length; j++) {
                image[i][j] = grayImageData[i][j];
                liveImage[i][j] = Live[i][j];
                if(image[i][j] == 0)

                    colorImage.setPixel(j, i, 0xffbdbdbd);
                else
                    colorImage.setPixel(j,i,image[i][j]);
            }
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFFFFF00);
        paint.setTextSize(20);

        Canvas canvas = new Canvas(colorImage);
        canvas.drawBitmap(colorImage,0,0,paint);
        image[0][0] = image[0][1] = image[1][0] = image[1][1] = 0;
        image[grayImageData.length-1][grayImageData[0].length-1] = image[0][grayImageData[0].length-1] = image[grayImageData.length-1][0] = 0;
        liveImage[0][0] = liveImage[0][1] = liveImage[1][0] = liveImage[1][1] = 0;
        liveImage[grayImageData.length-1][grayImageData[0].length-1] = liveImage[0][grayImageData[0].length-1] = liveImage[grayImageData.length-1][0] = 0;
        for (int i = 1; i < grayImageData.length-1; i++) {
            for (int j = 1; j < grayImageData[0].length-1; j++) {
                if (image[i][j] == 255) {
                    totalCell++;
                    a = 0;
//                    canvas.drawText("+", j, i+5, paint2);
                    dealBwlabe(i,j,grayImageData.length-1,grayImageData[0].length-1);
                    System.out.println("a的值为"+a);
                    if(a>40 && a<350)
                        canvas.drawText(text, j-18, i+18, paint);

                    if(a>=350 && a<550)
                    {
                        totalCell++;
                        canvas.drawText("2", j-18, i+18, paint);
//                    System.out.println("a的值为"+a);
                    }
                    if(a>=550 && a<750)
                    {
                        totalCell = totalCell+2;
                        canvas.drawText("3", j-18, i+18, paint);
//                    System.out.println("a的值为"+a);
                    }

                }

            }
        }

        color();
        Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(18);
        paint2.setFakeBoldText(true);
        Canvas canvas2 = new Canvas(colorImage);
        canvas2.drawBitmap(colorImage,0,0,paint2);
        for (int i = 1; i < grayImageData.length-1; i++) {
            for (int j = 1; j < grayImageData[0].length-1; j++) {

                if (liveImage[i][j] == 255) {
                    b=0;

                    canvas2.drawText("+", j-1, i+12, paint2);
                    liveCell++;
                    dealLiveBwlabe(i,j,grayImageData.length-1,grayImageData[0].length-1);
                    System.out.println("b的值为"+b);
                }
            }
        }
    }

    private void dealBwlabe(int i, int j,int y1,int x1) {
        // TODO Auto-generated method stub
        //上
        if (image[i-1][j] == 255) {
            image[i-1][j] = totalCell;
            a++;
            if((i-1)>0)
                dealBwlabe(i-1, j, y1, x1);
        }
        //左
        if (image[i][j-1] == 255) {
            image[i][j-1] = totalCell;
            a++;
            if((j-1)>0)
                dealBwlabe(i, j-1, y1, x1);
        }
        //下
        if (image[i+1][j] == 255) {
            image[i+1][j] = totalCell;
            a++;
            if((i+1) < y1-1)
            dealBwlabe(i+1, j, y1, x1);
        }
        //右
        if (image[i][j+1] == 255) {
            image[i][j+1] = totalCell;
            a++;
            if((j+1) < x1-1)
            dealBwlabe(i, j+1, y1, x1);
        }

//八连通需要
        //上左
        if (image[i-1][j-1] == 255) {
            image[i-1][j-1] = totalCell;
            a++;
            if((j-1)>0&&(i-1)>0)
            dealBwlabe(i-1, j-1, y1, x1);
        }
        //上右
        if (image[i-1][j+1] == 255) {
            image[i-1][j+1] = totalCell;
            a++;
            if((i-1) > 0 && (j+1) < x1-1)
            dealBwlabe(i-1, j+1, y1, x1);
        }
        //下左
        if (image[i+1][j-1] == 255) {
            image[i+1][j-1] = totalCell;
            a++;
            if((i+1) < y1-1 && (j-1) > 0)
            dealBwlabe(i+1, j-1, y1, x1);
        }
        //下右
        if (image[i+1][j+1] == 255) {
            image[i+1][j+1] = totalCell;
            a++;
            if((i+1) < y1-1 && (j+1) < x1-1)
            dealBwlabe(i+1, j+1, y1, x1);
        }

    }

    private void dealLiveBwlabe(int i, int j,int y1,int x1) {
        // TODO Auto-generated method stub
        //上
        if (liveImage[i-1][j] == 255) {
            liveImage[i-1][j] = liveCell;
            b++;
            if((i-1) > 0)
                dealLiveBwlabe(i-1, j,y1,x1);
        }
        //左
        if (liveImage[i][j-1] == 255) {
            liveImage[i][j-1] = liveCell;
            b++;
            if((j-1) > 0)
                dealLiveBwlabe(i, j-1,y1,x1);
        }
        //下
        if (liveImage[i+1][j] == 255) {
            liveImage[i+1][j] = liveCell;
            b++;
            if((i+1) < y1-1)
                dealLiveBwlabe(i+1, j,y1,x1);
        }
        //右
        if (liveImage[i][j+1] == 255) {
            liveImage[i][j+1] = liveCell;
            b++;
            if((j+1) < x1-1)
                dealLiveBwlabe(i, j+1,y1,x1);
        }

//八连通需要
        //上左
        if (liveImage[i-1][j-1] == 255) {
            liveImage[i-1][j-1] = liveCell;
            b++;
            if((i-1) > 0 && (j-1) >0)
                dealLiveBwlabe(i-1, j-1,y1,x1);
        }
        //上右
        if (liveImage[i-1][j+1] == 255) {
            liveImage[i-1][j+1] = liveCell;
            b++;
            if((i-1) > 0 && (j+1) < x1-1)
                dealLiveBwlabe(i-1, j+1,y1,x1);
        }
        //下左
        if (liveImage[i+1][j-1] == 255) {
            liveImage[i+1][j-1] = liveCell;
            b++;
            if((i+1) < y1-1 && (j-1) > 0)
                dealLiveBwlabe(i+1, j-1,y1,x1);
        }
        //下右
        if (liveImage[i+1][j+1] == 255) {
            liveImage[i+1][j+1] = liveCell;
            b++;
            if((i+1) < y1-1 && (j+1) < x1-1)
                dealLiveBwlabe(i+1, j+1,y1,x1);
        }

    }

    private void color(){

//        int color[];
//        color = new int[counter+1];

//        for (int i = 0; i < color.length; i++)
//            color[i] = (int) (0xff000000+Math.random()*0xffffff);
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[0].length; j++)
            {
                System.out.println(image[i][j]);
                if(image[i][j] > 0 && image[i][j]!=0xFFFFFF00)
                    colorImage.setPixel(j, i, 0xff00ffff);

            }
        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[0].length; j++)
            {
                if(image[i][j] > 0){
                    if(image[i-1][j-1]==0||image[i+1][j+1]==0||image[i-1][j+1]==0||image[i+1][j-1]==0)
                    {
                        colorImage.setPixel(j+1, i, 0xffffffff);
                        colorImage.setPixel(j-1, i, 0xffffffff);
                        colorImage.setPixel(j, i, 0xffffffff);
                        colorImage.setPixel(j, i+1, 0xffffffff);
                        colorImage.setPixel(j, i-1, 0xffffffff);

                    }


                }


            }
    }

    public Bitmap getColorImage()
    {
        return colorImage;
    }

    public int gettotalCell()
    {
        return totalCell;
    }

    public int getLiveCell(){return liveCell;}
//    public Bitmap getCaptureImageGray2()
//    {
//        return CaptureImageGray2;
//    }
}

