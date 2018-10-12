package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/25.
 */

public class whiteEdge {

    ///结构元素
    private static int sData[]={
            0xff808080,0xff808080,0xff808080,
            0xff808080,0xff808080,0xff808080,
            0xff808080,0xff808080,0xff808080
    };

    public Bitmap traverse(int[][] source){
        int width=source[0].length;
        int height=source.length;
        Bitmap colorImage2 = null;
        int[][] result=new int[height][width];
        colorImage2 = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                ///边缘不进行操作
                if(i>0&&j>0&&i<height-1&&j<width-1){
                    int max =0;

                    ///对结构元素进行遍历
//                    for(int k=0;k<sData.length;k++){
//                        int x=k/3;///商表示x偏移量
//                        int y=k%3;///余数表示y偏移量

                        if(source[i][j] > 0){
                            if(source[i-1][j-1]==0||source[i+1][j+1]==0||source[i-1][j+1]==0||source[i+1][j-1]==0)
                            {
                                colorImage2.setPixel(j+1, i, 0xffffffff);
                                colorImage2.setPixel(j-1, i, 0xffffffff);
                                colorImage2.setPixel(j, i, 0xffffffff);
                                colorImage2.setPixel(j, i+1, 0xffffffff);
                                colorImage2.setPixel(j, i-1, 0xffffffff);

                            }


                        }



                }else{
                    ///直接赋值
                    colorImage2.setPixel(j+1, i, source[i][j]);
                }

            }
        }
        return colorImage2;
    }

}
