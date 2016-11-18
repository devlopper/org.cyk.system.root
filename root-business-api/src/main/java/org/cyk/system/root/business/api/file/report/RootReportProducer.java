package org.cyk.system.root.business.api.file.report;

import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;

public interface RootReportProducer {

	Class<?> getReportTemplateFileClass(AbstractIdentifiable identifiable,String reportTemplateCode);
	
	<REPORT extends AbstractReportTemplateFile<REPORT>> REPORT produce(Class<REPORT> reportClass
			,CreateReportFileArguments<?> createReportFileArguments);
	
	//<REPORT extends AbstractReportTemplateFile<REPORT>> REPORT produce(Class<REPORT> reportClass,AbstractIdentifiable identifiable);

	//<REPORT extends AbstractReportTemplateFile<REPORT>> ProduceArguments<REPORT> getDefaultProduceArguments(Class<REPORT> reportClass,AbstractIdentifiable identifiable);
	
	/**/
	/*
	@Getter @Setter @NoArgsConstructor
	public static class ProduceArguments<REPORT extends AbstractReportTemplateFile<REPORT>> implements Serializable{
		private static final long serialVersionUID = 1446488904290411180L;
		
		public ProduceArguments(ProduceArguments<REPORT> parameters){
		
		}
	}*/
}
