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
	
	@Input @InputText private String extension,uniformResourceLocator,mime;
	
	public FileDetails(File file) {
		super(file);
		uniformResourceLocator = file.getUri() == null ? Constant.EMPTY_STRING : file.getUri().toString();
		extension = file.getExtension();
		mime = file.getMime();
	}
	
	/**/
	
	public static final String FIELD_EXTENSION = "extension";
	public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	public static final String FIELD_MIME = "mime";
}