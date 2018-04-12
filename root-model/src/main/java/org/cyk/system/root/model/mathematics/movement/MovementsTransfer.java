package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * move from one place to another.
 * @author Christian
 *
 */
@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementsTransfer extends AbstractIdentifiable implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_TYPE) private MovementsTransferType type;
	@ManyToOne @JoinColumn(name=COLUMN_ITEMS) @NotNull private MovementsTransferItemCollection items;
	
	@Transient private Party sender;
	@Transient private Party receiver;
	
	/**/
	
	public MovementsTransfer setSenderFromCode(String code){
		this.sender = getFromCode(Party.class, code);
		return this;
	}
	
	public MovementsTransfer setReceiverFromCode(String code){
		this.receiver = getFromCode(Party.class, code);
		return this;
	}
	
	@Override
	public MovementsTransfer addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (MovementsTransfer) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ITEMS = "items";
	public static final String FIELD_SENDER = "sender";
	public static final String FIELD_RECEIVER = "receiver";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
	public static final String COLUMN_ITEMS = FIELD_ITEMS;
}

