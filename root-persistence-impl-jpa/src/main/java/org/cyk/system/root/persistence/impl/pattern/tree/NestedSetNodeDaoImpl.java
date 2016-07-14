package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;

public class NestedSetNodeDaoImpl extends AbstractTypedDao<NestedSetNode> implements NestedSetNodeDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	 
	private String readByParent,countByParent,readByDetachedIdentifier,countByDetachedIdentifier;
	private String readBySet,countBySet,readWhereDetachedIdentifierIsNullBySet,countWhereDetachedIdentifierIsNullBySet;
	private String readBySetByLeftOrRightGreaterThanOrEqualTo;
	private String incrementLeftIndex="incrementLeftIndex",incrementRightIndex="incrementRightIndex";
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		 registerNamedQuery(readByParent, _select().where(NestedSetNode.FIELD_SET, PARAMETER_NESTED_SET).and(NestedSetNode.FIELD_LEFT_INDEX,GT).and(NestedSetNode.FIELD_LEFT_INDEX,NestedSetNode.FIELD_RIGHT_INDEX,LT).orderBy(NestedSetNode.FIELD_LEFT_INDEX, Boolean.TRUE));
		 registerNamedQuery(readByDetachedIdentifier, _select().where(NestedSetNode.FIELD_DETACHED_IDENTIFIER).orderBy(NestedSetNode.FIELD_LEFT_INDEX, Boolean.TRUE));
		 registerNamedQuery(readBySet,_select().where(NestedSetNode.FIELD_SET, PARAMETER_NESTED_SET)); 
		 registerNamedQuery(readWhereDetachedIdentifierIsNullBySet,_select().where(NestedSetNode.FIELD_SET, PARAMETER_NESTED_SET).append(" AND r.detachedIdentifier IS NULL"));
		 registerNamedQuery(readBySetByLeftOrRightGreaterThanOrEqualTo,_select().where(NestedSetNode.FIELD_SET, PARAMETER_NESTED_SET)
				 .append(String.format(" AND (r.%1$s >= :%2$s OR r.%3$s >= :%2$s)",NestedSetNode.FIELD_LEFT_INDEX, PARAMETER_INDEX,NestedSetNode.FIELD_RIGHT_INDEX))
				 	.orderBy(NestedSetNode.FIELD_LEFT_INDEX,Boolean.TRUE)
				);
		 registerNamedQuery(incrementLeftIndex, "UPDATE NestedSetNode nestedSetNode SET nestedSetNode.leftIndex = nestedSetNode.leftIndex + :increment WHERE nestedSetNode.identifier IN :identifiers");
		 registerNamedQuery(incrementRightIndex, "UPDATE NestedSetNode nestedSetNode SET nestedSetNode.rightIndex = :increment + nestedSetNode.rightIndex  WHERE nestedSetNode.identifier IN :identifiers");
	}
	
	@Override
	public Collection<NestedSetNode> readByParent(NestedSetNode parent) {
		return namedQuery(readByParent).parameter(PARAMETER_NESTED_SET, parent.getSet()).parameter(NestedSetNode.FIELD_LEFT_INDEX, parent.getLeftIndex())
				.parameter(NestedSetNode.FIELD_RIGHT_INDEX, parent.getRightIndex())
				.resultMany();
	}
	@Override
	public Long countByParent(NestedSetNode parent) {
		return countNamedQuery(countByParent).parameter(PARAMETER_NESTED_SET, parent.getSet()).parameter(NestedSetNode.FIELD_LEFT_INDEX, parent.getLeftIndex())
				.parameter(NestedSetNode.FIELD_RIGHT_INDEX, parent.getRightIndex())
				.resultOne();
	}
	
	@Override
	public Collection<NestedSetNode> readBySet(NestedSet set) {
		return namedQuery(readBySet).parameter(PARAMETER_NESTED_SET, set).resultMany();
	}
	@Override
	public Long countBySet(NestedSet set) {
		return countNamedQuery(countBySet).parameter(PARAMETER_NESTED_SET, set).resultOne();
	}
	
	@Override
	public Collection<NestedSetNode> readWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return namedQuery(readWhereDetachedIdentifierIsNullBySet).parameter(PARAMETER_NESTED_SET, set).resultMany();
	}
	@Override
	public Long countWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return countNamedQuery(countWhereDetachedIdentifierIsNullBySet).parameter(PARAMETER_NESTED_SET, set).resultOne();
	}
	
	@Override
	public Collection<NestedSetNode> readBySetByLeftOrRightGreaterThanOrEqualTo(NestedSet set,Integer index){
		return namedQuery(readBySetByLeftOrRightGreaterThanOrEqualTo).parameter(PARAMETER_NESTED_SET, set).parameter(PARAMETER_INDEX, index)
				.resultMany();
	}	
	
	@Override
	public Collection<NestedSetNode> readByDetachedIdentifier(String identifier) {
		return namedQuery(readByDetachedIdentifier).parameter(NestedSetNode.FIELD_DETACHED_IDENTIFIER, identifier)
				.resultMany();
	}
	@Override
	public Long countByDetachedIdentifier(String identifier) {
		return countNamedQuery(countByDetachedIdentifier).parameter(NestedSetNode.FIELD_DETACHED_IDENTIFIER, identifier).resultOne();
	}
	
	@Override
	public void incrementLeftIndex(Collection<NestedSetNode> nestedSetNodes,Long increment) {
		if(nestedSetNodes==null || nestedSetNodes.isEmpty())
			return ;
		entityManager.createNamedQuery(incrementLeftIndex).setParameter(QueryStringBuilder.VAR_IDENTIFIERS, ids(nestedSetNodes))
		.setParameter("increment", increment).executeUpdate();
	}
	
	@Override
	public void incrementRightIndex(Collection<NestedSetNode> nestedSetNodes,Long increment) {
		if(nestedSetNodes==null || nestedSetNodes.isEmpty())
			return ;
		entityManager.createNamedQuery(incrementRightIndex).setParameter(QueryStringBuilder.VAR_IDENTIFIERS, ids(nestedSetNodes)).setParameter("increment", increment).executeUpdate();
	}
	
	private static final String PARAMETER_NESTED_SET = "nestedSet";

}
