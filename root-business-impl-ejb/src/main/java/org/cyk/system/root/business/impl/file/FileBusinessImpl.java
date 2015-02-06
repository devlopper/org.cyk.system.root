package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.file.FileDao;

public class FileBusinessImpl extends AbstractTypedBusinessService<File, FileDao> implements FileBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private MediaBusiness mediaBusiness;
	
	@Inject
	public FileBusinessImpl(FileDao dao) {
		super(dao); 
	}

    @Override
    public File process(byte[] bytes, String name) {
        if(bytes==null || bytes.length==0)
            return null;
        File file = new File();
        file.setBytes(bytes);
        file.setExtension(findExtension(name));
        if(file.getExtension()!=null){
            file.setExtension(file.getExtension().toLowerCase());//better use lower case because of mime type lookup
        }
        return file;
    }

    @Override
    public String findMime(String extension) {
        try {
            return Files.probeContentType(Paths.get("file."+extension));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String findExtension(String name) {
    	int i = name.lastIndexOf('.');
        if (i > 0)
            return name.substring(i+1);
    	return null;
    }
    
    @Override
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

	@Override
	public URI findThumbnailUri(File file,ThumnailSize size) {
		return mediaBusiness.findThumbnailUri(file.getUri(), size);
	}

	@Override
	public URI findEmbeddedUri(File file) {
		return mediaBusiness.findEmbeddedUri(file.getUri());
	}
	
}
