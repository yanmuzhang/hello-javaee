package com.icbc;

import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.Reader; 
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import org.jdom.Document; 
import org.jdom.Element; 
import org.jdom.input.SAXBuilder; 


/**
 * TODO ʾ��demo,�����ο�
 * Author yangjian
 * CreateDate 2018-10-12 ����12:57:27
 */
public class TrxTest { 

	/**
	 * TODO  byte����תint
	 * Author yangjian
	 * CreateDate 2018-10-12 ����12:39:30
	 */
	public static int byteToInt(byte[] b){ 
		return (((b[0]&0xFF)<<24)+((b[1]&0xFF)<<16)+((b[2]&0xFF)<<8)+((b[3]&0xFF)<<0)); 
	} 
	/**
	 * TODO  intתbyte����
	 * Author yangjian
	 * CreateDate 2018-10-12 ����12:39:40
	 */
	public static byte[] intToByte(int b){ 
		return new byte[]{ 
				(byte)((b>>24)&0xFF), 
				(byte)((b>>16)&0xFF), 
				(byte)((b>>8)&0xFF), 
				(byte)(b&0xFF) 
		}; 
	} 
	/**
	 * TODO  ����demo
	 * Author yangjian
	 * CreateDate 2018-10-12 ����12:39:53
	 */
	public static String sendMsg(String trxcode,String snd_xml){ 
		String rcv_xml = ""; 
		Socket cSocket = null; 
		DataOutputStream dout = null; 
		DataInputStream dis = null; 
		try{ 
			String key = "29cc6ce5ab4a841a"; //���Լ���key
			String encoding = "UTF-8"; //����ʹ��utf-8
			String sign = MD5.encrypt(snd_xml, encoding); 
			System.out.println("snd_xml="+snd_xml); 
			System.out.println("sign="+sign); 

			snd_xml = AESPadding5.encrypt(snd_xml, key.getBytes(encoding), encoding); 
			sign = AESPadding5.encrypt(sign, key.getBytes(encoding), encoding); 
			/*
			 * ͳһ��װ��xml���ĸ�ʽ���͹�������ʽΪ<ICBC><Trxcode></Trxcode><Encrypt></Encrypt><Sign></Sign></ICBC>
			 */
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><ICBC><Trxcode>"+trxcode+"</Trxcode><Encrypt>"+snd_xml+"</Encrypt><Sign>"+sign+"</Sign></ICBC>"; 
			System.out.println("snd xml="+xml);


//			InetSocketAddress addr = new InetSocketAddress("88.88.16.82",17184);
//			Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // http 代理


			/*
			 * ����socket
			 */
//			cSocket=new Socket("95.6.13.35",17173);
//			cSocket=new Socket(proxy);
			cSocket=new Socket();
			cSocket.setSoTimeout(30000); //��ʱ����
			cSocket.connect(new InetSocketAddress("192.168.56.212",17184));


			dout=new DataOutputStream(cSocket.getOutputStream()); 
			dis = new DataInputStream(cSocket.getInputStream()); 

			/*
			 * ����ͨѶʱ��Ҫ��������ȷ�4���ֽڵĳ��� 
			 */
			byte[] xmlBytes=xml.getBytes("GBK"); //xml��������byte����
			int len=xmlBytes.length; //����xml����byte����ĳ���
			byte[] len_byte = intToByte(len); //����������ת����bytes����
			byte[] full_byte = new byte[len_byte.length+xmlBytes.length]; //��ʼ��ȫ��byte���飬ǰ4λ��4���ֽڵ�int�������Ǳ���
			System.arraycopy(len_byte, 0, full_byte, 0, len_byte.length); //����copy
			System.arraycopy(xmlBytes, 0, full_byte, len_byte.length, xmlBytes.length); //����copy
			/*
			 * ��������
			 */
			dout.write(full_byte);
			dout.flush(); 

			/*
			 *  ȡ��ԭ��writeInt�Լ�write��2�εķ�ʽ
                byte[] rawBytes=xml.getBytes("GBK"); 
                int len=rawBytes.length; 
                dout.writeInt(len); //java int��4���ֽڣ�4 byte��32bit�� 
                dout.write(rawBytes); 
                dout.flush(); 
			 */ 

			cSocket.shutdownOutput(); 

			/*
			 * ����ͨѶʱ��Ҫ�����������4���ֽڵĳ��� 
			 * ��Ҫע�⣺���ж��ڽ��ײ������޺����ֹ���󣬲����������ִERTL���󣬺������յ�socket������Ϣ����Ҫ���ж�ǰ4���ֽ��Ƿ�ΪERTL;
			 * ���ΪERTL������ñ�������Ϊ��������,�����ܾ������ҵ��δ�����κδ���;
			 * 
			 * Ŀǰdemo�в��õ�����read 4���ֽڣ�Ȼ��read���ݵķ�ʽ���������������ǲ���һ���Զ���ķ�ʽ��Ȼ����ȡǰ4���ֽ����ж�
			 */

			byte[] recv_bytes_first_4 = new byte[4]; 
			dis.read(recv_bytes_first_4, 0, 4);//��ȡ4���ֽ� 

			String content_4_byte = new String(recv_bytes_first_4,"GBK"); //���Ƚ�ǰ4��byteת�����ַ���,�ж��Ƿ�δERTL
			if("ERTL".equals(content_4_byte)){ 
				System.out.println("error:transaction limit!"); 
				return null; 
			}else{ 
				int rec_len = byteToInt(recv_bytes_first_4); //��bytes����ת��Ϊint
				System.out.println("rec_len="+rec_len); 
				if(rec_len == 0){ //�������Ϊ0���ȴ���δ�յ�����
					System.out.println("error:recv fail!"); 
					return null; 
				} 
				/*
				 * ��ȡ����
				 */
				byte[] tmp = new byte[rec_len]; 
				dis.readFully(tmp); 
				
				/*
				 *   ȡ����ԭ��eadInt��readFully�����εķ�ʽ
                     byte[] tmp = new byte[dis.readInt()];//java int��4���ֽڣ�4 byte��32bit�� 
                     dis.readFully(tmp); 
                     String rcv_xml_gbk = new String(tmp,"GBK"); 
                     System.out.println("rcv xml="+rcv_xml_gbk); 
				 */ 
				
				String rcv_xml_gbk = new String(tmp,"GBK"); 
				System.out.println("rcv xml="+rcv_xml_gbk); 
				
				//String rcv_sign = ""; 

				//���յ�����Ҳ��<ICBC><Encrypt></Encrypt><Sign></Sign></ICBC>��ʽ 

				//�������ر��� 
				SAXBuilder builder = new SAXBuilder(); 
				Reader ins= new StringReader(rcv_xml_gbk); 
				Document read_doc = builder.build(ins); 
				Element root = read_doc.getRootElement(); 
				Element Encrypt = root.getChild("Encrypt"); 
				String Encrypt_str = Encrypt.getText().trim(); 
				Element Sign = root.getChild("Sign"); 
				String Sign_str = Sign.getText().trim(); 
				System.out.println("rcv_encrypt_xml="+Encrypt_str); 
				System.out.println("rcv_encrypt_sign="+Sign_str); 

				String Encrypt_str_decry = AESPadding5.decrypt(Encrypt_str, key.getBytes(encoding), encoding); 
				String Sign_str_decry = AESPadding5.decrypt(Sign_str, key.getBytes(encoding), encoding); 
				rcv_xml = Encrypt_str_decry; 

				System.out.println("rcv_decrypt_xml="+Encrypt_str_decry); 
				System.out.println("rcv_decrypt_sign="+Sign_str_decry); 

				System.out.println("sign_check="+(MD5.encrypt(Encrypt_str_decry, encoding)).equals(Sign_str_decry)); 
			} 




		}catch(Exception e){ 
			e.printStackTrace(); 
		}finally{ 
			try{ 
				dis.close(); 
				dout.close(); 
				cSocket.close(); 
			}catch(Exception e){ 
				e.printStackTrace(); 
			} 

		} 
		return rcv_xml; 
	} 


	public static void main(String[] args) { 
		// TODO Auto-generated method stub 
		String snd_xml_0000 = "<?xml version=\"1.0\" encoding=\"GBK\"?><ICBC><CTL><TRXCODE>0001</TRXCODE><CORPID>XW</CORPID><PRODID>P0001</PRODID><CORP_SERIALNO>201810111234567</CORP_SERIALNO><WORKDATE>2018-10-11</WORKDATE><WORKTIME>10:37:33</WORKTIME></CTL><INFO><ACCNO>6222123456789</ACCNO></INFO></ICBC>";
		String rxc_xml_0000 = sendMsg("0001",snd_xml_0000);

		System.out.println(rxc_xml_0000);
		
	} 

}