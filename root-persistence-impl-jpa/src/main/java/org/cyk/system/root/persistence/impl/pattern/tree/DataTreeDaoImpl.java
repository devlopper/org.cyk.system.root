package org.cyk.system.root.persistence.impl.pattern.tree;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeDao;

public class DataTreeDaoImpl extends AbstractDataTreeDaoImpl<AbstractDataTree<DataTreeType>,DataTreeType> implements DataTreeDao {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8565120308534866976L;

	@Override
    protected Class<?> parameterizedClass() {
        return AbstractDataTree.class;
    }
    
}
