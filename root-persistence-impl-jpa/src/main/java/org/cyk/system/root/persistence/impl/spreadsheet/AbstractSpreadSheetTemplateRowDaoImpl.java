package org.cyk.system.root.persistence.impl.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateRowDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractSpreadSheetTemplateRowDaoImpl<ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>> extends AbstractTypedDao<ROW> implements AbstractSpreadSheetTemplateRowDao<ROW, COLUMN, TEMPLATE> {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readByTemplate;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByTemplate, _select().where(AbstractSpreadSheetTemplateRow.FIELD_TEMPLATE));
	}
	
	@Override
	public Collection<ROW> readByTemplate(TEMPLATE template) {
		return namedQuery(readByTemplate).parameter(AbstractSpreadSheetTemplateRow.FIELD_TEMPLATE, template).resultMany();
	}

	
	
}
