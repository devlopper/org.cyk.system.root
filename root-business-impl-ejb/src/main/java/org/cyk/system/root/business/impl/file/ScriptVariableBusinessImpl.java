package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptVariableBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.model.file.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.file.ScriptVariableDao;

public class ScriptVariableBusinessImpl extends AbstractCollectionItemBusinessImpl<ScriptVariable, ScriptVariableDao,ScriptVariableCollection> implements ScriptVariableBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptVariableBusinessImpl(ScriptVariableDao dao) { 
		super(dao);
	}
	
}
