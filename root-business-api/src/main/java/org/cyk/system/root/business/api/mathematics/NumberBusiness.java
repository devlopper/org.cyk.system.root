package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public interface NumberBusiness {

	String BASE_10_CHARACTERS = "0123456789";
	String BASE_16_CHARACTERS = BASE_10_CHARACTERS + "ABCDEF";
	String BASE_36_CHARACTERS = BASE_10_CHARACTERS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String BASE_62_CHARACTERS = BASE_10_CHARACTERS + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	String format(Number number,Locale locale);
	String format(Number number);
	String format(Number number,FormatArguments arguments);
	
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
	
	BigDecimal _100 = new BigDecimal("100");
	
	/**/
	
	@Getter @Setter
	public static class FormatArguments implements Serializable{
		private static final long serialVersionUID = 7407251574517349144L;
		public static enum Type{DIGIT,LETTER};
		private Locale locale;
		private Type type = Type.DIGIT;
		private Boolean isRank = Boolean.FALSE;
	}
}
