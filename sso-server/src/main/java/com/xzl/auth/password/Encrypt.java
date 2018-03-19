package com.xzl.auth.password;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.xzl.common.exception.BusinessException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ChenJunhong
 * @date 2018/03/19
 * Description:
 * 云梯平台的加密方法
 */
@SuppressWarnings("restriction")
public class Encrypt {

    //加密算法
    private SecretKey deskey;
    private String Algorithm = "Blowfish";
    private Cipher cipher;

    public Encrypt(){
        init();
    }
    
    public Encrypt(String key){
        init(key);
    }
    
    private void init(){
        deskey = new SecretKeySpec("xinzailing".getBytes(), Algorithm);
        try{
            cipher = Cipher.getInstance(Algorithm);
        }
        catch(NoSuchAlgorithmException e){
            throw new BusinessException("没有此加密算法，加密器初始化失败");
        }
        catch(NoSuchPaddingException e) {
            throw new BusinessException("加密器初始化失败");
        }
    }
    
    private void init(String key){
      deskey = new SecretKeySpec(key.getBytes(), Algorithm);
      try{
          cipher = Cipher.getInstance(Algorithm);
      }
      catch(NoSuchAlgorithmException e){
          throw new BusinessException("没有此加密算法，加密器初始化失败");
      }
      catch(NoSuchPaddingException e) {
          throw new BusinessException("加密器初始化失败");
      }
  }
    
    /**
     * 加密
     * @param datasource
     * @return
     */
    public byte[] encoder(byte datasource[]){
        byte encryptorData[] = (byte[])null;
        try {
            cipher.init(1, deskey);
            encryptorData = cipher.doFinal(datasource);
        }
        catch(InvalidKeyException ex) {
            throw new BusinessException("非法的加密密匙，加密失败");
        }
        catch(BadPaddingException ex) {
            throw new BusinessException("非法的加密数据，加密失败");
        }
        catch(IllegalBlockSizeException ex) {
            throw new BusinessException("加密字符串字节数不对，加密失败");
        }
        return encryptorData;
    }
    
    /**
     * 加密
     * @param datasource
     * @return
     */
    @SuppressWarnings("deprecation")
	public String encoder(String datasource) {
        byte encryptorData[] = encoder(datasource.getBytes());
        return URLEncoder.encode((new BASE64Encoder()).encode(encryptorData));
    }

    /**
     * 解密
     * @param datasource
     * @return
     */
    public byte[] decoder(byte datasource[]) {
        byte decryptorData[] = (byte[])null;
        try {
            cipher.init(2, deskey);
            decryptorData = cipher.doFinal(datasource);
        }
        catch(InvalidKeyException ex) {
            throw new BusinessException("非法的解密密匙，解密失败");
        }
        catch(BadPaddingException ex) {
            throw new BusinessException("非法的解密数据，解密失败");
        }
        catch(IllegalBlockSizeException ex) {
            throw new BusinessException("解密字符串字节数不对，解密失败");
        }
        return decryptorData;
    }

    /**
     * 解密
     * @param datasource
     * @return
     */
    public String decoder(String datasource){
        datasource = urlEncoder(datasource);
        byte decryptorData[] = new byte[datasource.length()];
        try {
            decryptorData = (new BASE64Decoder()).decodeBuffer(datasource);
        }
        catch(IOException e) {
            throw new BusinessException("字符串Base64解码失败");
        }
        return new String(decoder(decryptorData));
    }

    @SuppressWarnings("deprecation")
    public String urlEncoder(String datasource){
        if(datasource.indexOf(37) < 0){
            return datasource;
        }
        else{
            return urlEncoder(URLDecoder.decode(datasource));
        }
    }

    public static void main(String args[]){
        Encrypt encrypt = new Encrypt();
        String str = "123456";
        String str2 = encrypt.encoder(str);
        String dd = encrypt.decoder(str2);
        System.out.println(str2);
        System.out.println(dd);
    }
}

