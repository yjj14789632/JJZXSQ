package com.bn.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2017/3/12.
 */

public class MyGetBitmap {
    public static Bitmap bitmap = null;
    public static String filePath;
    public static String picFilePath;
    public static File file;
    //检查sd卡内是否有照片
    public static boolean isEmpty(String picName) {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory()
                    + "/szyl";
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            picFilePath = Environment.getExternalStorageDirectory().toString()
                    + "/szyl" + "/" + picName;
            file = new File(picFilePath);
            if (file.exists()) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    //放到SD卡的方法，两个参数，一个流，一个图片名称
    public static void setInSDBitmap(byte[] bb, String picName) {
        //得到路径
        filePath = Environment.getExternalStorageDirectory()+ "/jjzxsq/";
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        input = new ByteArrayInputStream(bb);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (bb != null) {
            bb = null;
        }try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fos = null;
        file = new File(filePath + "/" + picName);
        try {
            fos = new FileOutputStream(file); // 读到SD卡中
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            bitmap.recycle();
            System.gc();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //得到sd卡的图片，直接得到图片
    public static Bitmap getSDBitmap(String picName) {
        //pic的路径
        picFilePath = Environment.getExternalStorageDirectory()+ "/szyl" + "/" + picName;
        BitmapFactory.Options options = new BitmapFactory.Options();
        /* 不进行图片抖动处理 */
        options.inDither = false;
        /* 设置让解码器以最佳方式解码 */
        options.inPreferredConfig = null;
         /* 图片长宽方向缩小倍数 */
        options.inSampleSize = 1;
        Bitmap bit = BitmapFactory.decodeFile(picFilePath, options);
        return bit;
    }
    //得到图片数组
    public static Bitmap getBitmapFromByteArray(byte[] array) {

        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
    //放到缩小的
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;

    }

//    public static byte[] BitmapToBytes(Bitmap bm)
//    {
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG,90,baos);
//        return baos.toByteArray();
//    }
}
