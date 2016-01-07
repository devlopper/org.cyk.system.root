package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
		}
		return outputStream;
	}
	
	

}
