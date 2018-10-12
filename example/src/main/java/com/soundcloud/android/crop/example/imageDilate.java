package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/9.
 */

public class imageDilate {

    ///结构元素
    private static int sData[]={
            255,255,255,
            255,255,255,
            255,255,255
    };

    public int[][] open(int [][]source,double threshold){

        int width=source[0].length;
        int height=source.length;

        int[][] result=new int[height][width];

        ///后膨胀运算
        result=dilate(source, threshold);
			/*for(int j=0;j<height;j++){
				for(int i=0;i<width;i++){
					System.out.print(result[j][i]+",");
				}
				System.out.println();
			}

		*/

        return result;
    }

    private static int[][] dilate(int[][] source,double threshold){
        int width=source[0].length;
        int height=source.length;

        int[][] result=new int[height][width];

        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                ///边缘不进行操作
                if(i>0&&j>0&&i<height-1&&j<width-1){
                    int max =0;

                    ///对结构元素进行遍历
                    for(int k=0;k<sData.length;k++){
                        int x=k/3;///商表示x偏移量
                        int y=k%3;///余数表示y偏移量

                        if(sData[k]!=0){
                            ///当结构元素中不为0时,取出图像中对应各项的最大值赋给图像当前位置作为灰度值
                            if(source[i-1+x][j-1+y]>max){
                                max=source[i-1+x][j-1+y];
                            }
                        }
                    }

                    ////此处可以设置阈值，当max小于阈值的时候就赋为0
                    if(max<threshold){
                        result[i][j]=0;
                    }else{
                        result[i][j]=max;
                    }
                    //	result[i][j]=max;

                }else{
                    ///直接赋值
                    result[i][j]=source[i][j];
                }

            }
        }

        return result;
    }

//		public BufferedImage arrayToGreyImage(int[][] sourceArray){
//			int width=sourceArray[0].length;
//			int height=sourceArray.length;
//			BufferedImage targetImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//			for(int j=0;j<height;j++){
//				for(int i=0;i<width;i++){
//					int greyRGB=sourceArray[j][i];
//					int rgb=(greyRGB<<16)|(greyRGB<<8)|greyRGB;
//					System.out.println(rgb);
//					targetImage.setRGB(i, j, rgb);
//				}
//			}
//
//			return targetImage;
//		}

    public int[][] arrayListGreyImage(Bitmap sourceArray){
        int width=sourceArray.getWidth();
        int height=sourceArray.getHeight();
        int[][] targetImage=new int[height][width];

        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                targetImage[j][i] = sourceArray.getPixel(i,j);

            }
        }

        return targetImage;
    }

}

