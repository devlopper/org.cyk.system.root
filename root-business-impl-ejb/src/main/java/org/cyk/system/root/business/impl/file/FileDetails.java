package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDetails extends AbstractOutputDetails<File> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String extension,uniformResourceLocator,mime,generator,sender,contentWriter;
	
	public FileDetails() {
		this(null);
	}
	
	public FileDetails(File file) {
		super(file);
	}
	
	@Override
	public void setMaster(File master) {
		super.setMaster(master);
		if(master==null){
			
		}else{
			uniformResourceLocator = master.getUniformResourceIdentifier() == null ? Constant.EMPTY_STRING : master.getUniformResourceIdentifier();
			extension = master.getExtension();
			mime = master.getMime();	
			generator = master.getGenerator();
			sender = master.getSender();
			contentWriter = master.getContentWriter();
		}
	}
	
	/**/
	
	public static final String FIELD_EXTENSION = "extension";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	public static final String FIELD_MIME = "mime";
}