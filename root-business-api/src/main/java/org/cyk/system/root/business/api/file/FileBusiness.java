package org.cyk.system.root.business.api.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.model.Mime;
import org.cyk.system.root.model.file.File;

public interface FileBusiness extends TypedBusiness<File> {
    
    /**
     * Finds the Multipurpose Internet Mail Extensions
     * @return
     */
    String findMime(String extension);
    
    String findExtension(String name);
    
    File process(byte[] bytes, String name);
    
    void process(File file,byte[] bytes, String name);
    
    InputStream findInputStream(File file);
    
    Path findSystemPath(File file,Boolean createTemporaryIfNotExist);
    
    Path findSystemPath(File file);
    
    ByteArrayOutputStream merge(Collection<File> files,Mime mime);
    
    /* Media Stuff */
    
    URI findThumbnailUri(File file,ThumnailSize size);
    
    URI findEmbeddedUri(File file);

}
