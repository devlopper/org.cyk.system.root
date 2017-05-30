package org.cyk.system.root.business.api.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.utility.common.FileExtension;

public interface FileBusiness extends TypedBusiness<File> {
    
    /**
     * Finds the Multipurpose Internet Mail Extensions
     * @return
     */
    String findMime(String extension);
    
    String findExtension(String name);
    
    File process(byte[] bytes, String name);
    
    void process(File file,byte[] bytes, String name,Boolean reset);
    void process(File file,byte[] bytes, String name);
    
    InputStream findInputStream(File file,Boolean keep);
    InputStream findInputStream(File file);
    byte[] findBytes(File file);
    
    Path findSystemPath(File file,Boolean createTemporaryIfNotExist);
    
    Path findSystemPath(File file);
    
    ByteArrayOutputStream merge(Collection<File> files,FileExtension fileExtension);
    
    void writeTo(File file,java.io.File directory,String name);
    
    Collection<File> findByFileIdentifiableGlobalIdentifierSearchCriteria(FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria);
    Collection<File> findByRepresentationTypesByIdentifiables(Collection<FileRepresentationType> fileRepresentationTypes,Collection<? extends AbstractIdentifiable> identifiables);
    Collection<File> findByRepresentationTypeByIdentifiables(FileRepresentationType fileRepresentationType,Collection<? extends AbstractIdentifiable> identifiables);
    Collection<File> findByRepresentationTypeByIdentifiable(FileRepresentationType fileRepresentationType,AbstractIdentifiable identifiable);
    Collection<File> findByRepresentationTypeCodeByIdentifiable(String fileRepresentationTypeCode,AbstractIdentifiable identifiable);
    
    /* Media Stuff */
    
    URI findThumbnailUri(File file,ThumnailSize size);
    
    URI findEmbeddedUri(File file);

    Boolean isImage(File file);
    Boolean isText(File file);
    
    public static interface Mime {
    	String IMAGE = "image";
    	String TEXT = "text";
    	String APPLICATION_OCTET_STREAM = "application/octet-stream";
    }
	
}
