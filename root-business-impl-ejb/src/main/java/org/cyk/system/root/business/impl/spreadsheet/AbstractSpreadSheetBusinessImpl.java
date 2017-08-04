package org.cyk.system.root.business.impl.spreadsheet;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.spreadsheet.AbstractSpreadSheetBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheet;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetCell;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.model.spreadsheet.SpreadSheetSearchCriteria;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetDao;

public abstract class AbstractSpreadSheetBusinessImpl<SPREADSHEET extends AbstractSpreadSheet<TEMPLATE,ROW, COLUMN,CELL>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,CELL extends AbstractSpreadSheetCell<SPREADSHEET, ROW, COLUMN, VALUE>,VALUE,SEARCH_CRITERIA extends SpreadSheetSearchCriteria,DAO extends AbstractSpreadSheetDao<SPREADSHEET, TEMPLATE, ROW, COLUMN, CELL,VALUE, SEARCH_CRITERIA>> extends AbstractTypedBusinessService<SPREADSHEET, DAO> implements AbstractSpreadSheetBusiness<SPREADSHEET, TEMPLATE, ROW, COLUMN, CELL,VALUE, SEARCH_CRITERIA>,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;
	
	public AbstractSpreadSheetBusinessImpl(DAO dao) {
		super(dao);
	}

	@Override
	public Collection<SPREADSHEET> findByCriteria(SEARCH_CRITERIA searchCriteria) {
		return dao.readByCriteria(searchCriteria);
	}

	@Override
	public Long countByCriteria(SEARCH_CRITERIA searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}

}
