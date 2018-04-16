package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.helper.NumberHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
@FieldOverrides(value={
		@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=MovementCollection.class)
})
public class Movement extends AbstractCollectionItem<MovementCollection> implements Serializable {	
	private static final long serialVersionUID = -4946585596435850782L;

	//@Transient private IdentifiablePeriod identifiablePeriod;
	@ManyToOne @JoinColumn(name=COLUMN_ACTION) private MovementAction action;	
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	@ManyToOne @JoinColumn(name=COLUMN_MODE) private MovementMode mode;
	
	@Transient private BigDecimal valueAbsolute;
	@Transient private Boolean valueSettableFromAbsolute;
	
	@Transient private BigDecimal previousCumul;
	private @Column(name=COLUMN_CUMUL,precision=20,scale=FLOAT_SCALE) BigDecimal cumul;
	
	/**
	 * The party to whom value goes or from whom value comes
	 */
	@Transient private Party senderOrReceiverParty;
	
	@Transient private MovementCollection destinationMovementCollection;
	@Transient private Movement destinationMovement;
	@Transient private Boolean actionIsIncrementation;
	
	@ManyToOne @JoinColumn(name=COLUMN_PARENT) private Movement parent;
	private Boolean parentActionIsOppositeOfChildAction;
	
	/**/
	
	@Override
	public Movement setCode(String code) {
		return (Movement) super.setCode(code);
	}
	
	@Override
	public Movement setBirthDate(Date date) {
		return (Movement) super.setBirthDate(date);
	}
	
	public Movement setBirthDateFromString(String date) {
		return (Movement) super.setBirthDateFromString(date);
	}
	
	@Override
	public Movement setCollection(MovementCollection collection) {
		return (Movement) super.setCollection(collection); 
	}
	
	@Override
	public Movement setCollectionFromCode(String code) {
		return (Movement) super.setCollectionFromCode(code);
	}
	
	public Movement setActionFromIncrementation(Boolean isIncrementation){
		if(isIncrementation == null)
			this.action = null;
		else if(this.collection == null)
			setActionIsIncrementation(isIncrementation);
		else if(isIncrementation)
			this.action=this.collection.getType().getIncrementAction();
		else
			this.action=this.collection.getType().getDecrementAction();
		return this;
	}
	
	public Movement setActionFromValue(){
		return setActionFromIncrementation(NumberHelper.getInstance().isGreaterThanZero(value));
	}
	
	public Movement setValueFromObject(Object value){
		this.value = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	public Movement setValueAbsoluteFromObject(Object value){
		this.valueAbsolute = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	@Override
	public Movement set__identifiablePeriod__(IdentifiablePeriod identifiablePeriod) {
		return (Movement) super.set__identifiablePeriod__(identifiablePeriod);
	}
	
	public Movement set__identifiablePeriod__fromCode(String code){
		return (Movement) super.set__identifiablePeriod__fromCode(code);
	}
	
	public Movement __set__(String collectionCode,Boolean isIncrementation,Object value,Boolean isBirthDateComputedByUser,String birthDate){
		setCollectionFromCode(collectionCode);
		setActionFromIncrementation(isIncrementation);
		setValueFromObject(value);
		__setBirthDateComputedByUser__(isBirthDateComputedByUser);
		setBirthDateFromString(birthDate);
		return this;
	}
	
	public Movement __set__(String collectionCode,Boolean isIncrementation,Object value){
		return __set__(collectionCode, isIncrementation, value, null, null);
	}
	
	public Movement setCollectionAndValueUsingOppositeAndAction(Movement movement) {
		return setCollection(movement.getCollection()).setValue(movement.getValue().negate()).setActionFromValue();
	}
	
	@Override
	public Movement __setBirthDateComputedByUser__(Boolean value) {
		return (Movement) super.__setBirthDateComputedByUser__(value);
	}
	
	@Override
	public String toString() {
		return (parent == null ? Constant.EMPTY_STRING : "parent=["+parent.toString()+"]")+super.toString()+"/"+value+":"+getBirthDate();
	}
	
	/**/
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_MODE = "mode";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_VALUE_ABSOLUTE = "valueAbsolute";
	public static final String FIELD_SENDER_OR_RECEIVER_PARTY = "senderOrReceiverParty";
	public static final String FIELD_CUMUL = "cumul";
	public static final String FIELD_PREVIOUS_CUMUL = "previousCumul";
	public static final String FIELD_DESTINATION_MOVEMENT_COLLECTION = "destinationMovementCollection";
	public static final String FIELD_DESTINATION_MOVEMENT = "destinationMovement";
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_PARENT_ACTION_IS_OPPOSITE_OF_CHILD_ACTION = "parentActionIsOppositeOfChildAction";
	
	public static final String COLUMN_MODE = FIELD_MODE;
	public static final String COLUMN_ACTION = FIELD_ACTION;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	public static final String COLUMN_CUMUL = FIELD_CUMUL;
	public static final String COLUMN_PARENT = FIELD_PARENT;
	
	/**/
	
	public static class Filter extends AbstractCollectionItem.Filter<Movement> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
    }
}
