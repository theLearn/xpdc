/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.hongcheng.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.annotation.RawRes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载R文件里面的内容
 *
 * @author king
 * @version 2014年10月18日  下午11:46:29
 * @QQ:595163260
 */
public final class ResUtil {

    private static final int BUFFER_SIZE = 8192;

    // 文件路径名
    private static String pkgName;
    // R文件的对象
    private static Resources resources;

    // 初始化文件夹路径和R资源
    public static void init(Context context) {
        pkgName = context.getPackageName();
        resources = context.getResources();
    }

    public static Resources getResources() {
        return resources;
    }

    /**
     * layout文件夹下的xml文件id获取
     */
    public static int getLayoutID(String layoutName) {
        return resources.getIdentifier(layoutName, "layout", pkgName);
    }

    // 获取到控件的ID
    public static int getWidgetID(String widgetName) {
        return resources.getIdentifier(widgetName, "id", pkgName);
    }

    /**
     * anim文件夹下的xml文件id获取
     */
    public static int getAnimID(String animName) {
        return resources.getIdentifier(animName, "anim", pkgName);
    }

    /**
     * xml文件夹下id获取
     */
    public static int getXmlID(String xmlName) {
        return resources.getIdentifier(xmlName, "xml", pkgName);
    }

    // 获取xml文件
    public static XmlResourceParser getXml(String xmlName) {
        int xmlId = getXmlID(xmlName);
        return (XmlResourceParser) resources.getXml(xmlId);
    }

    /**
     * raw文件夹下id获取
     */
    public static int getRawID(String rawName) {
        return resources.getIdentifier(rawName, "raw", pkgName);
    }

    /**
     * drawable文件夹下文件的id
     */
    public static int getDrawableID(String drawName) {
        return resources.getIdentifier(drawName, "drawable", pkgName);
    }

    // 获取到Drawable文件
    public static Drawable getDrawable(String drawName) {
        int drawId = getDrawableID(drawName);
        return resources.getDrawable(drawId);
    }

    /**
     * value文件夹
     */
    // 获取到value文件夹下的attr.xml里的元素的id
    public static int getAttrID(String attrName) {
        return resources.getIdentifier(attrName, "attr", pkgName);
    }

    // 获取到dimen.xml文件里的元素的id
    public static int getDimenID(String dimenName) {
        return resources.getIdentifier(dimenName, "dimen", pkgName);
    }

    // 获取到color.xml文件里的元素的id
    public static int getColorID(String colorName) {
        return resources.getIdentifier(colorName, "color", pkgName);
    }

    // 获取到color.xml文件里的元素的id
    public static int getColor(String colorName) {
        return resources.getColor(getColorID(colorName));
    }

    // 获取到style.xml文件里的元素id
    public static int getStyleID(String styleName) {
        return resources.getIdentifier(styleName, "style", pkgName);
    }

    // 获取到String.xml文件里的元素id
    public static int getStringID(String strName) {
        return resources.getIdentifier(strName, "string", pkgName);
    }

    // 获取到String.xml文件里的元素
    public static String getString(String strName) {
        int strId = getStringID(strName);
        return resources.getString(strId);
    }

    // 获取color.xml文件里的integer-array元素
    public static int[] getInteger(String strName) {
        return resources.getIntArray(resources.getIdentifier(strName, "array",
                pkgName));
    }

    /**
     * Copy the file from assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param destFilePath   The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromAssets(Context context, final String assetsFilePath, final String destFilePath) {
        boolean res = true;
        try {
            String[] assets = context.getAssets().list(assetsFilePath);
            if (assets.length > 0) {
                for (String asset : assets) {
                    res &= copyFileFromAssets(context, assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else {
                res = writeFileFromIS(
                        destFilePath,
                        context.getAssets().open(assetsFilePath),
                        false
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @return the content of assets
     */
    public static String readAssets2String(Context context, final String assetsFilePath) {
        return readAssets2String(context, assetsFilePath, null);
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param charsetName    The name of charset.
     * @return the content of assets
     */
    public static String readAssets2String(Context context, final String assetsFilePath, final String charsetName) {
        InputStream is;
        try {
            is = context.getAssets().open(assetsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = is2Bytes(is);
        if (bytes == null) return null;
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath The path of file in assets.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(Context context, final String assetsPath) {
        return readAssets2List(context, assetsPath, null);
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath  The path of file in assets.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(Context context, final String assetsPath,
                                               final String charsetName) {
        try {
            return is2List(context.getResources().getAssets().open(assetsPath), charsetName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Copy the file from raw.
     *
     * @param resId        The resource id.
     * @param destFilePath The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromRaw(Context context, @RawRes final int resId, final String destFilePath) {
        return writeFileFromIS(
                destFilePath,
                context.getResources().openRawResource(resId),
                false
        );
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of resource in raw
     */
    public static String readRaw2String(Context context, @RawRes final int resId) {
        return readRaw2String(context, resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of resource in raw
     */
    public static String readRaw2String(Context context, @RawRes final int resId, final String charsetName) {
        InputStream is = context.getResources().openRawResource(resId);
        byte[] bytes = is2Bytes(is);
        if (bytes == null) return null;
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(Context context, @RawRes final int resId) {
        return readRaw2List(context, resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(Context context, @RawRes final int resId,
                                            final String charsetName) {
        return is2List(context.getResources().openRawResource(resId), charsetName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static boolean writeFileFromIS(final String filePath,
                                           final InputStream is,
                                           final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    private static boolean writeFileFromIS(final File file,
                                           final InputStream is,
                                           final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(data, 0, BUFFER_SIZE)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static byte[] is2Bytes(final InputStream is) {
        if (is == null) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> is2List(final InputStream is,
                                        final String charsetName) {
        BufferedReader reader = null;
        try {
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(is));
            } else {
                reader = new BufferedReader(new InputStreamReader(is, charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
