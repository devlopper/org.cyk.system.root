package org.cyk.system.root.service.impl.unit;

import java.awt.Color;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.security.BarcodeFormat;
import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.system.root.business.api.security.OpticalDecoderOptions;
import org.cyk.system.root.business.api.security.OpticalEncoderBusiness;
import org.cyk.system.root.business.api.security.OpticalEncoderOptions;
import org.cyk.system.root.business.impl.security.OpticalDecoderBusinessImpl;
import org.cyk.system.root.business.impl.security.OpticalEncoderBusinessImpl;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class EncoderDecoderUT extends AbstractUnitTest {

    private static final long serialVersionUID = -6691092648665798471L;

    @Override
    protected void _execute_() {
        super._execute_();
    }

    @Test
    public void opticalEncoderDecoder() {
    	String[] messages = new String[]{"manager\r\n123","zadi\r\n123"};
    	assertOpticalEncoderDecoder(messages,BarcodeFormat.QR);
    	//assertOpticalEncoderDecoder(new String[]{"Jesus is the lord","God is good"},BarcodeFormat._128);
    }
    
    public void assertOpticalEncoderDecoder(String[] messages,BarcodeFormat barcodeFormat) {
    	OpticalEncoderOptions encoderOptions = new OpticalEncoderOptions();
    	encoderOptions.setForegroundColor(Color.BLACK);
    	encoderOptions.setFormat(barcodeFormat);
    	OpticalDecoderOptions decoderOptions = new OpticalDecoderOptions();
    	decoderOptions.setFormat(barcodeFormat);
    	assertOpticalEncoderDecoder(messages, encoderOptions, decoderOptions);
    }
    
    public void assertOpticalEncoderDecoder(String[] messages,OpticalEncoderOptions encoderOptions,OpticalDecoderOptions decoderOptions) {
    	for(String message : messages){
    		 File file = opticalEncode(message,encoderOptions);
    		 opticalDecodeAssert(file, message,decoderOptions);
    	}
    }
    
    public File opticalEncode(String data,OpticalEncoderOptions options) {
        OpticalEncoderBusiness encoder = new OpticalEncoderBusinessImpl();
        String name = options.getFormat().name()+"_"+System.currentTimeMillis()+"."+options.getFile().getExtension();
        String fileName = "target/opticalcode/"+(StringUtils.startsWith(name, "_")?StringUtils.substring(name, 1):name);
        File file = null;
        try {
			FileUtils.writeByteArrayToFile(file = new File(fileName), encoder.execute(data, options));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return file;
    }
    
    public void opticalDecodeAssert(File file,String data,OpticalDecoderOptions options) {
    	OpticalDecoderBusiness decoder = new OpticalDecoderBusinessImpl();
    	try {
			Assert.assertEquals(data, decoder.execute(FileUtils.readFileToByteArray(file),options));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
