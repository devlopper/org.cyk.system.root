package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DateSearchCriteria extends AbstractFieldValueSearchCriteria<Date> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;

	/*
	public enum LocationType{START,INSIDE,END}
	
	private LocationType locationType;
	*/
	public DateSearchCriteria(Date value) {
		super(value);
		//this.locationType = locationType;
	}
	/*
	@Override
	public String getPreparedValue() {
		return value==null?"":value;
	}
	*/

}
