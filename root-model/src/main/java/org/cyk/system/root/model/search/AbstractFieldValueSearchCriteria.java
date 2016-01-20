package org.cyk.system.root.model.search;

import java.io.Serializable;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public abstract class AbstractFieldValueSearchCriteria<VALUE_TYPE> implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	protected VALUE_TYPE value,nullValue;
	protected Boolean ascendingOrdered=Boolean.FALSE;
	
	public VALUE_TYPE getPreparedValue(){
		return value==null?nullValue:value;
	}

	public AbstractFieldValueSearchCriteria(VALUE_TYPE value) {
		super();
		this.value = value;
	}
	
	public AbstractFieldValueSearchCriteria(AbstractFieldValueSearchCriteria<VALUE_TYPE> criteria) {
		super();
		this.value = criteria.value;
		this.nullValue = criteria.nullValue;
		this.ascendingOrdered = criteria.ascendingOrdered;
	}
	
	@Override
	public String toString() {
		Object o = getPreparedValue();
		if(o==null)
			return Constant.EMPTY_STRING;
		else
			return o.toString();
	}
}
