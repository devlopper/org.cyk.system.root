package org.cyk.system.root.business.impl.security;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.system.root.business.api.security.OpticalDecoderOptions;
import org.cyk.system.root.business.api.security.OpticalDecoderOptions.ScannerOptions;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class OpticalDecoderBusinessImpl extends AbstractDecoderBusinessImpl<byte[], String,OpticalDecoderOptions> implements OpticalDecoderBusiness,Serializable {

	private static final long serialVersionUID = -3801241843233470117L;

	private static final OpticalDecoderOptions DEFAULT_OPTIONS = new OpticalDecoderOptions();
	static{
		DEFAULT_OPTIONS.getExceptionClassesToIgnore().add(NotFoundException.class);
		DEFAULT_OPTIONS.getExceptionClassesToIgnore().add(ChecksumException.class);
		DEFAULT_OPTIONS.getExceptionClassesToIgnore().add(FormatException.class);
	}
	
	@Override
	public String execute(byte[] data, OpticalDecoderOptions options) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return execute(bufferedImage, options);
	}
	
	@Override
	public String execute(BufferedImage bufferedImage,OpticalDecoderOptions options){
		String resultAsString = null;
		Map<DecodeHintType, Object> hints = new HashMap<>();
		Result result = null;
		ImageRegion region = new ImageRegion();
		try {
			hints = new HashMap<>();
			hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(barcodeFormat(options.getFormat())));
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE.equals(options.getTryHarder()));
			//hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE.equals(options.getPureBarCode()));

			result = fetch(bufferedImage,region, hints, options);
			resultAsString = result.getText();
		} catch (NotFoundException e) {
			if(Boolean.TRUE.equals(options.getFullScan()))
				result = scan(bufferedImage,region, hints, options);
			
			if(result==null){
				if(options.getExceptionClassesToIgnore().contains(e.getClass()))
					resultAsString = StringUtils.defaultString((String) options.getValueToReturnOnClassIgnored().get(e.getClass()));
				else
					return null;
					//throw new RuntimeException(e);
					//ExceptionUtils.getInstance().exception(EXCEPTION_NOT_FOUND, "exception");
			}else{
				resultAsString = result.getText();
			}
		} catch (ChecksumException e) {
			if(options.getExceptionClassesToIgnore().contains(e.getClass()))
				return StringUtils.defaultString((String) options.getValueToReturnOnClassIgnored().get(e.getClass()));
			ExceptionUtils.getInstance().exception(EXCEPTION_CHEKSUM, "exception");
		} catch (FormatException e) {
			if(options.getExceptionClassesToIgnore().contains(e.getClass()))
				return StringUtils.defaultString((String) options.getValueToReturnOnClassIgnored().get(e.getClass()));
			ExceptionUtils.getInstance().exception(EXCEPTION_FORMAT, "exception");
		}
		
		return resultAsString;
	}
	
	private Result fetch(BufferedImage bufferedImage,ImageRegion region,Map<DecodeHintType, Object> hints,OpticalDecoderOptions options) throws NotFoundException, ChecksumException, FormatException{
		LuminanceSource source; 
		if(region.getX()==null)
			source = new BufferedImageLuminanceSource(bufferedImage);  
		else
			source = new BufferedImageLuminanceSource(bufferedImage,region.getX(),region.getY(),region.getWidth(),region.getHeight());  
			//source = new BufferedImageLuminanceSource(bufferedImage.getSubimage(region.getX(),region.getY(),region.getWidth(),region.getHeight()));  
			
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source)); 
		Reader reader = new MultiFormatReader(); 
		return reader.decode(bitmap,hints);
	}

	@Override
	protected OpticalDecoderOptions defaultOptions() {
		return DEFAULT_OPTIONS;
	}
	
	/**/
	
	public Result scan(BufferedImage bufferedImage,ImageRegion region,Map<DecodeHintType, Object> hints,OpticalDecoderOptions options) {
		int width = bufferedImage.getWidth(),height = bufferedImage.getHeight(),w = 1,h=1;
		ScannerOptions scannerOptions = options.getScannerOptions();
		while(w<width && h<height){
			for(int x=scannerOptions.getXStart();x<width;x += scannerOptions.getXStep()){
				for(int y=scannerOptions.getYStart();y<height;y +=scannerOptions.getYStep()){
					try {
						region.setX(x);
						region.setY(y);
						region.setWidth(w);
						region.setHeight(h);
						Result result = fetch(bufferedImage,region, hints, options);
						if(result==null){
							
						}else{
							/*
							Graphics2D graph = bufferedImage.createGraphics();
					        graph.setColor(Color.RED);
					        graph.drawRect(x, y, w, h);
					        graph.dispose();
					        ImageIO.write(bufferedImage, "png", new File(new File("H:/barcode/split/"),System.currentTimeMillis()+".png"));
					        */
					        return result;
						}
					} catch (Exception e) {
						//System.out.println(e);
						//e.printStackTrace();
					}  
				}
			}
			w += scannerOptions.getXIncrement();
			h += scannerOptions.getYIncrement();
		}
		/*
		try {
			ImageIO.write(bufferedImage, "png", new File(new File("H:/barcode/split/"),"NULL_"+System.currentTimeMillis()+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		return null;
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	private class ImageRegion implements Serializable{

		private static final long serialVersionUID = -3203853326113008207L;

		private Integer x,y,width,height;
		
		@Override
		public String toString() {
			return x+","+y+","+width+","+height;
		}
	}
	

}
