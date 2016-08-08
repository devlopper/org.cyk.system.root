package org.cyk.system.root.business.api.file;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;

public interface ScriptVariableBusiness extends TypedBusiness<ScriptVariable> {
	
	Collection<ScriptVariable> findByScript(Script script);
	
} 
