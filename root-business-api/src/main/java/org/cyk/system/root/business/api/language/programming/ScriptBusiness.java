package org.cyk.system.root.business.api.language.programming;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.language.programming.Script;

public interface ScriptBusiness extends TypedBusiness<Script> {
	
	Object evaluate(Script script);
	
} 
