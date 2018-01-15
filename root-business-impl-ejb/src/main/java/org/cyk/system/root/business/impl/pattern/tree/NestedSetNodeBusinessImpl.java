package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.BusinessException;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;

@Stateless
public class NestedSetNodeBusinessImpl extends AbstractTypedBusinessService<NestedSetNode, NestedSetNodeDao> implements NestedSetNodeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NestedSetNodeBusinessImpl(NestedSetNodeDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findByParent(NestedSetNode parent) {
		return dao.readByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByParent(NestedSetNode parent) {
		return dao.countByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findBySet(NestedSet set) {
		return dao.readBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countBySet(NestedSet set) {
		return dao.countBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return dao.readWhereDetachedIdentifierIsNullBySet(set);
	}
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereDetachedIdentifierIsNullBySet(NestedSet set) {
		return dao.countWhereDetachedIdentifierIsNullBySet(set);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> findByDetachedIdentifier(String identifier) {
		return dao.readByDetachedIdentifier(identifier);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByDetachedIdentifier(String identifier) {
		return dao.countByDetachedIdentifier(identifier);
	}

	@Override
	protected void beforeCreate(NestedSetNode node) {
		super.beforeCreate(node);
		Long numberOfChildrenOfparent = node.getParent() == null ? 0l : dao.countByParent(node.getParent());
		Long numberOfDirectChildrenOfparent = numberOfChildrenOfparent == 0 ? 0l : dao.countDirectChildrenByParent(node.getParent());
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		//LogMessage.Builder logMessageBuilder = new LogMessage.Builder("CREATE", node);
		//logMessageBuilder.addParameters("parent",node.getParent());
		loggingMessageBuilder.addManyParameters("CREATE NODE");
		loggingMessageBuilder.addNamedParameters("parent",node.getParent());
		
		NestedSet set = node.getSet();
		if(set == null && node.getParent()!=null)
			set = node.getParent().getSet();
		loggingMessageBuilder.addNamedParameters("set",set);
		createIfNotIdentified(set);
		/*
		if(set.getIdentifier()==null){//set not yet created
			inject(NestedSetDao.class).create(set);
			//logMessageBuilder.addParameters("Set",node.getSet());
			//logTrace("Set {} auto created",node.getSet().getName());
			loggingMessageBuilder.addNamedParameters("set",node.getSet());
		}
		*/
		if(set.getRoot()==null){//first node of the set
			node.setParent(null);
			set.setRoot(node);
			node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);
			//inject(NestedSetDao.class).update(set);
		}else{
			node.setSet(set);
			NestedSetNode parent = node.getParent();
			
			Integer parentRightIndex = parent.getRightIndex();
			node.setLeftIndex(parentRightIndex);
			node.setRightIndex(node.getLeftIndex()+1);
			Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputedCandidate = dao.readBySetByLeftOrRightGreaterThanOrEqualTo(set, parentRightIndex);
			Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed = new ArrayList<>();
			
			nestedSetNodesWhereIndexesToBeRecomputed.add(parent);
			for(NestedSetNode index : nestedSetNodesWhereIndexesToBeRecomputedCandidate){
				// Because node parent can be changed by attach so to be ignore
				// only one instance of parent must be handled to avoid inconsistent update
				if(index.equals(node) || index.equals(parent)){
					
				}else
					nestedSetNodesWhereIndexesToBeRecomputed.add(index);
			}
			//logTrace("On create : recomputing indexes of nodes. size = {} , elements = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
			dao.executeIncrementLeftIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.TRUE, parentRightIndex), 2l);
			dao.executeIncrementRightIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.FALSE, parentRightIndex), 2l);
		}
		
		node.setDetachedIdentifier(null);
	}
	
	@Override
	protected void __create__(NestedSetNode node) {
		Integer numberOfElement = NumberHelper.getInstance().get(Integer.class, node.getSet().getNumberOfElement(), 0);
		if(node.getIdentifier()==null){
			dao.create(node);
			node.getSet().setNumberOfElement(NumberHelper.getInstance().add(node.getSet().getNumberOfElement(), 1l).intValue());
			if(node.getSet().getRoot()==null){//first node of the set
				//loggingMessageBuilder.addNamedParameters("first",true);
			}else{
				//loggingMessageBuilder.addNamedParameters("created",true);
			}
		}else{
			dao.update(node);
			//loggingMessageBuilder.addNamedParameters("updated",true);
		}
		System.out.println("NestedSetNodeBusinessImpl.__create__() "+numberOfElement+" ::: "+(numberOfElement+5));
		throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfElement")
				.setDomainNameIdentifier("nestedset").setNumber1(numberOfElement).setNumber2(numberOfElement+5), BusinessException.class);
		
		/*
		throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfChildrenOfparent")
				.setDomainNameIdentifier("nestedsetnode").setNumber1(numberOfChildrenOfparent).setNumber2(numberOfChildrenOfparent+2), BusinessException.class);
		
		throw__(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("numberOfDirectChildrenOfparent")
				.setDomainNameIdentifier("nestedsetnode").setNumber1(numberOfDirectChildrenOfparent).setNumber2(numberOfChildrenOfparent+1), BusinessException.class);
		*/
		inject(NestedSetDao.class).update(node.getSet());
		/*
		loggingMessageBuilder.addNamedParameters("root",set.getRoot(),"left",node.getLeftIndex(),"right",node.getRightIndex());
		logTrace(loggingMessageBuilder);
		*/
	}

	@Override
	public NestedSetNode delete(NestedSetNode node) {
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		Collections.reverse(tree);
		tree.add(node);
		logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Deleting",node, tree.size(),tree);
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1));
		if(node.getParent()==null){
			node.getSet().setRoot(null);
			inject(NestedSetDao.class).update(node.getSet());	
		}
		
		//delete tree
		for(NestedSetNode n : tree){
			n.computeLogMessage();
			n.setParent(null);
			dao.delete(n);
			inject(GlobalIdentifierBusiness.class).delete(n.getGlobalIdentifier());
			logTrace("{} deleted", n.getLastComputedLogMessage());
		}
		
		//if(dao.countBySet(node.getSet()) == 0)
		//	inject(NestedSetBusiness.class).delete(node.getSet());
		
		//dao.delete(tree);
		
		return node;
	}
	
	private void computeIndexesOnDelete(Integer subTreeRootNodeRightIndex,Integer subTreeNodesCount,Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed) {
		logTrace("On delete : recomputing indexes of nodes. size = {} , elements = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
		
		int step = subTreeNodesCount*2;
		for(NestedSetNode n : nestedSetNodesWhereIndexesToBeRecomputed){
			updateBoundaries(n,-step, n.getLeftIndex()>subTreeRootNodeRightIndex?null:false);//both bounds or right only
			//dao.update(n); //TODO I think it is not necessary! to be confirmed
			logTrace("Node indexes {} recomputed",n);
		}
		
		//dao.incrementLeftIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.TRUE, subTreeRootNodeRightIndex), -step*1l);
		//dao.executeIncrementRightIndex(getWhereBoundariesGreaterThanOrEqualTo(nestedSetNodesWhereIndexesToBeRecomputed, Boolean.FALSE, subTreeRootNodeRightIndex), -step*1l);
	}
	
	@Override
	public NestedSetNode detach(NestedSetNode node) {
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("DETACH", node);
		exceptionUtils().exception(node.getLeftIndex()<=0 && node.getRightIndex()<=0, "exception.nestedsetnode.alreadydetached");
		List<NestedSetNode> tree = new ArrayList<>(dao.readByParent(node));
		tree.add(0, node);
		logMessageBuilder.addParameters("tree",tree,"size",tree.size());
		//logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Detaching", node,tree.size(),tree);
		computeIndexesOnDelete(node.getRightIndex(), tree.size(), dao.readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), node.getRightIndex()+1));
		String detachedIdentifier = System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(12);
		for(NestedSetNode n : tree){
			n.computeLogMessage();
			//n.setParent(null); this link should not be lost : it might help finding the subtree
			Integer leftIndex = n.getLeftIndex(),rightIndex = n.getRightIndex();
			n.setLeftIndex(-1 * rightIndex);
			n.setRightIndex(-1 * leftIndex);
			n.setDetachedIdentifier(detachedIdentifier);
			dao.update(n);
			//logTrace("{} detached -> {}", n.getLastComputedLogMessage(),n.getLogMessage());
			//logMessageBuilder.addParameters("Detach",n);
		}
		logTrace(logMessageBuilder);
		return node;
	}

	@Override
	public NestedSetNode attach(NestedSetNode node, NestedSetNode parent) {
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("ATTACH", node);
		exceptionUtils().exception(node.getLeftIndex()>=0 && node.getRightIndex()>=0, "exception.nestedsetnode.alreadyattached");
		List<NestedSetNode> treeNodes = new ArrayList<>(dao.readByDetachedIdentifier(node.getDetachedIdentifier()));
		logMessageBuilder.addParameters("nodes",treeNodes);
		//logTrace(SUB_TREE_ACTION_LOG_FORMAT,"Attaching", node,treeNodes.size(),treeNodes);
		treeNodes.get(0).setParent(parent);//Attaching to the new parent
		for(NestedSetNode treeNode : treeNodes){
			treeNode.setSet(parent.getSet());//attaching to the parent's set
			create(treeNode);
			//logTrace("{} attached", treeNode);
			//System.out.println("Children of "+node+" : "+dao.readByParent(node));
			//System.out.println("Children of "+node+" : "+dao.readAll());
		}
		logTrace(logMessageBuilder);
		return node;
	}
	
	/**/
	
	/*private void updateBoundariesGreaterThanOrEqualTo(NestedSetNode node,Boolean increase,Integer index){
		int sign = increase?+1:-1;
		if(node.getLeftIndex()>=index)
			updateBoundaries(node, sign*2, Boolean.TRUE);
		if(node.getRightIndex()>=index)
			updateBoundaries(node, sign*2, Boolean.FALSE);
	}*/
	private Collection<NestedSetNode> getWhereBoundariesGreaterThanOrEqualTo(Collection<NestedSetNode> nestedSetNodes,Boolean left,Integer index){
		Collection<NestedSetNode> result = new ArrayList<>();
		for(NestedSetNode nestedSetNode : nestedSetNodes)
			if( (Boolean.TRUE.equals(left) && nestedSetNode.getLeftIndex()>=index) || (Boolean.FALSE.equals(left) && nestedSetNode.getRightIndex()>=index) )
				result.add(nestedSetNode);
		return result;
	}
	
	private void updateBoundaries(NestedSetNode node,Integer step,Boolean left){
		if(left==null || left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_LEFT_INDEX, step);
		if(left==null || !left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_RIGHT_INDEX, step);
	}
	
	
	/**/
	
	private static final String SUB_TREE_ACTION_LOG_FORMAT = "{} sub tree ({}). size = {}. Elements = {}";
		
}
