package org.cyk.system.root.business.api.file;

import java.util.Map;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.Script;
import org.cyk.utility.common.Constant;

public interface ScriptBusiness extends TypedBusiness<Script> {
	
	String RESULT = Constant.VARIABLE_RESULT;
	
	Map<String, Object> evaluate(Script script,Map<String, Object> inputs);
	
} 
