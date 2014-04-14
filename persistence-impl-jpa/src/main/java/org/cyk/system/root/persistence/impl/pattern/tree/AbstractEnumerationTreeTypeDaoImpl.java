package org.cyk.system.root.persistence.impl.pattern.tree;

import java.io.Serializable;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractEnumerationTreeTypeDao;

public abstract class AbstractEnumerationTreeTypeDaoImpl<TYPE extends AbstractEnumerationTreeType> extends AbstractEnumerationNodeDaoImpl<TYPE>
	implements AbstractEnumerationTreeTypeDao<TYPE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	
}
