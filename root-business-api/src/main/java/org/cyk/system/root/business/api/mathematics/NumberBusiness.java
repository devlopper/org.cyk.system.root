package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.formatter.NumberFormatter;

import lombok.Getter;
import lombok.Setter;

public interface NumberBusiness {

	String BASE_10_CHARACTERS = "0123456789";
	String BASE_16_CHARACTERS = BASE_10_CHARACTERS + "ABCDEF";
	String BASE_36_CHARACTERS = BASE_10_CHARACTERS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String BASE_62_CHARACTERS = BASE_10_CHARACTERS + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	String format(Number number,Locale locale);
	String format(Number number);
	
	NumberFormatter.String instanciateOneFormatter(Number number);
	
	String format(NumberFormatter.String formatter);
	
	<NUMBER extends Number> NUMBER parse(Class<NUMBER> numberClass,String number);
	
	<NUMBER extends Number> String formatSequences(Collection<NUMBER> numbers,FormatSequenceArguments<NUMBER> arguments);
	
	String encode(String number,String inputCharacters,String outputCharacters);
	String encode(String number,String outputCharacters);
	String encodeToBase16(String number);
	String encodeToBase36(String number);
	String encodeToBase62(String number);
	
	String decode(String number,String inputCharacters,String outputCharacters);
	String decode(String number,String inputCharacters);
	String decodeBase16(String number);
	String decodeBase36(String number);
	String decodeBase62(String number);
	
	<NUMBER extends Number> NUMBER findHighest(Collection<NUMBER> numbers);
	String concatenate(Collection<? extends Number> numbers,Integer elementLenght);
	<NUMBER extends Number> Collection<NUMBER> deconcatenate(Class<NUMBER> numberClass,String number,Integer elementLenght);
	
	BigDecimal computePercentage(BigDecimal value,BigDecimal percent);
	BigDecimal incrementBy(BigDecimal value,BigDecimal increment);
	BigDecimal incrementByPercentage(BigDecimal value,BigDecimal percent);
	
	BigDecimal parseBigDecimal(String value);
	
	/**/
	
	@Getter @Setter
	public static class FormatSequenceArguments<NUMBER extends Number> implements Serializable{
		private static final long serialVersionUID = -5152613949478843525L;
		
		private NUMBER step;
		private String extremitySeparator=Constant.CHARACTER_HYPHEN.toString();
		private String sequenceSeparator = Constant.CHARACTER_COMA.toString();
		
		public FormatSequenceArguments(NUMBER step) {
			super();
			this.step = step;
		}
		
		
	}
}
