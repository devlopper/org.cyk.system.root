package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public abstract class AbstractFieldValueSearchCriteria<VALUE_TYPE> implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	protected VALUE_TYPE value;
	protected Boolean ascendingOrdered=Boolean.FALSE;
	
	public VALUE_TYPE getPreparedValue(){
		return value;
	}

	public AbstractFieldValueSearchCriteria(VALUE_TYPE value) {
		super();
		this.value = value;
	}
}
