package org.cyk.system.root.business.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;

public interface AbstractSpreadSheetTemplateColumnBusiness<COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>> extends TypedBusiness<COLUMN> {

	Collection<COLUMN> findByTemplate(TEMPLATE template);

}
