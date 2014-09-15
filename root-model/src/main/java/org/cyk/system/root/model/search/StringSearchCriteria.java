package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class StringSearchCriteria extends AbstractFieldValueSearchCriteria<String> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;

	public enum LocationType{START,INSIDE,END}
	
	private LocationType locationType;
	
	public StringSearchCriteria(String value,LocationType locationType) {
		super(value);
		this.locationType = locationType;
	}
	
	@Override
	public String getPreparedValue() {
		return value==null?"":value;
	}

}
