package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter
public class FileContentDetails extends AbstractOutputDetails<File> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputTextarea private String content;
	
	public FileContentDetails(File file) {
		super(file);
		if(inject(FileBusiness.class).isText(file))
			try {
				content = new String(file.getBytes(),Constant.ENCODING_UTF8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	}
	
	public static final String LABEL_IDENTIFIER = "content";
}