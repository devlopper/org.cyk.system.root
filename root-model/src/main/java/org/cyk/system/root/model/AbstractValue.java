package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractValue<TYPE> extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	protected TYPE user;
	
	protected TYPE system;
	
	protected TYPE gap;
	
	public AbstractValue(TYPE user) {
		super();
		this.user = user;
	}
	
	public AbstractValue<TYPE> set(TYPE value,Boolean isUser){
		if(Boolean.TRUE.equals(isUser))
			user = value;
		else
			system = value;
		return this;
	}
	
	public AbstractValue<TYPE> set(TYPE value){
		return set(value,Boolean.TRUE);
	}
	
	public TYPE get(Boolean isUserFirst){
		if(Boolean.TRUE.equals(isUserFirst))
			return user == null ? system : user;
		return system == null ? user : system;
	}
	
	public TYPE get(){
		return get(Boolean.FALSE);
	}
	
	public abstract void computeGap();
	
	@Override
	public String getUiString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}

	@Override
	public String toString() {
		return "U="+user+" , S="+system+" , G="+gap;
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_SYSTEM = "system";
	public static final String FIELD_GAP = "gap";
}
