package com.iasri.javaee.bookstore;

public class HttpDownloader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       String fileURL="http://download.springsource.com/release/TOOLS/update/3.8.3.RELEASE/e4.6/springsource-tool-suite-3.8.3.RELEASE-e4.6.2-updatesite.zip";
       String saveDir="E:/";
       
       try{
    	   HttpDownloadUtility.downloadFile(fileURL,saveDir);    	   
       }catch(Exception e){
    	   
       }
	}

}
