package org.cyk.system.root.model.file.report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface ReportBasedOnDynamicBuilderListener {

	Collection<ReportBasedOnDynamicBuilderListener> GLOBALS = new ArrayList<>();
	Collection<ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable,Object>> IDENTIFIABLE_CONFIGURATIONS = new ArrayList<>();
	
	String COLUMN_HEADER_BACKGROUND_COLOR = "CAD9C7";
	String COLUMN_DETAIL_BACKGROUND_COLOR = "FFFFFF";
	String COLUMN_FOOTER_BACKGROUND_COLOR = "CAD9C7";
	
	Boolean ignoreField(Field field);
	
	Set<String> fieldToIgnore();
	
	void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters);
	
	void column(ReportBasedOnDynamicBuilder<?> report,Column column);
	
	/**/
	
}