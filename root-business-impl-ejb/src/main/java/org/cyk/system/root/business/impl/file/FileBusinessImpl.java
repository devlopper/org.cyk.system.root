package org.cyk.system.root.business.impl.file;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.file.FileDao;

public class FileBusinessImpl extends AbstractTypedBusinessService<File, FileDao> implements FileBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public FileBusinessImpl(FileDao dao) {
		super(dao); 
	}

    @Override
    public File process(byte[] bytes, String nom) {
        if(bytes==null || bytes.length==0)
            return null;
        File file = new File();
        file.setBytes(bytes);
        int i = nom.lastIndexOf('.');
        if (i > 0) {
            file.setExtension(nom.substring(i+1));
        }
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
	
}
