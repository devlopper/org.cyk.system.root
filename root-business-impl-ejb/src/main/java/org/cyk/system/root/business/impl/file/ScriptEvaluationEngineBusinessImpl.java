package org.cyk.system.root.business.impl.file;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptEvaluationEngineBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.file.ScriptEvaluationEngine;
import org.cyk.system.root.persistence.api.file.ScriptEvaluationEngineDao;

public class ScriptEvaluationEngineBusinessImpl extends AbstractEnumerationBusinessImpl<ScriptEvaluationEngine, ScriptEvaluationEngineDao> implements ScriptEvaluationEngineBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptEvaluationEngineBusinessImpl(ScriptEvaluationEngineDao dao) { 
		super(dao);
	} 

}
