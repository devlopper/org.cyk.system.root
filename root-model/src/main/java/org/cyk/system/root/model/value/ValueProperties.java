package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.IntervalCollection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
public class ValueProperties extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne private IntervalCollection intervalCollection;
	@Enumerated(EnumType.ORDINAL) @Column(name="thetype") private ValueType type = ValueType.NUMBER;
	@Enumerated(EnumType.ORDINAL) @Column(name="theset") private ValueSet set = ValueSet.INTERVAL_VALUE;
	
	private Boolean nullable;
	private String nullString;
	private String nullAbbreviation;
	
	public ValueProperties setIntervalCollection(IntervalCollection intervalCollection){
		this.intervalCollection = intervalCollection;
		return this;
	}
	
	public ValueProperties setType(ValueType type){
		this.type = type;
		return this;
	}
	
	public ValueProperties setSet(ValueSet set){
		this.set = set;
		return this;
	}
	
	public ValueProperties setNullable(Boolean nullable){
		this.nullable = nullable;
		return this;
	}
	
	public ValueProperties setNullString(String nullString){
		this.nullString = nullString;
		return this;
	}
	
	public ValueProperties setNullAbbreviation(String nullAbbreviation){
		this.nullAbbreviation = nullAbbreviation;
		return this;
	}
	
	public ValueType getType(){
		return type == null ? ValueType.STRING : type;
	}
	
	public Boolean getNullable(){
		return nullable == null ? Boolean.TRUE : nullable;
	}
	
	public Boolean isBoolean(){
		return type == null ? Boolean.FALSE : ValueType.BOOLEAN.equals(type);
	}
	
	public Boolean isNumber(){
		return type == null ? Boolean.FALSE : ValueType.NUMBER.equals(type);
	}
	
	public Boolean isString(){
		return type == null ? Boolean.FALSE : ValueType.STRING.equals(type);
	}
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SET = "set";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_NULL_STRING = "nullString";
	public static final String FIELD_NULL_ABBREVIATION = "nullAbbreviation";
	
}