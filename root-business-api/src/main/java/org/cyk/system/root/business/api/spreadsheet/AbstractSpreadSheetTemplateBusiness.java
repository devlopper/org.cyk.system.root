package org.cyk.system.root.business.api.spreadsheet;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.spreadsheet.AbstractSpreadSheetTemplate;

public interface AbstractSpreadSheetTemplateBusiness<TEMPLATE extends AbstractSpreadSheetTemplate<ROW, COLUMN>,ROW,COLUMN> extends TypedBusiness<TEMPLATE> {

}
