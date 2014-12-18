package org.cyk.system.root.business.api.file;

import java.util.Map;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.Script;

public interface ScriptBusiness extends TypedBusiness<Script> {
	
	Map<String, Object> evaluate(Script script,Map<String, Object> inputs);
	
} 
