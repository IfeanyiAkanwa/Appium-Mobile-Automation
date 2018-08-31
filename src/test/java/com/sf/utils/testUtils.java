package com.sf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class testUtils extends testBase{

	 @SuppressWarnings("resource")
	    public static String addScreenshot() {

	        TakesScreenshot ts = (TakesScreenshot) getDriver();
	        File scrFile = ts.getScreenshotAs(OutputType.FILE);

	        String encodedBase64 = null;
	        FileInputStream fileInputStreamReader = null;
	        try {
	            fileInputStreamReader = new FileInputStream(scrFile);
	            byte[] bytes = new byte[(int)scrFile.length()];
	            fileInputStreamReader.read(bytes);
	            encodedBase64 = new String(Base64.encodeBase64(bytes));



	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "data:image/png;base64,"+encodedBase64;
	    }
}
