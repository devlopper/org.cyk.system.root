package org.cyk.system.root.business.impl.information;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.information.CommentBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;
import org.cyk.system.root.persistence.api.information.CommentDao;

@Stateless
public class CommentBusinessImpl extends AbstractTypedBusinessService<Comment, CommentDao> implements CommentBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public CommentBusinessImpl(CommentDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Comment> findByCriteria(final SearchCriteria searchCriteria) {
		return dao.readByCriteria(searchCriteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SearchCriteria searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
}
