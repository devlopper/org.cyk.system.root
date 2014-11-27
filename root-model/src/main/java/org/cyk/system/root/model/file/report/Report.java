package org.cyk.system.root.model.file.report;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Report<MODEL> {

	private Class<MODEL> modelClass;
	
	private String title,subTitle;
	private Collection<Column> columns = new ArrayList<>();
	private Collection<MODEL> dataSource = new ArrayList<>();
	
	private String fileName;
	private String fileExtension;
	
	private byte[] bytes;
	
}
