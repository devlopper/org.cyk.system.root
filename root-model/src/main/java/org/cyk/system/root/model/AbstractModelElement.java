package org.cyk.system.root.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.Setter;

/*lombok*/

/*mapping - jpa*/

@Getter @Setter
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
	
	protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
	
	@Transient protected LoggingHelper.Message.Builder loggingMessageBuilder;
	protected String lastComputedLogMessage;
	
	public AbstractModelElement __setFieldValueComputedByUser__(String name,Boolean value){
		if(StringHelper.getInstance().isNotBlank(name)){
			if(fieldValueComputedByUserMap == null){
				fieldValueComputedByUserMap = new HashMap<>();
			}
			fieldValueComputedByUserMap.put(name, value);
		}
		return this;
	}
	
	public AbstractModelElement __setBirthDateComputedByUser__(Boolean value){
		return __setFieldValueComputedByUser__(FieldHelper.getInstance()
				.buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE), value);
	}
	
	public Boolean isFieldValueComputedByUser(String name){
		if(fieldValueComputedByUserMap == null)
			return null;
		return fieldValueComputedByUserMap.get(name);
	}
	
	public Boolean isBirthDateComputedByUser(){
		return isFieldValueComputedByUser(FieldHelper.getInstance()
				.buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
	}
	
	public Boolean isDeathDateComputedByUser(){
		return isFieldValueComputedByUser(FieldHelper.getInstance()
				.buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_TO_DATE));
	}
	
	public LoggingHelper.Message.Builder getLoggingMessageBuilder(Boolean createIfNull){
		if(loggingMessageBuilder == null && Boolean.TRUE.equals(createIfNull))
			loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		return loggingMessageBuilder;
	}
	
	public String getUiString(){
		return null;
	}
 
	public String getLogMessage(){
		return CommonUtils.getInstance().getFieldsValues(this, AbstractModelElement.class);
	}
	
	public void computeLogMessage(){
		lastComputedLogMessage = getLogMessage();
	}
	
	protected <T> T getFromCode(Class<T> aClass,String code){
		return InstanceHelper.getInstance().getByIdentifier(aClass, code, ClassHelper.Listener.IdentifierType.BUSINESS);
	}
	
	protected <T extends Number> T getNumberFromObject(Class<T> aClass,Object value){
		return NumberHelper.getInstance().get(aClass, value, null);
	}
	
	/**/
	
	public static String generateColumnName(String fieldName){
		return fieldName;
	}
	
	@Override
	public String toString() {
		return StringHelper.getInstance().convert(this);
	}
	
}