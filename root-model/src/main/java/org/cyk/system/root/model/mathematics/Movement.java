package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
@FieldOverrides(value={
		@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=MovementCollection.class)
		//,@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=MovementCollection.class)
})
public class Movement extends AbstractCollectionItem<MovementCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_ACTION) private MovementAction action;	
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	@ManyToOne @JoinColumn(name=COLUMN_MODE) private MovementMode mode;
	
	@Transient private BigDecimal valueAbsolute;
	@Transient private Boolean valueSettableFromAbsolute;
	
	@Transient private BigDecimal previousCumul;
	private @Column(name=COLUMN_CUMUL,precision=20,scale=FLOAT_SCALE) BigDecimal cumul;
	
	/**
	 * The person to whom value goes or from whom value comes
	 */
	@ManyToOne @JoinColumn(name=COLUMN_SENDER_OR_RECEIVER_PERSON) private Person senderOrReceiverPerson;
	
	private String senderOrReceiverPersonAsString;
	
	@Transient private MovementCollection destinationMovementCollection;
	@Transient private Movement destinationMovement;
	
	/**/
	
	@Override
	public String toString() {
		return super.toString()+"/"+value;
	}
	
	/**/
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_MODE = "mode";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_VALUE_ABSOLUTE = "valueAbsolute";
	public static final String FIELD_SENDER_OR_RECEIVER_PERSON = "senderOrReceiverPerson";
	public static final String FIELD_SENDER_OR_RECEIVER_PERSON_AS_STRING = "senderOrReceiverPersonAsString";
	public static final String FIELD_CUMUL = "cumul";
	public static final String FIELD_PREVIOUS_CUMUL = "previousCumul";
	public static final String FIELD_DESTINATION_MOVEMENT_COLLECTION = "destinationMovementCollection";
	public static final String FIELD_DESTINATION_MOVEMENT = "destinationMovement";
	
	public static final String COLUMN_MODE = FIELD_MODE;
	public static final String COLUMN_ACTION = FIELD_ACTION;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	public static final String COLUMN_CUMUL = FIELD_CUMUL;
	public static final String COLUMN_SENDER_OR_RECEIVER_PERSON = FIELD_SENDER_OR_RECEIVER_PERSON;
	
	/**/
	
	public static class Filter extends AbstractCollectionItem.Filter<Movement> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
    }
}
