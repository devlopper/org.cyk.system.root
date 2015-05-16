package org.cyk.system.root.business.api.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;

import ar.com.fdvs.dj.domain.Style;

public interface JasperReportBusiness extends ReportBusiness<Style> {

	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(JasperReportBasedOnDynamicBuilderParameters<MODEL> parameters);
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public static class JasperReportBasedOnDynamicBuilderParameters<MODEL> extends ReportBasedOnDynamicBuilderParameters<MODEL> implements Serializable{

		private static final long serialVersionUID = -8188598097968268568L;
		
		private Collection<JasperReportBasedOnDynamicBuilderListener> jasperReportBasedOnDynamicBuilderListeners = new ArrayList<>();
		
	}
	
}
