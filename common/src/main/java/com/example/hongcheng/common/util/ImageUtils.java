package com.example.hongcheng.common.util;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;

/**
 * Created by hongcheng on 16/4/2.
 */
public final class ImageUtils {

    private ImageUtils() {
        throw new AssertionError();
    }

    /**
     * convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth  sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap scalePicture(Context context, String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeFile(filePath, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        // 2.得到设备的分辨率;
        // 得到系统提供的窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 得到系统默认的分辨率
        Display defaultDisplay = wm.getDefaultDisplay();
        // 得到设备的分辨率
        int screenHeight = defaultDisplay.getHeight();
        int screenWidth = defaultDisplay.getWidth();

        // 3.通过比较得到合适的比例值;
        // 屏幕的 宽320 高 480 ,图片的宽3000 ,高是2262  3000/320=9  2262/480=5,,使用大的比例值
        int scale = 1;
        int sx = srcWidth / screenWidth;
        int sy = srcHeight / screenHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeFile(filePath, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, String filePath, int ivWidth, int ivHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeFile(filePath, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        int scale = 1;
        int sx = srcWidth / ivWidth;
        int sy = srcHeight / ivHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeFile(filePath, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        // 2.得到设备的分辨率;
        // 得到系统提供的窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 得到系统默认的分辨率
        Display defaultDisplay = wm.getDefaultDisplay();
        // 得到设备的分辨率
        int screenHeight = defaultDisplay.getHeight();
        int screenWidth = defaultDisplay.getWidth();

        // 3.通过比较得到合适的比例值;
        // 屏幕的 宽320 高 480 ,图片的宽3000 ,高是2262  3000/320=9  2262/480=5,,使用大的比例值
        int scale = 1;
        int sx = srcWidth / screenWidth;
        int sy = srcHeight / screenHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, int resId, int ivWidth, int ivHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        int scale = 1;
        int sx = srcWidth / ivWidth;
        int sy = srcHeight / ivHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        return bm;
    }

    /**
     * Bitmap to bytes.
     *
     * @param bitmap The bitmap.
     * @param format The format of bitmap.
     * @return bytes
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bytes to bitmap.
     *
     * @param bytes The bytes.
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable to bitmap.
     *
     * @param drawable The drawable.
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap to drawable.
     *
     * @param bitmap The bitmap.
     * @return drawable
     */
    public static Drawable bitmap2Drawable(Context context, final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Drawable to bytes.
     *
     * @param drawable The drawable.
     * @param format   The format of bitmap.
     * @return bytes
     */
    public static byte[] drawable2Bytes(final Drawable drawable,
                                        final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * Bytes to drawable.
     *
     * @param bytes The bytes.
     * @return drawable
     */
    public static Drawable bytes2Drawable(Context context, final byte[] bytes) {
        return bytes == null ? null : bitmap2Drawable(context, bytes2Bitmap(bytes));
    }

    /**
     * View to bitmap.
     *
     * @param view The view.
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

}
