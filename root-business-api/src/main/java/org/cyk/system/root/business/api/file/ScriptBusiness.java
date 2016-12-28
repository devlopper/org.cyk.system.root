package org.cyk.system.root.business.api.file;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.Script;

public interface ScriptBusiness extends TypedBusiness<Script> {
	
	Object evaluate(Script script);
	
} 
