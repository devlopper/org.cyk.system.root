package org.cyk.system.root.business.impl.file.report.jasper;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ar.com.fdvs.dj.domain.Style;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReportColumnJasper implements Serializable {

	private static final long serialVersionUID = -6465141536112290681L;

	private String fieldName;
	private Class<?> type;
	private String title;
	private Integer width;
	
	private Style headerStyle, detailStyle;
	
}
