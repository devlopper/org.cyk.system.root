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
public class MovementCollectionValuesTransferAcknowledgement extends AbstractIdentifiable implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_TRANSFER) @NotNull private MovementCollectionValuesTransfer transfer;
	@ManyToOne @JoinColumn(name=COLUMN_ITEMS) @NotNull private MovementCollectionValuesTransferItemCollection items;
	
	/**/
	
	@Override
	public MovementCollectionValuesTransferAcknowledgement addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (MovementCollectionValuesTransferAcknowledgement) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	public MovementCollectionValuesTransferAcknowledgement setTransferFromCode(String code){
		this.transfer = getFromCode(MovementCollectionValuesTransfer.class, code);
		return this;
	}
	
	/**/
	
	public static final String FIELD_TRANSFER = "transfer";
	public static final String FIELD_ITEMS = "items";
	
	public static final String COLUMN_TRANSFER = FIELD_TRANSFER;
	public static final String COLUMN_ITEMS = FIELD_ITEMS;
	
	
}

