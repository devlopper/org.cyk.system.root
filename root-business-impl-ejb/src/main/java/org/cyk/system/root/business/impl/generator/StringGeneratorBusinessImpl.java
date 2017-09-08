package org.cyk.system.root.business.impl.generator;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.StringValueGeneratorConfiguration;
import org.cyk.system.root.model.generator.StringValueGeneratorPadding;
import org.cyk.system.root.persistence.api.generator.StringGeneratorDao;
import org.cyk.utility.common.Constant;

public class StringGeneratorBusinessImpl extends AbstractTypedBusinessService<StringGenerator, StringGeneratorDao> implements StringGeneratorBusiness, Serializable {

	private static final long serialVersionUID = -2569385241851506415L;

	@Inject private ScriptBusiness scriptBusiness;
  
	@Inject
	public StringGeneratorBusinessImpl(StringGeneratorDao dao) { 
		super(dao);
	}

	@Override
	public StringGenerator instanciateOne(String leftPrefix,String leftPattern,Long leftLength,String rightPattern,Long rightLength,Long length){
    	StringGenerator stringGenerator = instanciateOne();
    	stringGenerator.setConfiguration(new StringValueGeneratorConfiguration());
    	stringGenerator.getConfiguration().getLeftPadding().setPrefix(leftPrefix);
    	stringGenerator.getConfiguration().getLeftPadding().setPattern(leftPattern);
    	stringGenerator.getConfiguration().getLeftPadding().setLength(leftLength);
    	
    	stringGenerator.getConfiguration().getRightPadding().setPattern(rightPattern);
    	stringGenerator.getConfiguration().getRightPadding().setLength(rightLength);
    	
    	stringGenerator.getConfiguration().setLength(length);
    	
    	return stringGenerator;
    }
	
	@Override
	public String generate(StringGenerator generator,Object input) {
		if(generator.getScript()==null){
			return generate(generator.getConfiguration(), input);
		}else{
			scriptBusiness.evaluate(generator.getScript());
			return (String) generator.getScript().getReturned();
		}
	}

	@Override
	public StringGenerator create(StringGenerator stringGenerator) {
		if(stringGenerator.getScript()!=null)
			scriptBusiness.create(stringGenerator.getScript());
		return super.create(stringGenerator);
	}
	
	@Override
	public String generate(String identifier, Object input) {
		return inject(ApplicationBusiness.class).generateValue(identifier, Object.class, String.class, input);
	}

	@Override
	public String generate(StringValueGeneratorConfiguration configuration, Object input) {
		if(configuration==null){
			logError("String value configuration is null");
			return null;
		}
		logTrace("Generating string. configuration={} , input={}",configuration.getLogMessage(), input);
		StringBuilder stringBuilder = new StringBuilder(getPaddingString(configuration.getLeftPadding())+input+getPaddingString(configuration.getRightPadding()));
		logTrace("With padding = {} , {} character(s)", stringBuilder,stringBuilder.length());
		if(configuration.getLength()==null)
			return stringBuilder.toString();
		String a = StringUtils.right(stringBuilder.toString(), configuration.getLength().intValue());
		logTrace("Max length = {} , Adjusted length = {} , {} character(s)",configuration.getLength(), a,a.length());
		String s = prefix(configuration.getLeftPadding())+suffix(configuration.getLeftPadding())
				+a+prefix(configuration.getRightPadding())+suffix(configuration.getLeftPadding());
		logTrace("With prefixes and suffixes = {} , {} character(s)", s,s.length());
		return s;
	}
    
	private String getPaddingString(StringValueGeneratorPadding padding){
		if(padding==null || padding.getLength()==null)
			return Constant.EMPTY_STRING;
		String s = StringUtils.repeat(padding.getPattern(), padding.getLength().intValue());
		logTrace("Padding string : {} , {} charater(s)", s,s.length());
		return s;
	}
	
	private String prefix(StringValueGeneratorPadding padding){
		if(padding==null)
			return Constant.EMPTY_STRING;
		return StringUtils.defaultString(padding.getPrefix());
	}
	
	private String suffix(StringValueGeneratorPadding padding){
		if(padding==null)
			return Constant.EMPTY_STRING;
		return StringUtils.defaultString(padding.getSuffix());
	}
	
	public String generateIdentifier(AbstractIdentifiable identifiable,String runtimeGeneratorIdentifier,StringGenerator databaseGenerator){
		@SuppressWarnings("unchecked")
		StringValueGenerator<AbstractIdentifiable> runtimeGenerator = (StringValueGenerator<AbstractIdentifiable>) 
				inject(ApplicationBusiness.class).findValueGenerator(runtimeGeneratorIdentifier);
		if(runtimeGenerator==null)
			if(databaseGenerator==null)
				return identifiable.getIdentifier().toString();
			else
				return generate(databaseGenerator, identifiable.getIdentifier());
		else
			return runtimeGenerator.generate(identifiable);
	}

}
