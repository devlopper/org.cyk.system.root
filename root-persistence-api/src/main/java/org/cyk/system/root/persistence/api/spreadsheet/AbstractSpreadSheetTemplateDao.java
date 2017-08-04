package org.cyk.system.root.persistence.api.spreadsheet;

import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSpreadSheetTemplateDao<TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW,COLUMN> extends TypedDao<TEMPLATE> {

}
