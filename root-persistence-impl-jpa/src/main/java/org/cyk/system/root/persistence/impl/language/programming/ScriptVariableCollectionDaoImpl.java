package org.cyk.system.root.persistence.impl.language.programming;

import java.io.Serializable;

import org.cyk.system.root.model.language.programming.ScriptVariable;
import org.cyk.system.root.model.language.programming.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.language.programming.ScriptVariableCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class ScriptVariableCollectionDaoImpl extends AbstractCollectionDaoImpl<ScriptVariableCollection,ScriptVariable> implements ScriptVariableCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;


}
 