package org.cyk.system.root.business.api.file.report;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;

public interface RootReportProducer {

	<REPORT extends AbstractReportTemplateFile<REPORT>> REPORT produce(Class<REPORT> reportClass,AbstractIdentifiable identifiable);

	/**/
	
	
}
