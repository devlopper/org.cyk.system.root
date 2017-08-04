package org.cyk.system.root.persistence.impl.spreadsheet;

import java.util.Collection;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateColumn;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplateRow;
import org.cyk.system.root.persistence.api.spreadsheet.AbstractSpreadSheetTemplateColumnDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractSpreadSheetTemplateColumnDaoImpl<COLUMN extends AbstractSpreadSheetTemplateColumn<TEMPLATE>,ROW extends AbstractSpreadSheetTemplateRow<TEMPLATE>,TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>> extends AbstractTypedDao<COLUMN> implements AbstractSpreadSheetTemplateColumnDao<COLUMN,ROW, TEMPLATE> {

	private static final long serialVersionUID = 6920278182318788380L;

	private String readByTemplate;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByTemplate, _select().where(AbstractSpreadSheetTemplateColumn.FIELD_TEMPLATE));
	}
	
	@Override
	public Collection<COLUMN> readByTemplate(TEMPLATE template) {
		return namedQuery(readByTemplate).parameter(AbstractSpreadSheetTemplateColumn.FIELD_TEMPLATE, template).resultMany();
	}

	
	
}
