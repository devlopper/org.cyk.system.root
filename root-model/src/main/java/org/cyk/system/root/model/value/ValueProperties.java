package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
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
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class ValueProperties extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name=COLUMN_MEASURE) private Measure measure;
	@ManyToOne @JoinColumn(name=COLUMN_INTERVAL_COLLECTION) private IntervalCollection intervalCollection;
	@Enumerated(EnumType.ORDINAL) @Column(name=COLUMN_TYPE) private ValueType type = ValueType.DEFAULT;
	@Enumerated(EnumType.ORDINAL) @Column(name=COLUMN_SET) private ValueSet set = ValueSet.DEFAULT;
	
	@ManyToOne @JoinColumn(name=COLUMN_DERIVATION_SCRIPT) private Script derivationScript;
	
	private Boolean nullable;
	@ManyToOne @JoinColumn(name=COLUMN_NULL_STRING) private NullString nullString;
	
	public Boolean isValueTypeBoolean(){
		return ValueType.BOOLEAN.equals(type);
	}
	
	public Boolean isValueTypeNumber(){
		return ValueType.NUMBER.equals(type);
	}
	
	public Boolean isValueTypeString(){
		return ValueType.STRING.equals(type);
	}
	
	public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SET = "set";
	public static final String FIELD_NULLABLE = "nullable";
	public static final String FIELD_NULL_STRING = "nullString";
	public static final String FIELD_MEASURE = "measure";
	public static final String FIELD_DERIVED = "derived";
	public static final String FIELD_DERIVATION_SCRIPT = "derivationScript";
	
	public static final String COLUMN_INTERVAL_COLLECTION = FIELD_INTERVAL_COLLECTION;
	public static final String COLUMN_MEASURE = FIELD_MEASURE;
	public static final String COLUMN_NULL_STRING = FIELD_NULL_STRING;
	public static final String COLUMN_DERIVATION_SCRIPT = FIELD_DERIVATION_SCRIPT;
	public static final String COLUMN_TYPE = COLUMN_NAME_UNKEYWORD+FIELD_TYPE;
	public static final String COLUMN_SET = COLUMN_NAME_UNKEYWORD+FIELD_SET;
}