package com.soundcloud.android.crop.example;

import android.graphics.Bitmap;

/**
 * Created by abudu on 2018/9/10.
 */

public class Recognition {


    Bitmap colorimage;
    int counter = 0;
    int a = 0;
    int[][] image;

    private static int sData[]={
            0xffffff70,0xffffff70,0xffffff70,
            0xffffff70,0xffffff70,0xffffff70,
            0xffffff70,0xffffff70,0xffffff70
    };

    public Recognition(int[][] liveCellImage,Bitmap Image){



        int sizey = liveCellImage.length;
        int sizex = liveCellImage[0].length;
//    	colorimage = Image;
        image = new int[liveCellImage.length][liveCellImage[0].length];
        colorimage =Bitmap.createBitmap(sizex, sizey, Bitmap.Config.ARGB_8888);
        int[][] Image1 = new int[liveCellImage.length][liveCellImage[0].length];
        for (int i = 0; i < liveCellImage.length; i++) {
            for (int j = 0; j < liveCellImage[0].length; j++) {

                image[i][j] = 0;
                colorimage.setPixel(j, i, Image.getPixel(j, i));
            }
        }
        RegionColor(14,liveCellImage);

        image = dilate(image,0xffffff70);

        for(int i = 0; i < sizey; i++)
            for(int j = 0; j<sizex; j++)
            {
                if(image[i][j] == 0xffffff70 && colorimage.getPixel(j, i) != 0xff63b1ff)
                    colorimage.setPixel(j, i, image[i][j]);
            }
//    	for(int i = 0; i < sizey; i++)
//    		for(int j = 0;j <sizex; j++)
//    		{
//    			x[0][j] = 0;
//    			y[i][j] = 0;
//    		}

//        for (int i = 1; i < sizey-1; i++)
//        {
//            for (int j = 1; j < sizex-1; j++)
//            {
//            	if(image[i][j] == 0)
//            	{
//            		counter++;
//            		image[i][j] = 250;
//
//            			connectedRegion(i,j);
//            	}
//
//
//
//
//
//            }
//        }
//        for (int i = 1; i < sizey-1; i++)
//        	for (int j = 1; j < sizex-1; j++) {
//        		if((250 == image[i][j])&&(TotalCellImage[i][j] == 255))
//        		{
//        			a++;
//        			System.out.println(a);
//        			RegionColor(i,j,TotalCellImage);
//        		}
//        	}
//
//        color(TotalCellImage);
//
//    }
//    private void color(int[][] totalCellImage2) {
//		// TODO 自动生成的方法存根
//    	int sizey = totalCellImage2.length;
//    	int sizex = totalCellImage2[0].length;
//
//    	for (int i = 0; i < sizey; i++)
//            for (int j = 0; j < sizex; j++)
//            {
//            	if (totalCellImage2[i][j] == 128)
//            		colorImage.setRGB(j, i, 0xff63b1ff);
//            	else if(totalCellImage2[i][j] == 250)
//            		colorImage.setRGB(j, i, 0xffffff70);
//
//                else
//                	colorImage.setRGB(j, i, 0xff000000);
//            }

    }

    public Bitmap getColorImage()
    {
        return colorimage;
    }

    private void RegionColor(int distance, int[][] imageColor) {
        // TODO Auto-generated method stub
        int[][] newImageColor;
        newImageColor = imageColor;
        int counter;
        for (int i = distance; i < imageColor.length - distance; i++) {
            for (int j = distance; j < imageColor[1].length - distance; j++) {
                counter = 0;
                //上
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j] == 0)
                    {
                        counter++;
//                        break;
                    }
                }

                //左
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j-k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //下
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i+k1][j] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //右
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j+k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //左上
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j-k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //右上
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i+k1][j+k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //左下
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j+k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                //右下
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j-k1] == 0)
                    {
                        counter++;
//                        break;
                    }
                }
                if ((counter > 46)&&(counter < 100))  image[i][j] = 0xffffff70;
            }//for (int j = 0; j < imageColor[1].length; j++)
        }//for (int i = 0; i < imageColor.length; i++)
//        return newImageColor;
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
                            if(source[i-1+x][j-1+y]==0xffffff70){
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
}
