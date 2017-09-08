package org.cyk.system.root.model.spreadsheet;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor /*@AllArgsConstructor*/ @MappedSuperclass
public abstract class AbstractSpreadSheetTemplateColumn<TEMPLATE> extends AbstractSpreadSheetTemplateDimension<TEMPLATE> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	public AbstractSpreadSheetTemplateColumn(TEMPLATE template) {
		super(template);
	}

}

