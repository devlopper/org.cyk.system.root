package org.cyk.system.root.business.api.pattern.tree;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

public interface AbstractDataTreeBusiness<ENUMERATION extends AbstractDataTree<TYPE>,TYPE extends DataTreeType> extends AbstractDataTreeNodeBusiness<ENUMERATION> {

}
