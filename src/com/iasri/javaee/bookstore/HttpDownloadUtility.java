package com.iasri.javaee.bookstore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {
	private static final int BUFFER_SIZE=4096;
	public static void downloadFile(String fileURL, String saveDir) throws IOException{
		URL url =new URL(fileURL);
		
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		
		int responseCode=conn.getResponseCode();
		
		if(responseCode==HttpURLConnection.HTTP_OK)
		{
			String fileName="";
			String disposition=conn.getHeaderField("Content-Disposition");
			String contentType=conn.getContentType();
			int contentLength=conn.getContentLength();
			
			if(disposition!=null){
				int index=disposition.indexOf("filename=");
				if(index>0)
				{
					fileName=disposition.substring(index+10, disposition.length()-1);	
				}
			}else{
				fileName=fileURL.substring(fileURL.lastIndexOf("/")+1, fileURL.length());
			}
			
			System.out.println("Content Type :: "+contentType);
			System.out.println("Content Disposition :: "+disposition);
			System.out.println("Content Length :: "+contentLength);
			System.out.println("Filename :: "+fileName);
			
			InputStream inputStream=conn.getInputStream();
			
			String saveFilePath=saveDir+File.separator+fileName;
			
			FileOutputStream fos=new FileOutputStream(saveFilePath);
			
			int bytesRead=-1;
			byte[] buffer=new byte[BUFFER_SIZE];
			
			while((bytesRead=inputStream.read(buffer))!=-1)
				fos.write(buffer, 0, bytesRead);
			
			fos.close();
			inputStream.close();
			System.out.println("File downloaded...!");
		}else{
			System.out.println("File not downloaded...!");
		}
		conn.disconnect();		
	}
}
