package org.cyk.system.root.persistence.impl.file;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.file.ScriptVariableDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ScriptVariableDaoImpl extends AbstractTypedDao<ScriptVariable> implements ScriptVariableDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByScript;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByScript, _select().where(ScriptVariable.FIELD_SCRIPT));
	}
	
	@Override
	public Collection<ScriptVariable> readByScript(Script script) {
		return namedQuery(readByScript).parameter(ScriptVariable.FIELD_SCRIPT, script).resultMany();
	}

}
 