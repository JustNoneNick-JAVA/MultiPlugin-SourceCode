package com.jnngl.plugins.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Library {
	
	private static boolean isRedirected( Map<String, List<String>> header ) {
	      for( String hv : header.get( null )) {
	         if(   hv.contains( " 301 " )
	            || hv.contains( " 302 " )) return true;
	      }
	      return false;
	}
	
	public static void unzipLibrary() {
		String source = "./JNNPL-0.0.1.zip";
	    String destination = "./";
	    String password = "";

	    try {
	         ZipFile zipFile = new ZipFile(source);
	         if (zipFile.isEncrypted()) {
	            zipFile.setPassword(password);
	         }
	         zipFile.extractAll(destination);
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
    }
     
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
   }
	
	public static void getLibrary (  ) throws Throwable
	   {
	      String link ="https://github.com/JustNoneNick-JAVA/JNNNPL/raw/master/JNNPL-0.0.1.zip";
	      String            fileName = "./JNNPL-0.0.1.zip";
	      URL               url  = new URL( link );
	      HttpURLConnection http = (HttpURLConnection)url.openConnection();
	      Map< String, List< String >> header = http.getHeaderFields();
	      while( isRedirected( header )) {
	         link = header.get( "Location" ).get( 0 );
	         url    = new URL( link );
	         http   = (HttpURLConnection)url.openConnection();
	         header = http.getHeaderFields();
	      }
	      InputStream  input  = http.getInputStream();
	      byte[]       buffer = new byte[4096];
	      int          n      = -1;
	      OutputStream output = new FileOutputStream( new File( fileName ));
	      while ((n = input.read(buffer)) != -1) {
	         output.write( buffer, 0, n );
	      }
	      output.close();
	   }
	
}
