package org.cyk.system.root.persistence.impl.file;

import java.io.Serializable;

import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.model.file.ScriptVariableCollection;
import org.cyk.system.root.persistence.api.file.ScriptVariableCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class ScriptVariableCollectionDaoImpl extends AbstractCollectionDaoImpl<ScriptVariableCollection,ScriptVariable> implements ScriptVariableCollectionDao,Serializable {
	private static final long serialVersionUID = 6306356272165070761L;


}
 