package org.cyk.system.root.model.transfer;

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

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class TransferAcknowledgement extends AbstractIdentifiable implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_TRANSFER) @NotNull private Transfer transfer;
	@ManyToOne @JoinColumn(name=COLUMN_ITEMS) @NotNull private TransferItemCollection items;
	
	/**/
	
	public static final String FIELD_TRANSFER = "transfer";
	public static final String FIELD_ITEMS = "items";
	
	public static final String COLUMN_TRANSFER = FIELD_TRANSFER;
	public static final String COLUMN_ITEMS = FIELD_ITEMS;
}

