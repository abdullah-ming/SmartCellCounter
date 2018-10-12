package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/9.
 */

public class imFill {

    public int[][] imgFill(int[][] imFill)
    {
        int[][] imageColor;
        int sizey = imFill.length;
        int sizex = imFill[0].length;
        imageColor = new int[sizey][sizex];

        for(int i = 0; i < sizey; i++)
            for(int j = 0; j < sizex; j++)
                imageColor[i][j] = imFill[i][j];

        imageColor = imfillImage(20,imageColor);

        return imageColor;
    }
    private int[][] imfillImage(int distance, int[][] imageColor) {
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
                            break;
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
                            break;
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
                            break;
                        }
                    }
                    else break;
                }
                //右
                for (int k1 = 0; k1 < distance; k1++) {
                    if((j+k1)<imageColor[1].length) {
                        if (imageColor[i][j + k1] > 128) {
                            counter++;
                            break;
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
                            break;
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
                            break;
                        }
                    }
                    else break;
                }
                //左下
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1) > 0 && (j+k1) < imageColor[1].length) {
                        if (imageColor[i - k1][j + k1] > 128) {
                            counter++;
                            break;
                        }
                    }
                    else break;
                }
                //右下
                for (int k1 = 0; k1 < distance; k1++) {
                    if((i-k1)>0&&(j-k1)>0) {
                        if (imageColor[i - k1][j - k1] > 128) {
                            counter++;
                            break;
                        }
                    }
                    else break;
                }
                if (counter == 8)
                    newImageColor[i][j] = 255;
            }//for (int j = 0; j < imageColor[1].length; j++)
        }//for (int i = 0; i < imageColor.length; i++)
        return newImageColor;
    }
}
