package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.util.Locale;

public interface NumberBusiness {

	String BASE_10_CHARACTERS = "0123456789";
	String BASE_16_CHARACTERS = BASE_10_CHARACTERS + "ABCDEF";
	String BASE_36_CHARACTERS = BASE_10_CHARACTERS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String BASE_62_CHARACTERS = BASE_10_CHARACTERS + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	String format(Number number,Locale locale);
	String format(Number number);
	
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
	
	//String concatenate(Collection<Long> numbers,Boolean numberLenght);
	
	BigDecimal computePercentage(BigDecimal value,BigDecimal percent);
	BigDecimal incrementBy(BigDecimal value,BigDecimal increment);
	BigDecimal incrementByPercentage(BigDecimal value,BigDecimal percent);
	
	BigDecimal parseBigDecimal(String value);
	
	BigDecimal _100 = new BigDecimal("100");
}
