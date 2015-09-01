package org.cyk.system.root.business.impl.generator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
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
	public String generate(StringGenerator generator,Object input) {
		if(generator.getScript()==null){
			return generate(generator.getConfiguration(), input);
		}else{
			Map<String, Object> inputs = new HashMap<>();
			inputs.put(ScriptBusiness.RESULT, input);
			Map<String, Object> results = scriptBusiness.evaluate(generator.getScript(), inputs);
			return (String) results.get(ScriptBusiness.RESULT);
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
		return RootBusinessLayer.getInstance().getApplicationBusiness().generateValue(identifier, Object.class, String.class, input);
	}

	@Override
	public String generate(StringValueGeneratorConfiguration configuration, Object input) {
		if(configuration==null){
			logError("String value configuration is null");
			return null;
		}
		
		String value = getPaddingString(configuration.getLeftPadding())+input+getPaddingString(configuration.getRightPadding());
		if(configuration.getLenght()==null)
			return value;
		return StringUtils.right(value, configuration.getLenght().intValue());
	}
    
	private String getPaddingString(StringValueGeneratorPadding padding){
		if(padding==null || padding.getLenght()==null)
			return Constant.EMPTY_STRING;
		return StringUtils.repeat(padding.getPattern(), padding.getLenght().intValue());
	}
	
	public String generateIdentifier(AbstractIdentifiable identifiable,String runtimeGeneratorIdentifier,StringGenerator databaseGenerator){
		@SuppressWarnings("unchecked")
		StringValueGenerator<AbstractIdentifiable> runtimeGenerator = (StringValueGenerator<AbstractIdentifiable>) 
			RootBusinessLayer.getInstance().getApplicationBusiness().findValueGenerator(runtimeGeneratorIdentifier);
		if(runtimeGenerator==null)
			if(databaseGenerator==null)
				return identifiable.getIdentifier().toString();
			else
				return generate(databaseGenerator, identifiable.getIdentifier());
		else
			return runtimeGenerator.generate(identifiable);
	}

}
