package org.cyk.system.root.persistence.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSpreadSheetTemplateColumnDao<COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>> extends TypedDao<COLUMN> {

	Collection<COLUMN> readByTemplate(TEMPLATE template);

}
