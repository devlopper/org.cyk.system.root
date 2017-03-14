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
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness;
import org.cyk.system.root.business.api.file.MediaBusiness.ThumnailSize;
import org.cyk.system.root.business.api.file.StreamBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.PersistDataListener;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.LogMessage;

public class FileBusinessImpl extends AbstractTypedBusinessService<File, FileDao> implements FileBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject private MediaBusiness mediaBusiness;
	@Inject private StreamBusiness streamBusiness;
	
	private static final String NAME_EXTENSION_SEPARATOR = Constant.CHARACTER_DOT.toString();
	private static final String FILE = "file";
	private static final String FILE_DOT = FILE+NAME_EXTENSION_SEPARATOR;
	
	@Inject
	public FileBusinessImpl(FileDao dao) {
		super(dao); 
	}
	
	@Override
	protected Object[] getPropertyValueTokens(File file, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_NAME}, name) && file.getRepresentationType()!=null)
			return new Object[]{file.getRepresentationType()};
		return super.getPropertyValueTokens(file, name);
	}
	
	@Override
	protected void beforeCreate(File file) {
		super.beforeCreate(file);
		//exceptionUtils().exception(file.getBytes()==null, "file.bytes.required");
	}
	
	@Override
	protected File __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<File> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
    	set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME);
    	Integer index = listener.getSetListener().getIndex();
    	Package basePackage = PersistDataListener.Adapter.process(File.class, listener.getInstance().getCode(),PersistDataListener.BASE_PACKAGE, Package.getPackage(values[index++]));
		String relativePath = PersistDataListener.Adapter.process(File.class, listener.getInstance().getCode(), PersistDataListener.RELATIVE_PATH, values[index++]);
		String extension = FilenameUtils.getExtension(relativePath);
		//logMessageBuilder.addParameters("Code",code,"Name",name,"Package",basePackage,"Relative path",relativePath);
		//File file = null;
		if(StringUtils.isNotBlank(relativePath)){
			if(StringUtils.isBlank(listener.getInstance().getName()))
				listener.getInstance().setName(FilenameUtils.getName(relativePath));
			process(listener.getInstance(),getResourceAsBytes(basePackage,relativePath),listener.getInstance().getName()+Constant.CHARACTER_DOT+extension);
			//if(StringUtils.isNotBlank(listener.getInstance().getCode()))
			//	listener.getInstance().setCode(listener.getInstance().getCode());
		}
    	return listener.getInstance();
	}
	
	protected byte[] getResourceAsBytes(Package basePackage,String relativePath){
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Get", "resource as bytes");
		String path = "/"+StringUtils.replace(basePackage.getName(), Constant.CHARACTER_DOT.toString(), "/")+"/";
    	path += relativePath;
    	logMessageBuilder.addParameters("Path",path);
    	try {
    		return IOUtils.toByteArray(this.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			logMessageBuilder.addParameters("Exception",e.toString());
			return null;
		} finally {
			logTrace(logMessageBuilder);
		}
    }

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<File> findByFileIdentifiableGlobalIdentifierSearchCriteria(SearchCriteria searchCriteria) {
    	Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierDao.class).readByCriteria(searchCriteria);
		Collection<File> files = new ArrayList<>();
		for(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier : fileIdentifiableGlobalIdentifiers)
			files.add(fileIdentifiableGlobalIdentifier.getFile());
		return files;
	}
	
	@SuppressWarnings("unchecked")
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<File> findByRepresentationTypesByIdentifiables(Collection<FileRepresentationType> fileRepresentationTypes,Collection<? extends AbstractIdentifiable> identifiables) {
		FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
    	searchCriteria.addIdentifiablesGlobalIdentifiers((Collection<AbstractIdentifiable>) identifiables);
    	searchCriteria.addRepresentationTypes(fileRepresentationTypes);
		return findByFileIdentifiableGlobalIdentifierSearchCriteria(searchCriteria);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<File> findByRepresentationTypeByIdentifiables(FileRepresentationType fileRepresentationType,Collection<? extends AbstractIdentifiable> identifiables) {
		return findByRepresentationTypesByIdentifiables(Arrays.asList(fileRepresentationType),identifiables);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<File> findByRepresentationTypeByIdentifiable(FileRepresentationType fileRepresentationType,AbstractIdentifiable identifiable) {
		return findByRepresentationTypeByIdentifiables(fileRepresentationType, Arrays.asList(identifiable));
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
	public void process(File file, byte[] bytes, String name,Boolean reset) {
    	file.setBytes(bytes);
    	if(Boolean.TRUE.equals(reset) || StringUtils.isBlank(file.getExtension()))
    		file.setExtension(findExtension(name));
    	file.setExtension(StringUtils.lowerCase(file.getExtension()));//better use lower case because of mime type lookup
    	
    	if(Boolean.TRUE.equals(reset) || StringUtils.isBlank(file.getMime()))
    		file.setMime(findMime(file.getExtension()));
    	
    	if(Boolean.TRUE.equals(reset) || StringUtils.isBlank(file.getName())){
    		if(StringUtils.contains(name, NAME_EXTENSION_SEPARATOR))
    			file.setName(StringUtils.split(name,NAME_EXTENSION_SEPARATOR)[0]);
    		else
    			file.setName(name);
    	}
	}
    
    @Override
	public void process(File file, byte[] bytes, String name) {
    	process(file, bytes, name, Boolean.TRUE);
    }
    
    @Override
    public void writeTo(File file,java.io.File directory,String name) {
    	try {
    		// new java.io.File(directory,aReport.getFileName()+"."+aReport.getFileExtension())
			//FileUtils.writeByteArrayToFile(directory, file.getBytes());
    		if(StringUtils.isNotBlank(file.getExtension()))
    			name = name+NAME_EXTENSION_SEPARATOR+file.getExtension();
    		FileUtils.writeByteArrayToFile(new java.io.File(directory,name), IOUtils.toByteArray(findInputStream(file)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    private String getMime(String extension){
    	if(StringUtils.isBlank(extension))
    		return null;
        String mime = null;
        String fileName = FILE_DOT+extension;
        try {
            mime = Files.probeContentType(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	if(StringUtils.isBlank(mime) || mime.equalsIgnoreCase(Mime.APPLICATION_OCTET_STREAM))
    		mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
    	if(StringUtils.isBlank(mime) || mime.equalsIgnoreCase(Mime.APPLICATION_OCTET_STREAM))
    		mime = URLConnection.guessContentTypeFromName(fileName);
    	
    	return mime;
    }

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String findMime(String extension) {
    	String mime = getMime(extension);
    	
    	if(StringUtils.isBlank(mime) && FileExtension.JRXML.name().equalsIgnoreCase(extension))
    		mime = getMime(FileExtension.XML.name());
    	
    	if(StringUtils.isBlank(mime)){
    		logWarning("Cannot find mime for extension {}. {} will be used instead", extension,Mime.APPLICATION_OCTET_STREAM);
    		mime = Mime.APPLICATION_OCTET_STREAM;
    	}
    	return mime;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String findExtension(String name) {
    	int i = name.lastIndexOf(NAME_EXTENSION_SEPARATOR);
        if (i > 0)
            return name.substring(i+1);
        logWarning("Cannot find extension on file named {}", name);
    	return null;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InputStream findInputStream(File file) {
        if(file.getBytes()==null)
            if(file.getUri()==null)
                exceptionUtils().exception("exception.file.nocontentnouri");
            else
                if(FILE.equals(file.getUri().getScheme()))
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
    public byte[] findBytes(File file) {
    	if(file.getBytes()==null){
    		try {
				return IOUtils.toByteArray(findInputStream(file));
			} catch (IOException e) {
				throw new BusinessException(e.getMessage());
			}
    	}else
    		return file.getBytes();
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
		return file!=null && (StringUtils.startsWith(file.getMime(), Mime.IMAGE) || "jpg".equalsIgnoreCase(file.getExtension()));
	}
	
	@Override
	public Boolean isText(File file) {
		return file!=null && (StringUtils.startsWith(file.getMime(), Mime.TEXT) );
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<File> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter.Default<File> implements Listener,Serializable{
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;
				
				/**/
			
				
				public static class EnterpriseResourcePlanning extends Listener.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					/**/
					
					
				}
			}
		}
	}
}
