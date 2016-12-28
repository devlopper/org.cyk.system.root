package org.cyk.system.root.model.value;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.IntervalCollection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class Value extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	/*
	 * The solution to support value type is to defined each possible usable type of value.
	 * Depending on the business anyone of those value fields can be filled.
	 * A value field will be named as xxxValue where xxx is the type of value
	 */
	
	@ManyToOne private ValueProperties properties;
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=BooleanValue.FIELD_USER,column=@Column(name="boolean_value_user"))
			,@AttributeOverride(name=BooleanValue.FIELD_SYSTEM,column=@Column(name="boolean_value_system"))
			,@AttributeOverride(name=BooleanValue.FIELD_PREFERRED_PROPERTY,column=@Column(name="boolean_value_preferred_property"))
	})
	private BooleanValue booleanValue = new BooleanValue();
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=BigDecimalValue.FIELD_USER,column=@Column(name="number_value_user"))
			,@AttributeOverride(name=BigDecimalValue.FIELD_SYSTEM,column=@Column(name="number_value_system"))
			,@AttributeOverride(name=BigDecimalValue.FIELD_GAP,column=@Column(name="number_value_gap"))
			,@AttributeOverride(name=BigDecimalValue.FIELD_PREFERRED_PROPERTY,column=@Column(name="number_value_preferred_property"))
	})
	private BigDecimalValue numberValue = new BigDecimalValue();
	
	@Embedded
	@AttributeOverrides(value={
			@AttributeOverride(name=StringValue.FIELD_USER,column=@Column(name="string_value_user"))
			,@AttributeOverride(name=StringValue.FIELD_SYSTEM,column=@Column(name="string_value_system"))
			,@AttributeOverride(name=StringValue.FIELD_PREFERRED_PROPERTY,column=@Column(name="string_value_preferred_property"))
	})
	private StringValue stringValue = new StringValue();
	
	public Value(ValueProperties properties) {
		super();
		this.properties = properties;
	}
	
	public void set(Object value) {
		switch(getType()){
		case BOOLEAN:
			getBooleanValue().set(value == null ? null :  (Boolean) value);
			break;
		case NUMBER:
			getNumberValue().set(value == null ? null : new BigDecimal(value.toString()));
			break;
		case STRING:
			getStringValue().set(value == null ? null : value.toString());
			break;
		}
	}
	
	public Object get() {
		switch(getType()){
		case BOOLEAN:
			return getBooleanValue().get();
		case NUMBER:
			return getNumberValue().get();
		case STRING:
			return getStringValue().get();
		}
		return null;
	}
	
	public Measure getMeasure(){
		return properties == null ? null : properties.getMeasure();
	}
	
	public ValueType getType(){
		return properties == null ? ValueType.DEFAULT : properties.getType();
	}
	
	public ValueSet getSet(){
		return properties == null ? ValueSet.DEFAULT : properties.getSet();
	}

	public Boolean getNullable(){
		return properties == null ? Boolean.FALSE : properties.getNullable();
	}
	
	public NullString getNullString(){
		return properties == null ? null : properties.getNullString();
	}
	
	public IntervalCollection getIntervalCollection(){
		return properties == null ? null : properties.getIntervalCollection();
	}
	
	public BooleanValue getBooleanValue(){
		if(booleanValue==null)
			booleanValue = new BooleanValue();
		return booleanValue;
	}
	
	public StringValue getStringValue(){
		if(stringValue==null)
			stringValue = new StringValue();
		return stringValue;
	}
	
	public BigDecimalValue getNumberValue(){
		if(numberValue==null)
			numberValue = new BigDecimalValue();
		return numberValue;
	}
	
	public Boolean isDerived(){
		return properties == null ? Boolean.FALSE : Boolean.TRUE.equals(properties.getDerived());
	}
	
	@Override
	public String toString() {
		ValueType type = getType();
		if(ValueType.BOOLEAN.equals(type))
			return getBooleanValue().get() == null ? null : getBooleanValue().get().toString();
		else if(ValueType.NUMBER.equals(type))
			return getNumberValue().get() == null ? null : getNumberValue().get().toString();
		else if(ValueType.STRING.equals(type))
			return getStringValue().get() == null ? null : getStringValue().get().toString();
		return null;
	}

	
}