package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/9.
 */

public class bwAreaOpen {
    Bitmap grayImage;
    Bitmap bwAreaImage;

    public int[][] bwAreaImage(int[][] imFill)
    {
        int[][] imageColor;
        int sizey = imFill.length;
        int sizex = imFill[0].length;
        bwAreaImage = Bitmap.createBitmap(sizex, sizey, Bitmap.Config.RGB_565);
        imageColor = new int[sizey][sizex];

        for(int i = 0; i < sizey; i++)
            for(int j = 0; j<sizex; j++)
            {
                imageColor[i][j] = imFill[i][j];
            }
        imageColor = bwaImage(12,imageColor);

        return imageColor;
    }

    private int[][] bwaImage(int distance, int[][] imageColor) {
        // TODO Auto-generated method stub
        int[][] newImageColor;
        newImageColor = imageColor;
        int counter;
        for (int i = 0; i < imageColor.length; i++) {
            for (int j = 0; j < imageColor[1].length; j++) {
                counter = 0;

                //上
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1) > 0)
                    {
                        if (imageColor[i-k1][j] > 128)
                        {
                            counter++;
                        }
                    }
                    else break;
                }

                //左
                for (int k1 = 0; k1 < distance; k1++) {
                    if((j-k1) > 0)
                    {
                        if (imageColor[i][j-k1] > 128)
                        {
                            counter++;
                        }
                    }
                    else break;
                }
                //下
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i+k1)<imageColor.length)
                    {
                        if (imageColor[i+k1][j] > 128)
                        {
                            counter++;
                        }
                    }
                    else break;
                }
                //右
                for (int k1 = 0; k1 < distance; k1++) {
                    if((j+k1)<imageColor[1].length) {
                        if (imageColor[i][j + k1] > 128) {
                            counter++;
                        }
                    }
                    else break;
                }
                //左上
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1)>0&&(j-k1)>0)
                    {
                        if (imageColor[i-k1][j-k1] > 128)
                        {
                            counter++;
                        }
                    }
                    else break;
                }
                //右上
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i+k1)<imageColor.length && (j+k1)<imageColor[1].length)
                    {
                        if (imageColor[i+k1][j+k1] > 128)
                        {
                            counter++;
                        }
                    }
                    else break;
                }
                //左下
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1) > 0 && (j+k1) < imageColor[1].length) {
                        if (imageColor[i - k1][j + k1] > 128) {
                            counter++;
                        }
                    }
                    else break;
                }
                //右下
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1)>0&&(j-k1)>0) {
                        if (imageColor[i - k1][j - k1] > 128) {
                            counter++;
                        }
                    }
                    else break;
                }
                if (counter <= 25)  newImageColor[i][j] = 0;
//                System.out.println(counter);
            }//for (int j = 0; j < imageColor[1].length; j++)
        }//for (int i = 0; i < imageColor.length; i++)
        return newImageColor;
    }
}
//-838609