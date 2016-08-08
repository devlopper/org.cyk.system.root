package org.cyk.system.root.persistence.api.file;

import java.util.Collection;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.TypedDao;

public interface ScriptVariableDao extends TypedDao<ScriptVariable> {

	Collection<ScriptVariable> readByScript(Script script);
	
}
