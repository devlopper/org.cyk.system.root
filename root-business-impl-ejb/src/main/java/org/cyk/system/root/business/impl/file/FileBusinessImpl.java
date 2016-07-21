package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.business.api.file.StreamBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.utility.common.FileExtension;

public class FileBusinessImpl extends AbstractTypedBusinessService<File, FileDao> implements FileBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private MediaBusiness mediaBusiness;
	@Inject private StreamBusiness streamBusiness;
	
	@Inject
	public FileBusinessImpl(FileDao dao) {
		super(dao); 
	}

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS) //TODO is this Support and followings really needed ???
    public File process(byte[] bytes, String name) {
        if(bytes==null || bytes.length==0)
            return null;
        File file = new File();
        process(file, bytes, name);
        return file;
    }
    
    @Override
	public void process(File file, byte[] bytes, String name) {
    	file.setBytes(bytes);
        file.setExtension(findExtension(name));
        if(file.getExtension()==null){
        	file.setMime(null);
        }else{
            file.setExtension(file.getExtension().toLowerCase());//better use lower case because of mime type lookup
            file.setMime(findMime(file.getExtension()));
        }
	}

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String findMime(String extension) {
        try {
            return Files.probeContentType(Paths.get("file."+extension));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String findExtension(String name) {
    	int i = name.lastIndexOf('.');
        if (i > 0)
            return name.substring(i+1);
    	return null;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InputStream findInputStream(File file) {
        if(file.getBytes()==null)
            if(file.getUri()==null)
                exceptionUtils().exception("exception.file.nocontentnouri");
            else
                if("file".equals(file.getUri().getScheme()))
                    try {
                        return new FileInputStream(StringUtils.substring(file.getUri().getPath(), 1));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        exceptionUtils().resourceNotFound();
                    }
                else
                    exceptionUtils().exception("exception.file.urinothandled");
        else
            return new ByteArrayInputStream(file.getBytes());
        return null;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Path findSystemPath(File file, Boolean createTemporaryIfNotExist) {
    	if(file.getUri()==null){
    		try {
				Path temporaryFilePath = Files.createTempFile(null, null);
				IOUtils.write(file.getBytes(), new FileOutputStream(temporaryFilePath.toFile()));
				return temporaryFilePath;
			} catch (IOException e) {
				e.printStackTrace();
				exceptionUtils().exception("exception.file.temporaryfilenotcreated");
			}
    		
    	}else{
    		logError("Uri to file system path not yet handled");
    	}
    	return null;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Path findSystemPath(File file) {
    	return findSystemPath(file,Boolean.TRUE);
    }

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public URI findThumbnailUri(File file,ThumnailSize size) {
		return mediaBusiness.findThumbnailUri(file.getUri(), size);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public URI findEmbeddedUri(File file) {
		return mediaBusiness.findEmbeddedUri(file.getUri());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ByteArrayOutputStream merge(Collection<File> files,FileExtension fileExtension) {
		Collection<InputStream> inputStreams = new ArrayList<>();
		for(File file : files)
			inputStreams.add(findInputStream(file));
		return streamBusiness.merge(inputStreams, fileExtension);
	}

	@Override
	public Boolean isImage(File file) {
		return file!=null && StringUtils.startsWith(file.getMime(), Mime.IMAGE);
	}
}
