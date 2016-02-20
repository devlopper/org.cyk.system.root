package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Singleton;

import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.utility.common.Constant;

@Singleton
public class NumberBusinessImpl implements NumberBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;
	
	//private NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	//private DecimalFormat myFormatter = new DecimalFormat(pattern);

	@Setter private LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance();
	
	@Override
	public String format(Number number, Locale locale) {
		if(number==null)
			return Constant.EMPTY_STRING;
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
		return numberFormatter.format(number);
	}
	
	@Override
	public String format(Number number) {
		return format(number, languageBusiness.findCurrentLocale());
	}
	
	@Override
	public String encode(String number, String inputCharacters, String outputCharacters) {
		BigInteger integer = new BigInteger(number);
		if (integer.compareTo(BigInteger.ZERO) == -1) {
			throw new IllegalArgumentException(number+" must be nonnegative");
		}
		BigInteger size = new BigInteger(String.valueOf(outputCharacters.length()));
		String result = "";
		while (integer.compareTo(BigInteger.ZERO) == 1) {
			result = outputCharacters.charAt(integer.mod(size).intValue()) + result;
			integer = integer.divide(size);
		}
		return result;
	}
	@Override
	public String encode(String number, String outputCharacters) {
		return encode(number, BASE_10_CHARACTERS, outputCharacters);
	}
	@Override
	public String encodeToBase16(String number) {
		return encode(number, BASE_16_CHARACTERS);
	}
	@Override
	public String encodeToBase36(String number) {
		return encode(number, BASE_36_CHARACTERS);
	}
	@Override
	public String encodeToBase62(String number) {
		return encode(number, BASE_62_CHARACTERS);
	}
	
	@Override
	public String decode(String number, String inputCharacters, String outputCharacters) {
		for (char character : number.toCharArray()) {
			if (!inputCharacters.contains(String.valueOf(character))) {
				throw new IllegalArgumentException("Invalid character(s) in string: " + character);
			}
		}
		BigInteger result = BigInteger.ZERO;
		number = new StringBuffer(number).reverse().toString();
		BigDecimal count = BigDecimal.ONE;
		BigDecimal inputCharactersLenght = new BigDecimal(inputCharacters.length());
		for (char character : number.toCharArray()) {
			result = result.add(new BigDecimal(inputCharacters.indexOf(character)).multiply(count).toBigInteger());
			count = count.multiply(inputCharactersLenght);
		}
		return result.toString();
	}
	
	@Override
	public String decode(String number, String inputCharacters) {
		return decode(number, inputCharacters, BASE_10_CHARACTERS);
	}
	
	@Override
	public String decodeBase16(String number) {
		return decode(number, BASE_16_CHARACTERS);
	}
	
	@Override
	public String decodeBase36(String number) {
		return decode(number, BASE_36_CHARACTERS);
	}
	
	@Override
	public String decodeBase62(String number) {
		return decode(number, BASE_62_CHARACTERS);
	}
	
	@Override
	public BigDecimal computePercentage(BigDecimal value, BigDecimal percent) {
		return value.multiply(percent).divide(_100);
	}
	
	@Override
	public BigDecimal incrementBy(BigDecimal value, BigDecimal increment) {
		return value.add(increment);
	}

	@Override
	public BigDecimal incrementByPercentage(BigDecimal value, BigDecimal percent) {
		return incrementBy(value, computePercentage(value, percent));
	}
	
	@Override
	public BigDecimal parseBigDecimal(String value) {
		return value == null ? null : new BigDecimal(value);
	}

}
