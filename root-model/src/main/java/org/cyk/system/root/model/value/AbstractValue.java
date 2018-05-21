package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractValue<TYPE> extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable {
	private static final long serialVersionUID = 6459524990626259467L;
	
	@Enumerated(EnumType.ORDINAL) protected PreferredProperty preferredProperty;
	
	protected TYPE user;
	
	protected TYPE system;
	
	public AbstractValue(TYPE value) {
		super();
		set(value);
	}
	
	public PreferredProperty getPreferredProperty(){
		return preferredProperty == null ? PreferredProperty.DEFAULT : preferredProperty;
	}
	
	public AbstractValue<TYPE> set(TYPE value){
		if(PreferredProperty.USER.equals(getPreferredProperty()))
			user = value;
		else
			system = value;
		return this;
	}
	
	public TYPE get(){
		if(PreferredProperty.USER.equals(getPreferredProperty()))
			return user;
		return system;
	}
	
	public TYPE get(TYPE defaultValueIfNull){
		TYPE value = get();
		if(value == null)
			value = defaultValueIfNull;
		return value;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}

	@Override
	public String toString() {
		return String.valueOf(PreferredProperty.USER.equals(getPreferredProperty()) ? user : system);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_SYSTEM = "system";
	public static final String FIELD_PREFERRED_PROPERTY = "preferredProperty";
	
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_SYSTEM = "system";
	public static final String COLUMN_PREFERRED_PROPERTY = "preferred_property";
	
}
