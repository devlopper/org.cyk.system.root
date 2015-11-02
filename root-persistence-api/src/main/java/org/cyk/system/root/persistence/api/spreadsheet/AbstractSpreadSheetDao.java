package org.cyk.system.root.persistence.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.model.spreadsheet.SpreadSheetSearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSpreadSheetDao<SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,VALUE,SEARCH_CRITERIA extends SpreadSheetSearchCriteria> extends TypedDao<SPREADSHEET> {

	Collection<SPREADSHEET> readByCriteria(SEARCH_CRITERIA searchCriteria);
	
	Long countByCriteria(SEARCH_CRITERIA searchCriteria);
	
}
