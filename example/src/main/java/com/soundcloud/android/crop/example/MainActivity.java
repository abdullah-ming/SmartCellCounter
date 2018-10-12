package com.soundcloud.android.crop.example;

import com.soundcloud.android.crop.Crop;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private ImageView resultView;
    private EditText editText;
    public Bitmap bitmap=null;
    private Button button1;
    private Button button2;
    Bitmap bi = null;

    public Bitmap CaptureImageGray1 = null;

    private static final String filename = "9.jpg";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        if(bitmap==null)
        {
            resultView = (ImageView) findViewById(R.id.result_image);
        }

        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap!=null)
                {

                    int [][] CaptureImageGray = null;
                    Gray greyPro = new Gray();
                    Canny canny = null;
                    imageDilate imDilate = null;
                    LiveCells liveCells = null;
                    imFill imf = new imFill();
                    bwAreaOpen bao = new bwAreaOpen();

                    try {
                        CaptureImageGray = greyPro.greyProcessing(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    CaptureImageGray1 = greyPro.ToGrayImage(CaptureImageGray);

                    int sizey = CaptureImageGray.length;
                    int sizex = CaptureImageGray[0].length;
                    int[][] invesetImage = new int[sizey][sizex];
                    if(CaptureImageGray!=null)
                    {
                        invesetImage = greyPro.InverseImage(CaptureImageGray);
//				CaptureImageGray1 = greyPro.ToGrayImage(invesetImage);
                    }
                    canny = new Canny(CaptureImageGray);

//		// -- set very low thresholds just to see what happens
                    int t0 = 0;
                    int t1 = 93;
                    canny.SetThresholds(t0, t1);
                    int[][] edges = canny.Apply();
//        // -- shift 0/1 values to 0/255

                    int[][] edge255 = new int[edges.length][edges[0].length];
                    for (int i = 0; i < edge255.length; ++i)
                    {
                        for (int j = 0; j < edge255[i].length; ++j) {

                            edge255[i][j] = edges[i][j] << 8;

                        }
                    }

                    imDilate = new imageDilate();
//                    int[][] imDi= imDilate.open(edge255, t1);
//
//                    int[][] bao1 = imf.imgFill(imDi);
//                    bao1 = bao.bwAreaImage(bao1);

//死细胞识别



//活细胞识别
                    int[][] invesetCellImage = greyPro.InverseImage(CaptureImageGray);
//                    for (int i = 0; i < edge255.length; ++i)
//                    {
//                        for (int j = 0; j < edge255[i].length; ++j) {
//                            System.out.println(invesetCellImage[i][j]);
//                        }
//                    }
                    double[][] iCM = greyPro.MinMaxNormalization(invesetCellImage);
                    double[][] cIG = greyPro.MinMaxNormalization(CaptureImageGray);


                    double level2 = DeadCells.cellsLevel(iCM, cIG);
                    int[][] DeadCellArray = LiveCells.binarize(invesetCellImage,82,20);
                    System.out.println(level2);

                    double level = LiveCells.cellsLevel(iCM, cIG);
                    System.out.println(level);
//                    int[][] banCellArray = LiveCells.binarize(CaptureImageGray,(int)(level*255),75);
                    int[][] banCellArray = LiveCells.binarize(CaptureImageGray,178,120);
//
                    int[][] bao1 = imDilate.open(DeadCellArray, t1);

                     bao1 = imf.imgFill(bao1);
                    bao1 = bao.bwAreaImage(bao1);


                    int[][] bao2 = imDilate.open(banCellArray, t1);
                    bao2 = imf.imgFill(bao2);
                    bao2 = bao.bwAreaImage(bao2);
                    for (int i = 0; i < edge255.length; ++i)
                    {
                        for (int j = 0; j < edge255[i].length; ++j) {
                            System.out.println(bao2[i][j]);
                        }
                    }
                    bwLabel arrayColorImage = new bwLabel(bao1,bao2);
                    Bitmap colorImage = arrayColorImage.getColorImage();

                    int totalCell = 0;
                    totalCell = arrayColorImage.gettotalCell();
                    String totalCellData = String.valueOf(totalCell);
                    int liveCell = 0;
                    liveCell = arrayColorImage.getLiveCell();
                    String liveCellData = String.valueOf(liveCell);
                    int deadCell = totalCell - liveCell;
                    String deadCellData = String.valueOf(deadCell);

                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("extra_bitmap",colorImage);
                    view.setSaveEnabled(false);
                    intent.putExtra("totalCellData",totalCellData);
                    intent.putExtra("liveCellData",liveCellData);
                    intent.putExtra("deadCellData",deadCellData);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please click the upper right corner to insert the picture.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);

        }
    }



    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri=Crop.getOutput(result);
            if(uri != null){
                bitmap = getBitmapFormUri(uri);

            }
            resultView.setImageBitmap(bitmap);

        }else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap getBitmapFormUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            return decodeUri(this,uri);
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeUri(Context context, Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只读取图片尺寸
        resolveUri(context, uri, options);

        //计算实际缩放比例
        //inSampleSize
        //这个值是一个int，当它小于1的时候，将会被当做1处理，如果大于1，那么就会按照比例（1
                // inSampleSize）缩小bitmap的宽和高、

        //降低分辨率，大于1时这个值将会被处置为2的倍数。

       // 例如，width=100，height=100，inSampleSize=2，那么就会将bitmap处理为，width=50，height=50，宽高降为1
                // 2，像素数降为1 / 4。

        options.inSampleSize = calculateInSampleSize(options, 400, 400);
//
//        int height = options.outHeight * 1200 /options.outWidth;
//
//        options.outWidth = 1200;
//
//        options.outHeight = height;

//        options.inSampleSize = 6;
        options.inJustDecodeBounds = false;//读取图片内容
        options.inPreferredConfig = Bitmap.Config.RGB_565; //根据情况进行修改

        Bitmap bitmap = null;
        try {
            bitmap = resolveUriForBitmap(context, uri, options);
            System.out.println(bitmap.getWidth());
            System.out.println(bitmap.getHeight());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private static void resolveUri(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return;
        }

        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUri", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUri", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUri", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUri", "Unable to close content: " + uri);
        }
    }
    private static Bitmap resolveUriForBitmap(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return null;
        }

        Bitmap bitmap = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUriForBitmap", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUriForBitmap", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);
        }

        return bitmap;
    }





}
