package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValueTransformer implements Serializable{

	private static final long serialVersionUID = -4869402977946675590L;
	/*
	public static int NUMBER_OF_DECIMAL_AFTER_DOT = 2;
	public static ValueTransformationType TRANSFORMATION_TYPE = ValueTransformationType.TRUNCATE;
	
	private ValueTransformationType transformationType;*/
	private Integer numberOfDecimalAfterDot;
	private NumberFormat numberFormat;
	
	public ValueTransformer() {}
	
	public BigDecimal transform(BigDecimal value){
		/*ValueTransformationType vtt = transformationType;
		if(transformationType==null)
			vtt = TRANSFORMATION_TYPE;
		switch(vtt){
		case FORMAT:
			return format(value);
		case TRUNCATE:
			if(numberOfDecimalAfterDot==null)
				return value.setScale(NUMBER_OF_DECIMAL_AFTER_DOT, BigDecimal.ROUND_DOWN);
			return value.setScale(numberOfDecimalAfterDot, BigDecimal.ROUND_DOWN);
		}*/
		return null;
	}

	public BigDecimal format(BigDecimal value) {
		return new BigDecimal(numberFormat.format(value));
	}
	
	
}
