package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class MovementCollection extends AbstractCollection<Movement> implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;
	
	@Column(name=COLUMN_INITIAL_VALUE,precision=20,scale=FLOAT_SCALE) private BigDecimal initialValue;
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE) private BigDecimal value;
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) private MovementCollectionType type;
	@ManyToOne @JoinColumn(name=COLUMN_BUFFER) private MovementCollection buffer;
	
	@Transient private Boolean isCreateBufferAutomatically;
	
	@Override
	public MovementCollection setCode(String code) {
		return (MovementCollection) super.setCode(code);
	}
	
	public MovementCollection setTypeFromCode(String code){
		this.type = getFromCode(MovementCollectionType.class, code);
		return this;
	}
	
	public MovementCollection setBufferFromCode(String code){
		this.buffer = getFromCode(MovementCollection.class, code);
		return this;
	}
	
	public MovementCollection setInitialValueFromObject(Object value){
		this.initialValue = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	public MovementCollection setValueFromObject(Object value){
		this.value = getNumberFromObject(BigDecimal.class, value);
		return this;
	}
	
	public MovementCollection setIsCreateBufferAutomatically(Boolean isCreateBufferAutomatically){
		this.isCreateBufferAutomatically = isCreateBufferAutomatically;
		if(Boolean.TRUE.equals(this.isCreateBufferAutomatically))
			addCascadeOperationToMasterFieldNames(FIELD_BUFFER);
		else
			CollectionHelper.getInstance().removeElement(cascadeOperationToMasterFieldNames, FIELD_BUFFER);
		return this;
	}
	
	@Override
	public <T> MovementCollection __setJoinedIdentifiablesFromCodes__(Class<T> aClass, String... codes) {
		super.__setJoinedIdentifiablesFromCodes__(aClass, codes);
		return this;
	}
	
	@Override
	public MovementCollection setItemAggregationApplied(Boolean itemAggregationApplied) {
		return (MovementCollection) super.setItemAggregationApplied(itemAggregationApplied);
	}
	
	public static final String FIELD_INITIAL_VALUE = "initialValue";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_BUFFER = "buffer";
	public static final String FIELD_IS_CREATE_BUFFER_AUTOMATICALLY = "isCreateBufferAutomatically";
	
	public static final String COLUMN_INITIAL_VALUE = FIELD_INITIAL_VALUE;
	public static final String COLUMN_VALUE = COLUMN_NAME_UNKEYWORD+FIELD_VALUE;
	public static final String COLUMN_TYPE = COLUMN_NAME_UNKEYWORD+FIELD_TYPE;
	public static final String COLUMN_BUFFER = COLUMN_NAME_UNKEYWORD+FIELD_BUFFER;
	
	public static final String VARIABLE_NAME = ClassHelper.getInstance().getVariableName(MovementCollection.class);
	
	/**/
	
	public static class Filter extends AbstractCollection.Filter<MovementCollection> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
    }
}
