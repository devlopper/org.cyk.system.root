package org.cyk.system.root.business.api.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;

import org.cyk.system.root.model.Mime;

public interface StreamBusiness {

	ByteArrayOutputStream merge(Collection<InputStream> inputStreams,Mime mime);
	
}
