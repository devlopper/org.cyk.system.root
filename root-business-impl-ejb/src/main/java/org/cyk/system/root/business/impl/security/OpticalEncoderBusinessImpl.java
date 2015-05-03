package org.cyk.system.root.business.impl.security;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.security.OpticalEncoderBusiness;
import org.cyk.system.root.business.api.security.OpticalEncoderOptions;
import org.cyk.system.root.business.api.security.OpticalEncoderOptions.Shape;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

public class OpticalEncoderBusinessImpl extends AbstractEncoderBusinessImpl<String, byte[],OpticalEncoderOptions>  implements OpticalEncoderBusiness,Serializable {

	private static final long serialVersionUID = -3801241843233470117L;

	private static final OpticalEncoderOptions DEFAULT_OPTIONS = new OpticalEncoderOptions();
	
	@Override
	public byte[] execute(String data,OpticalEncoderOptions options) {  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ErrorCorrectionLevel errorCorrectionLevel=null;
		try {
			BarcodeFormat format = barcodeFormat(options.getFormat());
			Writer writer = null;
			switch(format){
			case QR_CODE:
				writer=new QRCodeWriter();
				break;
			case CODABAR:
				writer=new CodaBarWriter();
				break;
			case CODE_128:
				writer=new Code128Writer();
				break;
			default:
				ExceptionUtils.getInstance().exception(new IllegalArgumentException("No encoder available for format " + format));
				break;
			}
			Map<EncodeHintType, Object> hints = new HashMap<>();
			if(StringUtils.isNotBlank(options.getCharacterSet()))
				hints.put(EncodeHintType.CHARACTER_SET, options.getCharacterSet());
			if(options.getShape()!=null){
				Shape shape = options.getShape()==null?Shape.SQUARE:options.getShape();
				switch(shape){
				case AUTO:case SQUARE:
					hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
					break;
				case RECTANGLE:
					hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_RECTANGLE);
					break;
				}
				
			}
			if(options.getErrorCorrectionLevel()!=null){
				if(options.getErrorCorrectionLevel()<=7)
					errorCorrectionLevel = ErrorCorrectionLevel.L;
				else if(options.getErrorCorrectionLevel()<=15)
					errorCorrectionLevel = ErrorCorrectionLevel.M;
				else if(options.getErrorCorrectionLevel()<=25)
					errorCorrectionLevel = ErrorCorrectionLevel.Q;
				else if(options.getErrorCorrectionLevel()<=30)
					errorCorrectionLevel = ErrorCorrectionLevel.H;
				else
					errorCorrectionLevel = ErrorCorrectionLevel.H;
				hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
			}
			if(options.getMargin()!=null)
				hints.put(EncodeHintType.MARGIN, options.getMargin());
			
			if(BarcodeFormat.QR_CODE.equals(format)){
				QRCode qrCode = Encoder.encode(data, errorCorrectionLevel,hints);
				ByteMatrix byteMatrix = qrCode.getMatrix();
				java2d(byteMatrix,baos,options);
			}else{
				BitMatrix bitMatrix = writer.encode(data,format,options.getFile().getWidth() , options.getFile().getHeight(),hints);
				MatrixToImageWriter.writeToStream(bitMatrix, options.getFile().getExtension(), baos);
			}
			
			return baos.toByteArray();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	private void java2d(ByteMatrix matrix,ByteArrayOutputStream baos,OpticalEncoderOptions options) throws IOException {
		// Java 2D Traitement de Area
        // Futurs modules
        Area a = new Area();
        Area module = new Area(new Rectangle.Float(0, 0, 1, 1));

        // Deplacement du module
        AffineTransform at = new AffineTransform(); 
        int width = matrix.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i) == 1) {
                    // Ajout du module
                    a.add(module);
                }
                // Decalage a droite
                at.setToTranslation(1, 0); 
                module.transform(at);
            }

            // Ligne suivante
            at.setToTranslation(-width, 1); 
            module.transform(at);
        }

        // Agrandissement de l'Area pour le remplissage de l'image
        double ratio = options.getFile().getWidth() / (double) width;
        // Quietzone : 4 modules de bordures autour du QR Code (zone vide pour bien identifier le code dans la page imprimee)
        Integer margin = options.getMargin()==null?1:options.getMargin();
        if(margin==0)
        	margin = 1;
        
        double adjustment = width / (double) (width + 2*margin);
        ratio = ratio * adjustment;
        at.setToTranslation(margin, margin); 
        a.transform(at);

        // On agrandit le tour a la taille souhaitee.
        at.setToScale(ratio, ratio); 
        a.transform(at);
        
        //-----------------------------------------------------
        
        // Java 2D Traitement l'image
        BufferedImage im = new BufferedImage(options.getFile().getWidth(), options.getFile().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setPaint(options.getForegroundColor());
        g.setBackground(options.getBackgroundColor());
        g.clearRect(0, 0, options.getFile().getWidth(), options.getFile().getHeight());
        
        // Remplissage des modules
        g.fill(a); 
        
        //------------------------------------------------
        
        ImageIO.write(im, options.getFile().getExtension(), baos);
    }

	
	@Override
	protected OpticalEncoderOptions defaultOptions() {
		return DEFAULT_OPTIONS;
	}

}
