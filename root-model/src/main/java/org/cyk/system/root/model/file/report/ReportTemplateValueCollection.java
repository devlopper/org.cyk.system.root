package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class ReportTemplateValueCollection extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -6245201590820068337L;

	@ManyToOne @NotNull private ReportTemplate reportTemplate;
	@ManyToOne @NotNull private ValueCollection valueCollection;
	
	public static final String FIELD_REPORT_TEMPLATE = "reportTemplate";
	public static final String FIELD_VALUE_COLLECTION = "valueCollection";
	
}
