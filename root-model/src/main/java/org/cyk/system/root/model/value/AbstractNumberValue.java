package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractNumberValue<NUMBER extends Number> extends AbstractValue<NUMBER> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	protected NUMBER gap;
	
	public AbstractNumberValue(NUMBER user) {
		super();
		this.user = user;
	}
	
	public void addUser(NUMBER value){
		if(value!=null){
			if(this.user == null)
				this.user = getZeroValue();
			this.user = add(user, value);
		}
	}
	
	protected NUMBER add(NUMBER value1,NUMBER value2){
		if(value1 == null)
			value1 = getZeroValue();
		if(value2 == null)
			value2 = getZeroValue();
		return __add__(value1, value2);
	}
	
	protected NUMBER subtract(NUMBER value1,NUMBER value2){
		if(value1 == null)
			value1 = getZeroValue();
		if(value2 == null)
			value2 = getZeroValue();
		return __subtract__(value1, value2);
	}
	
	public void computeGap(){
		gap = system==null?null:(user==null?null:subtract(system, user));
	}
		
	protected abstract NUMBER getZeroValue();
	protected abstract NUMBER __add__(NUMBER value1,NUMBER value2);
	protected abstract NUMBER __subtract__(NUMBER value1,NUMBER value2);
	
	@Override
	public String toString() {
		return super.toString()+" , G="+gap;
	}
	
	public static final String FIELD_GAP = "gap";
	
	public static final String COLUMN_GAP = "gap";
}
