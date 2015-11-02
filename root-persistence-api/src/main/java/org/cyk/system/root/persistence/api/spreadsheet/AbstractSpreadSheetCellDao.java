package org.cyk.system.root.persistence.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSpreadSheetCellDao<CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, CELL>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>> extends TypedDao<CELL> {

	Collection<CELL> readBySpreadSheet(SPREADSHEET spreadsheet);

}
