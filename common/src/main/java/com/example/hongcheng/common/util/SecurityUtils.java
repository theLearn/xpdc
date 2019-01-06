package com.example.hongcheng.common.util;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hongcheng on 16/4/2.
 */
public final class SecurityUtils {

    private static String TAG = SecurityUtils.class.getName();

    private SecurityUtils(){

    }

    public static String toMD5(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for(byte b : result){
                int number = (int)(b & 0xff) ;
                String str = Integer.toHexString(number);
                if(str.length()==1){
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //can't reach
            return "";
        }
    }

    /**
     * Return the urlencoded string.
     *
     * @param input The input.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * Return the urlencoded string.
     *
     * @param input       The input.
     * @param charsetName The name of charset.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Return the string of decode urlencoded string.
     *
     * @param input The input.
     * @return the string of decode urlencoded string
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * Return the string of decode urlencoded string.
     *
     * @param input       The input.
     * @param charsetName The name of charset.
     * @return the string of decode urlencoded string
     */
    public static String urlDecode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLDecoder.decode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Base64操作
     * @param source
     * @return
     */
    public static String encodeBase64(String source)
    {

        String result = "";

        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }
        try
        {
            result = StringUtils.byte2String(Base64.encode(source.trim().getBytes("UTF-8"), Base64.DEFAULT));
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }
        return result;
    }

    // 解密Base64
    public static String decodeBase64(String source)
    {
        String result = "";
        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        try
        {
            result = StringUtils.byte2String(Base64.decode(source.getBytes("UTF-8"), Base64.NO_WRAP));
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }

        return result;
    }

    // 加密Base64
    public static String encodeBase64(byte[] source)
    {
        return StringUtils.byte2String(Base64.encode(source, Base64.NO_WRAP));
    }

    // 解密Base64
    public static String encodeBase64(String source, Charset encoding)
    {
        String result = "";
        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        result = new String(Base64.encode(source.trim().getBytes(encoding), Base64.NO_WRAP), encoding);

        return result;
    }

    // 解密Base64
    public static String decodeBase64(String source, Charset encoding)
    {
        String result = "";
        if (StringUtils.isEmpty(source))
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        return new String(Base64.decode(source.trim().getBytes(encoding), Base64.NO_WRAP), encoding);
    }

    /**
     *
     * <br>
     * AES256加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptApp(String data, String key)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);

            byte[] clearText = data.getBytes("UTF8");

            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(clearText);

            return StringUtils.byte2String(Base64.encode(encrypted, Base64.NO_WRAP));
        }
        catch (InvalidKeyException e)
        {
            LoggerUtils.error(TAG, "", e);
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (NoSuchPaddingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (InvalidAlgorithmParameterException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (IllegalBlockSizeException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (BadPaddingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (IllegalArgumentException e)
        {
            LoggerUtils.error(TAG, "Key may be lost.");
            return null;
        }

        return null;

    }

    /**
     *
     * <br>
     * AES256解密
     * @param data
     * @param key
     * @return
     */
    public static String decryptApp(String data, String key)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            byte[] encrypted = Base64.decode(data.getBytes("UTF-8"), Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(encrypted);
            LoggerUtils.debug(TAG, String.valueOf(original));
            return StringUtils.byte2String(original);
        }
        catch (InvalidKeyException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (NoSuchPaddingException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (InvalidAlgorithmParameterException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (IllegalBlockSizeException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (BadPaddingException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }
        catch (IllegalArgumentException e)
        {
            LoggerUtils.error(TAG, "Key may be lost.");
            return null;
        }

        return "";
    }

    /**
     * 对字符串加密,加密算法使用SHA-256,默认使用SHA-256<br>
     * @see [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static String sha256Encrypt(String strSrc, String encName)
    {
        MessageDigest md = null;

        String vEncName = encName;

        try
        {
            byte[] bt = strSrc.getBytes("UTF-8");
            md = MessageDigest.getInstance(vEncName);
            md.update(bt);
            return StringUtils.bytesToHexString(md.digest());
        }
        catch(UnsupportedEncodingException ue)
        {
            LoggerUtils.error(TAG,"encrypt UnsupportedEncodingException.");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.error(TAG,"There is a NoSuchAlgorithmException in method EncryptHandler.");
        }
        return "";
    }

    /**
     * Return html-encode string.
     *
     * @param input The input.
     * @return html-encode string
     */
    public static String htmlEncode(final CharSequence input) {
        if (input == null || input.length() == 0) return "";
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Return the string of decode html-encode string.
     *
     * @param input The input.
     * @return the string of decode html-encode string
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (input == null || input.length() == 0) return "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }

    /**
     * Return the binary encoded string padded with one space
     *
     * @param input
     * @return binary string
     */
    public static String binEncode(final String input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char i : input.toCharArray()) {
            stringBuilder.append(Integer.toBinaryString(i));
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    /**
     * Return UTF-8 String from binary
     *
     * @param input binary string
     * @return UTF-8 String
     */
    public static  String binDecode(final String input){
        String[] splitted = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String i : splitted){
            sb.append(((char) Integer.parseInt(i.replace(" ", ""), 2)));
        }
        return sb.toString();
    }
}
