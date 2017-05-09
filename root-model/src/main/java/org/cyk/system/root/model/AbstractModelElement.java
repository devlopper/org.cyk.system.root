package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.CommonUtils;

import lombok.Getter;
import lombok.Setter;

/*lombok*/

/*mapping - jpa*/

public abstract class AbstractModelElement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String COLUMN_NAME_UNKEYWORD = "the_";
	public static final String COLUMN_NAME_WORD_SEPARATOR = "_";
	public static final int COLUMN_VALUE_PRECISION = 30;
	public static final int COEFFICIENT_PRECISION = 5;
	public static final int PERCENT_PRECISION = 5;
	public static final int FLOAT_SCALE = 2;
	public static final int PERCENT_SCALE = 4;
	public static final BigDecimal LOWEST_NON_ZERO_POSITIVE_VALUE = new BigDecimal("0."+StringUtils.repeat('0', 10)+"1");
	
	@Getter @Setter protected String lastComputedLogMessage;
	
	public abstract String getUiString();
 
	public String getLogMessage(){
		return CommonUtils.getInstance().getFieldsValues(this, AbstractModelElement.class);
	}
	
	public void computeLogMessage(){
		lastComputedLogMessage = getLogMessage();
	}
	
	/**/
	
	public static String generateColumnName(String fieldName){
		return fieldName;
	}
	
}