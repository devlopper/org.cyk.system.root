package org.cyk.system.root.persistence.impl.spreadsheet;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class  AbstractSpreadSheetTemplateDaoImpl<TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW,COLUMN> extends AbstractTypedDao<TEMPLATE> implements AbstractSpreadSheetTemplateDao<TEMPLATE,ROW,COLUMN> {

	private static final long serialVersionUID = 6920278182318788380L;

	
	
}
