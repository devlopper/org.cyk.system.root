package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.net.URI;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.MediaBusiness;
import org.cyk.system.root.business.api.file.YoutubeMediaBusiness;
import org.cyk.utility.common.annotation.Generic;

@Generic
public class MediaBusinessImpl implements MediaBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	//TODO at the moment we do use youtube. Do add another video site like vimeo
	@Inject private YoutubeMediaBusiness youtube;
	
	@Override
	public URI findThumbnailUri(URI uri,ThumnailSize size) {
		return youtube.findThumbnailUri(uri, size);
	}

	@Override
	public URI findEmbeddedUri(URI uri) {
		return youtube.findEmbeddedUri(uri);
	}
	
}
