package org.cyk.system.root.business.api.generator;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGeneratorConfiguration;

public interface StringGeneratorBusiness extends TypedBusiness<StringGenerator> {

	String generate(StringGenerator generator,Object input);

	String generate(String identifier,Object input);
    
    String generate(StringValueGeneratorConfiguration configuration,Object input);
    
    String generateIdentifier(AbstractIdentifiable identifiable,String runtimeGeneratorIdentifier,StringGenerator databaseGenerator);

	StringGenerator instanciateOne(String leftPrefix, String leftPattern, Long leftLength, String rightPattern,Long rightLength, Long length);
	
} 
