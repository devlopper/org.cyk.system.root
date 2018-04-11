package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementsTransferAcknowledgement extends AbstractIdentifiable implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_MOVEMENTS_TRANSFER) @NotNull private MovementsTransfer movementsTransfer;
	@ManyToOne @JoinColumn(name=COLUMN_ITEMS) @NotNull private MovementsTransferItemCollection items;
	
	/**/
	
	@Override
	public MovementsTransferAcknowledgement addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (MovementsTransferAcknowledgement) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	public MovementsTransferAcknowledgement setMovementsTransferFromCode(String code){
		this.movementsTransfer = getFromCode(MovementsTransfer.class, code);
		return this;
	}
	
	/**/
	
	public static final String FIELD_MOVEMENTS_TRANSFER = "movementsTransfer";
	public static final String FIELD_ITEMS = "items";
	
	public static final String COLUMN_MOVEMENTS_TRANSFER = FIELD_MOVEMENTS_TRANSFER;
	public static final String COLUMN_ITEMS = FIELD_ITEMS;
}

