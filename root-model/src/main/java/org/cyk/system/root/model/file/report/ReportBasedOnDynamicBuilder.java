package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportBasedOnDynamicBuilder<MODEL> extends AbstractReport<MODEL> implements Serializable {
	
	private static final long serialVersionUID = -6269733939974607163L;
	
	protected String title,subTitle,companyName,companyContacts,companyLogoPath,creationDate,createdBy;
	protected Collection<Column> columns = new ArrayList<>();
	
}
