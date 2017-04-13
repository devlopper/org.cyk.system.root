package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
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
	private String executeIncrementLeftIndex,executeIncrementRightIndex,readDirectChildrenByParent,countDirectChildrenByParent;
	
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
		 registerNamedQuery(executeIncrementLeftIndex, "UPDATE NestedSetNode nestedSetNode SET nestedSetNode.leftIndex = nestedSetNode.leftIndex + :increment WHERE nestedSetNode.identifier IN :identifiers");
		 registerNamedQuery(executeIncrementRightIndex, "UPDATE NestedSetNode nestedSetNode SET nestedSetNode.rightIndex = :increment + nestedSetNode.rightIndex "
		 		+ "WHERE nestedSetNode.identifier IN :identifiers"
		 		+ "");
		 
		 registerNamedQuery(readDirectChildrenByParent, _select().where(NestedSetNode.FIELD_PARENT)
				 .orderBy(commonUtils.attributePath(NestedSetNode.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_ORDER_NUMBER), Boolean.TRUE)
				 .orderBy(NestedSetNode.FIELD_LEFT_INDEX, Boolean.TRUE));
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
	public Collection<NestedSetNode> readDirectChildrenByParent(NestedSetNode parent) {
		return namedQuery(readDirectChildrenByParent).parameter(NestedSetNode.FIELD_PARENT, parent).resultMany();
	}

	@Override
	public Long countDirectChildrenByParent(NestedSetNode parent) {
		return countNamedQuery(countDirectChildrenByParent).parameter(NestedSetNode.FIELD_PARENT, parent).resultOne();
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
	public Integer executeIncrementLeftIndex(Collection<NestedSetNode> nestedSetNodes,Long increment) {
		if(nestedSetNodes==null || nestedSetNodes.isEmpty())
			return 0;
		Integer count = entityManager.createNamedQuery(executeIncrementLeftIndex).setParameter(QueryStringBuilder.VAR_IDENTIFIERS, ids(nestedSetNodes))
				.setParameter(PARAMETER_INCREMENT, increment).executeUpdate();
		throwExecuteUpdateExceptionIfAny(nestedSetNodes.size(), count);
		for(NestedSetNode nestedSetNode : nestedSetNodes)
			nestedSetNode.setLeftIndex(nestedSetNode.getLeftIndex()+increment.intValue());
		return count;
	}
	
	@Override
	public Integer executeIncrementRightIndex(Collection<NestedSetNode> nestedSetNodes,Long increment) {
		if(nestedSetNodes==null || nestedSetNodes.isEmpty())
			return 0;
		Integer count = entityManager.createNamedQuery(executeIncrementRightIndex).setParameter(QueryStringBuilder.VAR_IDENTIFIERS, ids(nestedSetNodes))
			.setParameter(PARAMETER_INCREMENT, increment).executeUpdate();
		throwExecuteUpdateExceptionIfAny(nestedSetNodes.size(), count);
		for(NestedSetNode nestedSetNode : nestedSetNodes)
			nestedSetNode.setRightIndex(nestedSetNode.getRightIndex()+increment.intValue());
		return count;
	}
	
	private static final String PARAMETER_NESTED_SET = "nestedSet";
	private static final String PARAMETER_INCREMENT = "increment";

	

}
