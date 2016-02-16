package org.cyk.system.root.business.impl.file;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.cyk.system.root.business.api.file.StreamBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.Mime;

public class StreamBusinessImpl implements StreamBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;

	@Override
	public ByteArrayOutputStream merge(Collection<InputStream> inputStreams, Mime mime) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if(Mime.PDF.equals(mime)){
			PDFMergerUtility merger = new PDFMergerUtility();
			merger.addSources(new ArrayList<>(inputStreams));
			merger.setDestinationStream(outputStream);
			try {
				merger.mergeDocuments(null);
			} catch (IOException e) {
				e.printStackTrace();
				ExceptionUtils.getInstance().exception("stream.cannotmerge");
			}
		}else if(Mime.IMAGE_JPEG.equals(mime)){
			BufferedImage result = new BufferedImage(500, 800, BufferedImage.TYPE_INT_RGB);
			Graphics g = result.getGraphics();
			
			int x=0,y=0;
			for(InputStream inputStream : inputStreams){
		        BufferedImage bi;
				try {
					bi = ImageIO.read(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
		        g.drawImage(bi, x, y, null);
		        x = 0;
		        y += bi.getHeight();
		    }
			try {
				ImageIO.write(result,"jpeg",outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outputStream;
	}
	
	

}
