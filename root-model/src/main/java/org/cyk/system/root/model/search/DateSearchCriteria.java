package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DateSearchCriteria extends AbstractFieldValueSearchCriteria<Date> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;

	public static Date DATE_MOST_PAST;
	public static Date DATE_MOST_FUTURE;
	
	static {
		try {
			DATE_MOST_PAST = DateUtils.parseDate("01/01/1800", "dd/MM/yyyy");
			DATE_MOST_FUTURE = DateUtils.parseDate("01/01/9000", "dd/MM/yyyy");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public enum LocationType{START,INSIDE,END}
	
	private LocationType locationType;
	*/
	public DateSearchCriteria(Date value) {
		super(value);
		//this.locationType = locationType;
	}
	
	public DateSearchCriteria(DateSearchCriteria criteria) {
		super(criteria);
	}
	
	/*
	@Override
	public String getPreparedValue() {
		return value==null?"":value;
	}
	*/

}
