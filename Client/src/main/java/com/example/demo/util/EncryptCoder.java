package com.example.demo.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

;

/**
 * 编码工具类
 * 1.AES 加密，解密
 * 2.MD5 加密
 * 3.SHA 加密
 * 4.BASE64 加密 解密
 * @author
 * @version 0.0.7.20140601
 */
public class EncryptCoder {
    /**
     * 加密的KEY
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String default_charset = "UTF-8";
    private static final String sKey = "huaxunxinkeDianZ";
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA-1";
    private static final String KEY_SHA224 = "SHA-224";
    private static final String KEY_SHA256 = "SHA-256";
    private static final String KEY_SHA384 = "SHA-384";
    private static final String KEY_SHA512 = "SHA-512";
    /*
    01 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
    02 AES/CBC/NoPadding             16                          不支持
    03 AES/CBC/PKCS5Padding          32                          16
    04 AES/CBC/ISO10126Padding       32                          16
    05 AES/CFB/NoPadding             16                          原始数据长度
    06 AES/CFB/PKCS5Padding          32                          16
    07 AES/CFB/ISO10126Padding       32                          16
    08 AES/ECB/NoPadding             16                          不支持
    09 AES/ECB/PKCS5Padding          32                          16
    10 AES/ECB/ISO10126Padding       32                          16
    11 AES/OFB/NoPadding             16                          原始数据长度
    12 AES/OFB/PKCS5Padding          32                          16
    13 AES/OFB/ISO10126Padding       32                          16
    14 AES/PCBC/NoPadding            16                          不支持
    15 AES/PCBC/PKCS5Padding         32                          16
    16 AES/PCBC/ISO10126Padding      32                          16
    CryptoJS supports the following padding schemes:

        Pkcs7 (the default)
        Iso97971
        AnsiX923
        Iso10126
        ZeroPadding
        NoPadding
    */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法

    /*
   * 加密
   * 1.构造密钥生成器
   * 2.根据ecnodeRules规则初始化密钥生成器
   * 3.产生密钥
   * 4.创建和初始化密码器
   * 5.内容加密
   * 6.返回字符串
   */
    public static String AESEncode(String sSrc) throws Exception{
        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }

        byte[] raw = sKey.getBytes(default_charset);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(raw);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE,skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(default_charset));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDncode(String sSrc) throws Exception{
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(default_charset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(raw);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,ivParameterSpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,default_charset);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * MD5加密
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String MD5Encrypt(String sSrc) throws Exception{
        //确定计算方法
        String encodeStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = sSrc.getBytes(default_charset);
            md.update(inputData);
            encodeStr = bytes2Hex(md.digest());
        } catch (Exception e) {e.printStackTrace();}
        return encodeStr;
    }

    /**
     * SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，
     被广泛地应用于电子商务等信息安全领域。虽然，SHA与MD5通过碰撞法都被破解了，
     但是SHA仍然是公认的安全加密算法，较之MD5更为安全
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String SHAEncrypt(String sSrc) throws Exception {
        String encodeStr = null;
        byte[] inputData = sSrc.getBytes(default_charset);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            encodeStr = bytes2Hex(messageDigest.digest());
        } catch (Exception e) {e.printStackTrace();}
        return encodeStr;
    }

    /**
     * BASE64解密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String sSrc) throws Exception {
        byte[] b = null;
        String result = null;
        if (sSrc != null) {
            Base64 decoder = new Base64();
            try {
                b = decoder.decode(sSrc);
                result = new String(b, default_charset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * BASE64加密
     *
     * @param sSrc 加密字符
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String sSrc) throws Exception {
        byte[] b = null;
        String s = null;
        try {
            b = sSrc.getBytes(default_charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new String(new Base64().encode(b));
        }
        return s;
    }
    /**
     * 将byte转为16进制
     * @param bts
     * @return
     */
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 16位加密 订单号生成
     * @param plainText
     * @return
     */
    public static String MD5OrderNo(String plainText) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //result = buf.toString();  //md5 32bit
            // result = buf.toString().substring(8, 24))); //md5 16bit
             result = buf.toString().substring(8, 24);
            // System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
            // System.out.println("md5 32bit: " + buf.toString() );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */

        // 需要加密的字串
        String cSrc = "电子车牌ss";
        System.out.println("订单号生成："+MD5OrderNo(cSrc).toUpperCase()+"，长度："+MD5OrderNo(cSrc).length());
        // 加密
        System.out.println("AES加密的字符串："+AESEncode(cSrc));
        // 解密
        System.out.println("解密后的字串是：" + AESDncode(AESEncode(cSrc)));
        System.out.println("MD5加密:"+MD5Encrypt(cSrc));
        System.out.println("SHAEncrypt加密:"+SHAEncrypt(cSrc));
        System.out.println("encryptBASE64加密:"+encryptBASE64(cSrc));
        System.out.println("decryptBASE64解密:"+decryptBASE64(encryptBASE64(cSrc)));
    }
}
