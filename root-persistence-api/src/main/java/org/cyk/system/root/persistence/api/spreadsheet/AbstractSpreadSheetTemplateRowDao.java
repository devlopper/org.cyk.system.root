package org.cyk.system.root.persistence.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSpreadSheetTemplateRowDao<ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>> extends TypedDao<ROW> {

	Collection<ROW> readByTemplate(TEMPLATE template);

}
