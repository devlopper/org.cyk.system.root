package org.cyk.system.root.persistence.impl.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetCellDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractSpreadSheetCellDaoImpl<CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,VALUE,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>> extends AbstractTypedDao<CELL> implements AbstractSpreadSheetCellDao<CELL,ROW,COLUMN,VALUE,TEMPLATE,SPREADSHEET> {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readBySpreadSheet;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readBySpreadSheet, _select().where(AbstractSpreadSheetCell.FIELD_SPREADSHEET));
	}
	
	@Override
	public Collection<CELL> readBySpreadSheet(SPREADSHEET spreadsheet) {
		return namedQuery(readBySpreadSheet).parameter(AbstractSpreadSheetCell.FIELD_SPREADSHEET, spreadsheet).resultMany();
	}
	
}
