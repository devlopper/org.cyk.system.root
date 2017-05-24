package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<FileIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	//@Input @InputFile(thumbnail=@Thumbnail(renderStrategy=Thumbnail.RenderStrategy.NEVER),showLink=true) 
	//private File file;
	
	@Input @InputText
	private FieldValue file;
	
	public FileIdentifiableGlobalIdentifierDetails(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		super(fileIdentifiableGlobalIdentifier);
		
	}
	
	@Override
	public void setMaster(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		super.setMaster(fileIdentifiableGlobalIdentifier);
		if(fileIdentifiableGlobalIdentifier==null){
			
		}else{
			file = new FieldValue(fileIdentifiableGlobalIdentifier.getFile());
		}
	}
	
	/**/
	
	public static final String FIELD_FILE = "file";
	
}