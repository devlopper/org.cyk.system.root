package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.utility.common.computation.ArithmeticOperator;

public abstract class AbstractDataTreeNodeDaoImpl<ENUMERATION extends AbstractDataTreeNode> extends AbstractEnumerationDaoImpl<ENUMERATION> 
	implements AbstractDataTreeNodeDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	//@Inject protected NestedSetNodeDao nestedSetNodeDao;
	
	/* 
	 *Named Queries Identifiers Declaration 
	 */
	private String readByParent,countByParent,readRoots,countRoots,readByLeftIndexByRightIndex; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByLeftIndexByRightIndex, _select().where("node.set", "nestedSet")
				.and("node.leftIndex","leftIndex",ArithmeticOperator.EQ).and("node.leftIndex","rightIndex",ArithmeticOperator.EQ));
		registerNamedQuery(readByParent, _select().where("node.set", "nestedSet").and("node.leftIndex","leftIndex",GT).and("node.leftIndex","rightIndex",LT)
				.orderBy(commonUtils.attributePath(AbstractDataTreeNode.FIELD_NODE,NestedSetNode.FIELD_LEFT_INDEX), Boolean.TRUE));
		registerNamedQuery(readRoots, _select().where(null,"node.set.root", QueryStringBuilder.VAR+".node",ArithmeticOperator.EQ,false));
	}
	
	@Override
	public ENUMERATION readParent(ENUMERATION child) {
		NestedSetNode parentNode = child.getNode().getParent();
		if(parentNode==null)
			return null;
		return namedQuery(readByLeftIndexByRightIndex).parameter("nestedSet", parentNode.getSet()).parameter("leftIndex", parentNode.getLeftIndex())
				.parameter("rightIndex", parentNode.getLeftIndex()).resultOne();
	}
		
	@Override
	public Collection<ENUMERATION> readByParent(ENUMERATION parent) {
	    NestedSetNode n = parent.getNode();
	    return namedQuery(readByParent).parameter("nestedSet", n.getSet()).parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultMany();
	}
	
	@Override
	public Long countByParent(ENUMERATION parent) {
	    NestedSetNode n = parent.getNode();
        return countNamedQuery(countByParent).parameter("nestedSet", n.getSet()).parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultOne();
	}
	
	@Override
    public Collection<ENUMERATION> readRoots() {
        return namedQuery(readRoots).resultMany();
    }
    
    @Override
    public Long countRoots() {
        return countNamedQuery(countRoots).resultOne();
    }
		
	
	
	/*private ENUMERATION getParent(ENUMERATION enumeration,Collection<ENUMERATION> enumerations){
		for(ENUMERATION index : enumerations)
			if( index.getNode()!=null && enumeration.getNode().getParent().getIdentifier().equals(index.getNode().getIdentifier()) )
				return index;
		return null;
	}*/
	
}
