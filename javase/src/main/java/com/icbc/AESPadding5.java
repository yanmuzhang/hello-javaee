package com.icbc;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESPadding5 {
	
	public static final String ALGORITHM = "AES/ECB/PKCS5Padding"; 
	
	/** 
     * @param String str  Ҫ�����ܵ��ַ��� 
     * @param byte[] key  ��/����Ҫ�õĳ���Ϊ16���ֽ����飨128λ����Կ 
     * @param String encoding  �ַ����� UTF-8 ���� GBK 
     * @return byte[]  ���ܺ���ֽ����� 
     */ 
    public static String encrypt(String str, byte[] key, String encoding){ 
//	    	//��ȡ��־����
//			Log logger = LogManager.getTradeLogger();
//			if(null == logger){
//				logger = LogFactory.getCtpLog();
//			}
            String re = ""; 
            byte[] result = null; 
            try{ 
            	Cipher cipher = Cipher.getInstance(ALGORITHM); 
            	SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //���ɼ��ܽ�����Ҫ��Key 
            	cipher.init(Cipher.ENCRYPT_MODE, keySpec); 
            	result = cipher.doFinal(str.getBytes(encoding)); 
            	BASE64Encoder base64encoder = new BASE64Encoder();
            	re = base64encoder.encode(result); 
            }catch(Exception e){ 
            	re = null;
                System.out.println( "AESPadding5.encrypt�쳣,{0}"+new Object[]{e});
                System.out.println("=========>�쳣��Ϣ="+e);
            } 
            return re; 
    } 
    
    /** 
     * @param String str  Ҫ�����ܵ����� 
     * @param byte[] key  ��/����Ҫ�õĳ���Ϊ16���ֽ����飨128λ����Կ
     * @param String encoding  �ַ����� UTF-8 ���� GBK 
     * @return String  ���ܺ���ַ��� 
     */ 
    public static String decrypt(String str, byte[] key, String encoding){ 
	    	//��ȡ��־����
//			Log logger = LogManager.getTradeLogger();
//			if(null == logger){
//				logger = LogFactory.getCtpLog();
//			}
            String result = null; 
            try{ 
            	Cipher cipher = Cipher.getInstance(ALGORITHM); 
            	SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //���ɼ��ܽ�����Ҫ��Key 
            	cipher.init(Cipher.DECRYPT_MODE, keySpec); 
            	BASE64Decoder base64decoder = new BASE64Decoder();
                byte[] decoded = cipher.doFinal(base64decoder.decodeBuffer(str)); 
            	result = new String(decoded, encoding); 
            }catch(Exception e){ 
            	result = null;
                System.out.println(  "AESPadding5.decrypt�쳣,{0}"+ new Object[]{e});
                System.out.println( "=========>�쳣��Ϣ="+e);
            } 
            return result; 
    } 
    
    public static void main(String[] args){
    	 try{ 
             String key = "29cc6ce5ab4a841a"; 
             String encoding = "UTF-8";
             byte[] keyBytes = key.getBytes(encoding);; 
             System.out.println(keyBytes.length); 
             String str = "{\"idCard\":\"533522199812052854\",\"channelId\":26,\"name\":\"�\"}"; 
             System.out.println("ԭ���ģ�"+str); 
             String m_w = AESPadding5.encrypt(str,keyBytes,encoding); 
             System.out.println("���ܺ�"+m_w);// 
             String decode = AESPadding5.decrypt(m_w, keyBytes,encoding); 
             System.out.println("���ܺ��ģ�"+decode); 
    	 }catch(Exception e){ 
             //YapiLogger.errorEx(logger,"=========>�쳣��Ϣ=",e); 
    		 e.printStackTrace();
    	 } 
    }
    
}
