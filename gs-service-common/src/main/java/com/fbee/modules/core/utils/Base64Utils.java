package com.fbee.modules.core.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Base64Utils {

	//图片转化成base64字符串  
	public static String GetImageStr()  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "d:/test.jpg";//待处理的图片  
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
      
    //base64字符串转化成图片  
    public static String GenerateImage(String imgStr, String certNo){  
    	//对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i){  
                if( b[i]<0 ){
                	//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            String imgFilePath = "/fbee/images/staff/headImage/"+ certNo + ".jpg";//新生成的图片  
//            String imgFilePath = "d:/"+ certNo + ".jpg";//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return "/staff/headImage/"+ certNo + ".jpg";  
        }   
        catch (Exception e){  
            return null;  
        }  
    }
	
    public static void main(String[] args)  {  
//      String strImg = GetImageStr();  
//      System.out.println(strImg);  
  	  String strImg = "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";
      GenerateImage(strImg, "321283199306301817");  
  }  
    
}
