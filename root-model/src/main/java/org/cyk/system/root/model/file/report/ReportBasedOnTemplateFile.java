package org.cyk.system.root.model.file.report;

import org.cyk.system.root.model.file.File;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportBasedOnTemplateFile<MODEL> extends AbstractReport<MODEL> {
	
	private File templateFile;
	
}
