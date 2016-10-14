package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class NumberBusinessImpl extends AbstractBean implements NumberBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;
	
	//private NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	//private DecimalFormat myFormatter = new DecimalFormat(pattern);

	@Override
	public String format(Number number, Locale locale) {
		if(number==null)
			return Constant.EMPTY_STRING;
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
		return numberFormatter.format(number);
	}
	
	@Override
	public String format(Number number) {
		return format(number, inject(LanguageBusiness.class).findCurrentLocale());
	}
	
	@Override
	public String format(Number number, FormatArguments arguments) {
		StringBuilder stringBuilder = new StringBuilder();
		if(FormatArguments.CharacterSet.DIGIT.equals(arguments.getType())){
			NumberFormat numberFormatter = NumberFormat.getNumberInstance(arguments.getLocale()==null?inject(LanguageBusiness.class).findCurrentLocale():arguments.getLocale());
			if(Boolean.TRUE.equals(arguments.getIsPercentage())){
				number = new BigDecimal(number.toString()).multiply(_100);
			}
			stringBuilder.append(numberFormatter.format(number));
			if(Boolean.TRUE.equals(arguments.getIsPercentage())){
				if(StringUtils.isNotBlank(arguments.getPercentageSymbol()))
					stringBuilder.append(Constant.CHARACTER_SPACE+arguments.getPercentageSymbol());
			}
			if(arguments.getWidth()!=null){
				stringBuilder = new StringBuilder(StringUtils.leftPad(stringBuilder.toString(), arguments.getWidth(), arguments.getLeftPadding()));
			}
		}else if(FormatArguments.CharacterSet.LETTER.equals(arguments.getType())){
			if(Boolean.TRUE.equals(arguments.getIsRank())){
				return inject(LanguageBusiness.class).findText("rank."+number+".letter");
			}else{
				throw new RuntimeException("Not yet implemented");
			}
		}
		return stringBuilder.toString();
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
		logTrace("encoding {} in base {} is {}", number,outputCharacters.length(),result);
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
		logTrace("decoding {} from base {} is {}", number,inputCharacters.length(),result);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public<NUMBER extends Number> NUMBER findHighest(Collection<NUMBER> numbers) {
		NUMBER highest = null;
		for(Number number : numbers)
			if(number instanceof Long){
				if(highest==null || (Long)number > (Long)highest)
					highest = (NUMBER) number;
			}else if(number instanceof Integer){
				if(highest==null || (Integer)number > (Integer)highest)
					highest = (NUMBER) number;
			}else
				throw new RuntimeException("Not yet handled "+number.getClass());
		logTrace("highest value between {} is {}", numbers,highest);
		return highest;
	}
	
	@Override
	public String concatenate(Collection<? extends Number> numbers,Integer elementLenght) {
		StringBuilder stringBuilder = new StringBuilder();
		for(Number number : numbers)
			stringBuilder.append(StringUtils.leftPad(String.valueOf(number), elementLenght,Constant.CHARACTER_ZERO));
		logTrace("concatenation of {} with lenght {} is {}", numbers,elementLenght,stringBuilder);
		return stringBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <NUMBER extends Number> Collection<NUMBER> deconcatenate(Class<NUMBER> numberClass,String number,Integer elementLenght) {
		Collection<NUMBER> numbers = new ArrayList<>();
		for(int i=0; i<number.length();i=i+elementLenght){
			String p = StringUtils.substring(number, i, i+elementLenght);
			NUMBER n = null;
			if(Long.class.equals(numberClass))
				n = (NUMBER) new Long(p);
			else if(Integer.class.equals(numberClass))
				n = (NUMBER) new Integer(p);
			
			if(n==null)
				;
			else
				numbers.add(n);
		}
		return numbers;
	}

	@Override
	public <NUMBER extends Number> String formatSequences(Collection<NUMBER> numbers,FormatSequenceArguments<NUMBER> arguments) {
		StringBuilder stringBuilder = new StringBuilder();
		List<NUMBER> sequence = new ArrayList<>();
		BigDecimal step = new BigDecimal(arguments.getStep().intValue());
		BigDecimal previous = null;
		for(NUMBER number : numbers){
			if(sequence.isEmpty())
				sequence.add(number);
			else{
				if ( previous.add(step).compareTo(new BigDecimal(number.intValue())) == 0){
					sequence.add(number);
				}else{
					addSequence(stringBuilder, sequence, arguments);
					sequence.add(number);
				}
			}
			previous = new BigDecimal(number.intValue());
		}
		if(!sequence.isEmpty())
			addSequence(stringBuilder, sequence, arguments);
		
		return stringBuilder.toString();
	}
	
	private void addSequence(StringBuilder stringBuilder,List<? extends Number> sequence,FormatSequenceArguments<? extends Number> arguments){
		if(stringBuilder.length()>0)
			stringBuilder.append(Constant.CHARACTER_SPACE+arguments.getSequenceSeparator()+Constant.CHARACTER_SPACE);
		stringBuilder.append( sequence.get(0) );
		if(sequence.size()>1)
			stringBuilder.append(Constant.CHARACTER_SPACE+arguments.getExtremitySeparator()+Constant.CHARACTER_SPACE+sequence.get(sequence.size()-1) );
		sequence.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <NUMBER extends Number> NUMBER parse(Class<NUMBER> numberClass,String number) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0 ; i < number.length() ; i++)
			if( ((int)number.charAt(i)) < 127 )
				stringBuilder.append(number.charAt(i));
		number = stringBuilder.toString();
		number = StringUtils.remove(number, Constant.CHARACTER_SPACE);
		
		BigDecimal bigDecimal = new BigDecimal(number);
		if(BigDecimal.class.equals(numberClass))
			return (NUMBER) bigDecimal;
		if(Long.class.equals(numberClass))
			return (NUMBER) new Long(bigDecimal.longValue());
		if(Integer.class.equals(numberClass))
			return (NUMBER) new Integer(bigDecimal.intValue());
		return null;
	}
	
}
