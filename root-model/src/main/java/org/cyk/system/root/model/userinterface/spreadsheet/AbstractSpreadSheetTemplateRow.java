package org.cyk.system.root.model.userinterface.spreadsheet;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter /*@NoArgsConstructor @AllArgsConstructor*/ @MappedSuperclass
public abstract class AbstractSpreadSheetTemplateRow<TEMPLATE> extends AbstractSpreadSheetTemplateDimension<TEMPLATE> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
}

