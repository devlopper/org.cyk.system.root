package org.cyk.system.root.business.api.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;

public interface AbstractSpreadSheetCellBusiness<CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,VALUE,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>> extends TypedBusiness<CELL> {

	Collection<CELL> findBySpreadSheet(SPREADSHEET spreadsheet);

}
