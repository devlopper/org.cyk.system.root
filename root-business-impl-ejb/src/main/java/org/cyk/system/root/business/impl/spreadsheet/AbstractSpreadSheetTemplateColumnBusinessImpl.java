package org.cyk.system.root.business.impl.spreadsheet;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.spreadsheet.AbstractSpreadSheetTemplateColumnBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateColumnDao;

public abstract class AbstractSpreadSheetTemplateColumnBusinessImpl<COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,DAO extends AbstractSpreadSheetTemplateColumnDao<COLUMN, ROW, TEMPLATE>> extends AbstractTypedBusinessService<COLUMN, DAO> implements AbstractSpreadSheetTemplateColumnBusiness<COLUMN, ROW, TEMPLATE>,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;
	
	public AbstractSpreadSheetTemplateColumnBusinessImpl(DAO dao) {
		super(dao);
	}

	@Override
	public Collection<COLUMN> findByTemplate(TEMPLATE template) {
		return dao.readByTemplate(template);
	}
	
}
