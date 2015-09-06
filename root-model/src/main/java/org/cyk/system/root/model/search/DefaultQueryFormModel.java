package org.cyk.system.root.model.search;
import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar.Format;

@Getter @Setter
public class DefaultQueryFormModel implements Serializable {

	private static final long serialVersionUID = -3328823824725030136L;

	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
	
	@Input @InputText
	private String identifier;
	
	@Input @InputCalendar(format=Format.DATETIME_SHORT)
	private Date fromDate;
	
	@Input @InputCalendar(format=Format.DATETIME_SHORT)
	private Date toDate;
	
	/**/
	
	private Boolean showIdentifier=Boolean.FALSE;
	private Boolean showFromDate = Boolean.TRUE;
	private Boolean showToDate = Boolean.TRUE;
	
	/**/
	
	public void setupAsOneFetch(){
		showIdentifier=Boolean.TRUE;
		showFromDate = Boolean.FALSE;
		showToDate = Boolean.FALSE;
	}
	
	public void setupAsManyFetch(){
		showIdentifier=Boolean.FALSE;
		showFromDate = Boolean.TRUE;
		showToDate = Boolean.TRUE;
	}
	
}
