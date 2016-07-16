package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.information.CommentDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.Utils;

public class CommentDaoImpl extends AbstractTypedDao<Comment> implements CommentDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCriteria,countByCriteria;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCriteria, "SELECT r FROM Comment r WHERE r.identifiableGlobalIdentifier.identifier IN :globalIdentifiers AND "
				+ " r.type.identifier IN :typeIdentifiers ");
	}

	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter("globalIdentifiers", Utils.getGlobalIdentfierValues(((SearchCriteria)searchCriteria).getGlobalIdentifiers()));
		queryWrapper.parameter("typeIdentifiers", ids(((SearchCriteria)searchCriteria).getCommentTypes()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Comment> readByCriteria(SearchCriteria criteria) {
		QueryWrapper<?> queryWrapper = namedQuery(readByCriteria);
		applySearchCriteriaParameters(queryWrapper, criteria);
		return (Collection<Comment>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(SearchCriteria searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applySearchCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
	
	

}
 