package org.cyk.system.root.business.impl.spreadsheet;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.spreadsheet.AbstractSpreadSheetCellBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetCellDao;

public abstract class AbstractSpreadSheetCellBusinessImpl<CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,VALUE,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>,DAO extends AbstractSpreadSheetCellDao<CELL, ROW, COLUMN,VALUE, TEMPLATE, SPREADSHEET>> extends AbstractTypedBusinessService<CELL, DAO> implements AbstractSpreadSheetCellBusiness<CELL, ROW, COLUMN,VALUE, TEMPLATE, SPREADSHEET>,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;
	
	public AbstractSpreadSheetCellBusinessImpl(DAO dao) {
		super(dao);
	}

	@Override
	public Collection<CELL> findBySpreadSheet(SPREADSHEET production) {
		return dao.readBySpreadSheet(production);
	}

	
	
}
