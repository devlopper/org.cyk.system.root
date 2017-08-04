package org.cyk.system.root.business.impl.spreadsheet;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.spreadsheet.AbstractSpreadSheetTemplateRowBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateRowDao;

public abstract class AbstractSpreadSheetTemplateRowBusinessImpl<ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,DAO extends AbstractSpreadSheetTemplateRowDao<ROW, COLUMN, TEMPLATE>> extends AbstractTypedBusinessService<ROW, DAO> implements AbstractSpreadSheetTemplateRowBusiness<ROW, COLUMN, TEMPLATE>,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;
	
	public AbstractSpreadSheetTemplateRowBusinessImpl(DAO dao) {
		super(dao);
	}

	@Override
	public Collection<ROW> findByTemplate(TEMPLATE template) {
		return dao.readByTemplate(template);
	}
	
}
