package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS)
public class ValueProperties extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne private Measure measure;
	@ManyToOne private IntervalCollection intervalCollection;
	@Enumerated(EnumType.ORDINAL) @Column(name="thetype") private ValueType type = ValueType.DEFAULT;
	@Enumerated(EnumType.ORDINAL) @Column(name="theset") private ValueSet set = ValueSet.DEFAULT;
	
	@Deprecated private Boolean derived;//TODO create getter and setter method in super type
	@ManyToOne private Script derivationScript;
	
	private Boolean nullable;
	@ManyToOne private NullString nullString;
	
	public ValueProperties setIntervalCollection(IntervalCollection intervalCollection){
		this.intervalCollection = intervalCollection;
		return this;
	}
	
	public ValueProperties setMeasure(Measure measure){
		this.measure = measure;
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
	
	public ValueProperties setNullString(NullString nullString){
		this.nullString = nullString;
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
	
	public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SET = "set";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_NULL_STRING = "nullString";
	public static final String FIELD_MEASURE = "measure";
	public static final String FIELD_DERIVATION_SCRIPT = "derivationScript";
	
}