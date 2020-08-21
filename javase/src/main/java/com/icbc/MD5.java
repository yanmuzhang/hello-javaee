package com.icbc;

import java.security.MessageDigest;

public class MD5 {
	public static String encrypt(String str,String encoding){
		String encryStr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes(encoding));
			encryStr = bytesToHexString(md5.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			encryStr = null;
			System.out.println("MD5.encrypt�쳣,{0}"+ new Object[]{e});
			System.out.println("=========>�쳣��Ϣ="+e);
		}
		
		return encryStr;
	}
	/**
     * ��byte����ת��Ϊ16�����ַ�����ʽ
     * @param bys
     * @return
     */
    public static String bytesToHexString(byte[] bys){
        StringBuffer hexVal=new StringBuffer();
        int val=0;
        for (int i = 0; i < bys.length; i++) {
            //��byteת��Ϊint  ���byte��һ�������ͱ���Ҫ��16���Ƶ�0xff��һ��������
            val=((int)bys[i]) & 0xff;
            if(val<16){
                hexVal.append("0");
            }
            hexVal.append(Integer.toHexString(val));
        }

        return hexVal.toString();

    }
    
    public static void main(String[] args){
    	System.out.println(MD5.encrypt("1234567890", "UTF-8"));
    }
}
