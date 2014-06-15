package org.cyk.system.root.business.api.file;

import java.io.InputStream;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.File;

public interface FileBusiness extends TypedBusiness<File> {
    
    /**
     * Finds the Multipurpose Internet Mail Extensions
     * @return
     */
    String findMime(String extension);
    
    File process(byte[] bytes, String nom);
    
    InputStream findInputStream(File file);

}
