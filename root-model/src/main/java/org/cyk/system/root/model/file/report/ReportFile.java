package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ReportFile extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	@ManyToOne @NotNull private ReportTemplate reportTemplate;
	
	@ManyToOne @NotNull private File file;

	public ReportFile(ReportTemplate reportTemplate, File file) {
		super();
		this.reportTemplate = reportTemplate;
		this.file = file;
	}
	
	public static final String FIELD_REPORT_TEMPLATE = "reportTemplate";
	public static final String FIELD_FILE = "file";
}
