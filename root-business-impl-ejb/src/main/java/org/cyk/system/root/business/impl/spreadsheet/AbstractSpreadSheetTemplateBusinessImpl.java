package org.cyk.system.root.business.impl.spreadsheet;

import java.io.Serializable;

import org.cyk.system.root.business.api.spreadsheet.AbstractSpreadSheetTemplateBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateDao;

public abstract class AbstractSpreadSheetTemplateBusinessImpl<TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW,COLUMN,DAO extends AbstractSpreadSheetTemplateDao<TEMPLATE, ROW, COLUMN>>  extends AbstractTypedBusinessService<TEMPLATE, DAO> implements AbstractSpreadSheetTemplateBusiness<TEMPLATE, ROW, COLUMN>,Serializable {

	private static final long serialVersionUID = -7830673760640348717L;
	
	public AbstractSpreadSheetTemplateBusinessImpl(DAO dao) {
		super(dao);
	}
	
}
