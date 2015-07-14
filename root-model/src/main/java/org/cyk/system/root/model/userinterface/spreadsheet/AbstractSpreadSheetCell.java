package org.cyk.system.root.model.userinterface.spreadsheet;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractSpreadSheetCell<SPREADSHEET,ROW,COLUMN,VALUE> extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @NotNull
	protected SPREADSHEET spreadSheet;
	
	@ManyToOne @NotNull
	protected ROW row;
	
	@ManyToOne @NotNull
	protected COLUMN column;
	
	protected VALUE value;

	public AbstractSpreadSheetCell(SPREADSHEET spreadSheet) {
		super();
		this.spreadSheet = spreadSheet;
	}

	public AbstractSpreadSheetCell(ROW row, COLUMN column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	

}

