package org.cyk.system.root.business.api.file;

import java.net.URI;

public interface YoutubeMediaBusiness extends MediaBusiness {
    
    String findVideoId(URI uri);
}
