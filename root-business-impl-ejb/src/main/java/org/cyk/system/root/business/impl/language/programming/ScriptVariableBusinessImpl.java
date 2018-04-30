package org.cyk.system.root.business.impl.language.programming;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.programming.ScriptVariableBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.language.programming.ScriptVariable;
import org.cyk.system.root.model.language.programming.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.language.programming.ScriptVariableDao;

public class ScriptVariableBusinessImpl extends AbstractCollectionItemBusinessImpl<ScriptVariable, ScriptVariableDao,ScriptVariableCollection> implements ScriptVariableBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptVariableBusinessImpl(ScriptVariableDao dao) { 
		super(dao);
	}
	
}
