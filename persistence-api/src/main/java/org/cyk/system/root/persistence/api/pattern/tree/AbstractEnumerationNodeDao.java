package org.cyk.system.root.persistence.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationNode;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractEnumerationNodeDao<ENUMERATION extends AbstractEnumerationNode> extends TypedDao<ENUMERATION>,AbstractEnumerationDao<ENUMERATION> {

	Collection<ENUMERATION> readByParent(ENUMERATION parent);
	Long countByParent(ENUMERATION parent);
	
}
