package org.cyk.system.root.model.userinterface.spreadsheet;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractSpreadSheetTemplateDimension<TEMPLATE> extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull
	protected TEMPLATE template;
	
	@Column(name="dim_index",nullable=false)
	protected Integer index;
	
}

