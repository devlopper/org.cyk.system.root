package org.cyk.system.root.business.impl.language.programming;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.programming.ScriptVariableBusiness;
import org.cyk.system.root.business.api.language.programming.ScriptVariableCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.language.programming.ScriptVariable;
import org.cyk.system.root.model.language.programming.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.language.programming.ScriptVariableCollectionDao;
import org.cyk.system.root.persistence.api.language.programming.ScriptVariableDao;

public class ScriptVariableCollectionBusinessImpl extends AbstractCollectionBusinessImpl<ScriptVariableCollection,ScriptVariable, ScriptVariableCollectionDao,ScriptVariableDao,ScriptVariableBusiness> implements ScriptVariableCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ScriptVariableCollectionBusinessImpl(ScriptVariableCollectionDao dao) {
		super(dao); 
	}
		
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<ScriptVariableCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(ScriptVariableCollection.class);
		}
		
	}

}
