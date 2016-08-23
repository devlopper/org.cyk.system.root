package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptVariableBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.file.ScriptVariableDao;

public class ScriptVariableBusinessImpl extends AbstractTypedBusinessService<ScriptVariable, ScriptVariableDao> implements ScriptVariableBusiness, Serializable {
	
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptVariableBusinessImpl(ScriptVariableDao dao) { 
		super(dao);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ScriptVariable> findByScript(Script script) {
		return dao.readByScript(script);
	} 

	
}
