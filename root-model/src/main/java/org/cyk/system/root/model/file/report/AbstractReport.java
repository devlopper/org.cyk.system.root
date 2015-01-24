package org.cyk.system.root.model.file.report;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractReport<MODEL> {

	//inputs
	protected Class<MODEL> modelClass;
	
	protected Collection<MODEL> dataSource = new ArrayList<>();
	
	protected String fileName;
	protected String fileExtension;
	
	//outputs
	protected byte[] bytes;
	
}
