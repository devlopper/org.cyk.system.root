package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.api.security.AbstractEncoderDecoderBusiness;

import com.google.zxing.BarcodeFormat;

public abstract class AbstractEncoderDecoderBusinessImpl<SOURCE,DESTINATION,OPTIONS> implements AbstractEncoderDecoderBusiness<SOURCE,DESTINATION,OPTIONS>,Serializable {

	private static final long serialVersionUID = -4553708311085832491L;

	@Override
	public DESTINATION execute(SOURCE data) {
		return execute(data, defaultOptions());
	}
	
	protected abstract OPTIONS defaultOptions();
	
	protected BarcodeFormat barcodeFormat(org.cyk.system.root.business.api.security.BarcodeFormat format){
		if(format==null)
			format = org.cyk.system.root.business.api.security.BarcodeFormat.QR;
		switch(format){
		case _128:return BarcodeFormat.CODE_128;
		case CODABAR:return BarcodeFormat.CODABAR;
		case QR:return BarcodeFormat.QR_CODE;
		default : return BarcodeFormat.QR_CODE;
		}
	}
	
}
