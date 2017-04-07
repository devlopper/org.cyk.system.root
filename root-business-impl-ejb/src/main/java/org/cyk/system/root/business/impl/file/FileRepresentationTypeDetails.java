package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.FileRepresentationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FileRepresentationTypeDetails extends AbstractOutputDetails<FileRepresentationType> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;

	public FileRepresentationTypeDetails(FileRepresentationType source) {
		super(source);
	}
	
}