package org.cyk.system.root.model.userinterface.spreadsheet;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.userinterface.InputName;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractSpreadSheetTemplateColumn<TEMPLATE> extends AbstractSpreadSheetTemplateDimension<TEMPLATE> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@ManyToOne @NotNull
	protected InputName inputName;

	
}

