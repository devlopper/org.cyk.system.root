package org.cyk.system.root.persistence.impl.pattern.tree;

import java.io.Serializable;

import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeTypeDao;

public abstract class AbstractDataTreeTypeDaoImpl<TYPE extends DataTreeType> extends AbstractDataTreeNodeDaoImpl<TYPE>
	implements AbstractDataTreeTypeDao<TYPE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	
}
