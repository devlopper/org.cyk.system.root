package org.cyk.system.root.model.file.report;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportTable<MODEL> extends AbstractReport<MODEL> {
	
	private String title,subTitle;
	private Collection<Column> columns = new ArrayList<>();
	
}
