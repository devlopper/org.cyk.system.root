package org.cyk.system.root.business.api.globalidentification;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;

public interface GlobalIdentifierBusiness {

	Boolean isCreatable(Class<? extends AbstractIdentifiable> aClass);
	Boolean isReadable(AbstractIdentifiable identifiable);
	Boolean isUpdatable(AbstractIdentifiable identifiable);
	Boolean isDeletable(AbstractIdentifiable identifiable);
	
	GlobalIdentifier create(GlobalIdentifier globalIdentifier);
	GlobalIdentifier update(GlobalIdentifier globalIdentifier);
	GlobalIdentifier delete(GlobalIdentifier globalIdentifier);
	
	Collection<GlobalIdentifier> findAll();
	
	Collection<GlobalIdentifier> instanciateMany(MicrosoftExcelHelper.Workbook.Sheet sheet,InstanceHelper.Builder.OneDimensionArray<GlobalIdentifier> instanceBuilder);
}
