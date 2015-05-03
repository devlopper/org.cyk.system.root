package org.cyk.system.root.business.api.security;

import java.awt.Color;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OpticalEncoderOptions extends OpticalEncoderDecoderOptions implements Serializable {

	private static final long serialVersionUID = 2151716694164578593L;

	public static final BarcodeFormat DEFAULT_FORMAT = BarcodeFormat.QR;
	public static final Double DEFAULT_ERROR_CORRECTION_LEVEL = 50d;
	public static final Integer DEFAULT_MARGIN = 0;
	
	private File file = new File();
	private Shape shape = Shape.AUTO;
	private Double errorCorrectionLevel = DEFAULT_ERROR_CORRECTION_LEVEL;
	private Integer margin = DEFAULT_MARGIN;
	private Color foregroundColor=Color.BLACK,backgroundColor=Color.WHITE;
	
	/**/
	
	@Getter @Setter
	public static class File implements Serializable {
		
		private static final long serialVersionUID = 5232806127898864031L;
		
		public static final String DEFAULT_EXTENSION = "png";
		public static final Integer DEFAULT_WIDTH = 400;
		public static final Integer DEFAULT_HEIGHT = 400;
		
		private String extension=DEFAULT_EXTENSION;
		private Integer width=DEFAULT_WIDTH,height=DEFAULT_HEIGHT;
		
	}
	
	public enum Shape{
		AUTO,
		SQUARE,
		RECTANGLE,
		
		;
	}
	
	/*
	public interface GraphicsListener{
		
		color
		
	}*/
	
}
