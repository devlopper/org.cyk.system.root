package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Thumbnail;

@Getter @Setter
public class FileIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<FileIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String description;
	@Input @InputFile(thumbnail=@Thumbnail(renderStrategy=Thumbnail.RenderStrategy.NEVER),showLink=true) 
	private File file;
	
	public FileIdentifiableGlobalIdentifierDetails(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		super(fileIdentifiableGlobalIdentifier);
		description = fileIdentifiableGlobalIdentifier.getFile().getDescription();
		file = fileIdentifiableGlobalIdentifier.getFile();
	}
	
}