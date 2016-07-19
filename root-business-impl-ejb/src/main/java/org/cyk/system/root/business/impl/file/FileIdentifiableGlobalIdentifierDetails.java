package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class FileIdentifiableGlobalIdentifierDetails extends AbstractOutputDetails<FileIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String file;
	
	public FileIdentifiableGlobalIdentifierDetails(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier) {
		super(fileIdentifiableGlobalIdentifier);
		file = formatUsingBusiness(fileIdentifiableGlobalIdentifier.getFile());
	}
	
}