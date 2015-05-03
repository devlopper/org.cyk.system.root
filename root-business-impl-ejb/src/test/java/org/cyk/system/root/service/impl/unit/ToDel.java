package org.cyk.system.root.service.impl.unit;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.FileUtils;
import org.cyk.system.root.business.api.security.OpticalDecoderOptions;
import org.cyk.system.root.business.impl.security.OpticalDecoderBusinessImpl;

public class ToDel {

	static OpticalDecoderOptions decoderOptions = new OpticalDecoderOptions();
	static OpticalDecoderBusinessImpl decoderBusiness = new OpticalDecoderBusinessImpl();
	
	public static void main(String[] args) {
		decoderOptions.getExceptionClassesToIgnore().clear();
		decoderBusiness = new OpticalDecoderBusinessImpl();
				
		for(String fn : new File("H:/barcode/taken/null/").list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("png");
			}
		})){
			try {
				System.out.println(fn+" : "+decoderBusiness.execute(FileUtils.readFileToByteArray(new File("H:/barcode/taken/null/"+fn)), decoderOptions));
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
		
		
	}
		
}
