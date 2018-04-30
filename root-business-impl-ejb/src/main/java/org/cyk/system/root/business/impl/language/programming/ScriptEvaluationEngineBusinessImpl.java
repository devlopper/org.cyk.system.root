package org.cyk.system.root.business.impl.language.programming;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.programming.ScriptEvaluationEngineBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.language.programming.ScriptEvaluationEngine;
import org.cyk.system.root.persistence.api.language.programming.ScriptEvaluationEngineDao;

public class ScriptEvaluationEngineBusinessImpl extends AbstractEnumerationBusinessImpl<ScriptEvaluationEngine, ScriptEvaluationEngineDao> implements ScriptEvaluationEngineBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptEvaluationEngineBusinessImpl(ScriptEvaluationEngineDao dao) { 
		super(dao);
	} 

	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<ScriptEvaluationEngine> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(ScriptEvaluationEngine.class);
		}
		
	}

}
