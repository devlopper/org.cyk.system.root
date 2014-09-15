package org.cyk.system.root.business.api.file;

import java.net.URI;

public interface MediaBusiness {
    
	public enum ThumnailSize {_1,_2,_3,_4,_5}
	
    URI findThumbnailUri(URI uri,ThumnailSize size);

    URI findEmbeddedUri(URI uri);
}
