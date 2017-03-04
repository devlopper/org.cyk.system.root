package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public abstract class AbstractFieldValueSearchCriteria<VALUE_TYPE> implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	protected VALUE_TYPE value,nullValue;
	protected Boolean ascendingOrdered=Boolean.FALSE;
	protected Collection<VALUE_TYPE> excluded;
	
	public AbstractFieldValueSearchCriteria(VALUE_TYPE value) {
		super();
		this.value = value;
	}
	
	public AbstractFieldValueSearchCriteria(AbstractFieldValueSearchCriteria<VALUE_TYPE> criteria) {
		super();
		set(criteria);
	}
	
	public void set(AbstractFieldValueSearchCriteria<VALUE_TYPE> criteria){
		this.value = criteria.value;
		this.nullValue = criteria.nullValue;
		this.ascendingOrdered = criteria.ascendingOrdered;
		if(criteria.excluded!=null)
			this.excluded = new ArrayList<>(criteria.excluded);
	}
	
	public Collection<VALUE_TYPE> getExcluded(){
		if(excluded == null)
			excluded = new ArrayList<>();
		return excluded;
	}
	
	public VALUE_TYPE getPreparedValue(){
		return value==null?nullValue:value;
	}
	
	public Boolean isNull(){
		return value == null;
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
