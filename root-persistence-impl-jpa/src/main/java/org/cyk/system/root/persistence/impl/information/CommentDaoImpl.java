package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.persistence.api.information.CommentDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class CommentDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<Comment,Comment.SearchCriteria> implements CommentDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	@Override
	protected String getReadByCriteriaQueryString() {
		return super.getReadByCriteriaQueryString()+ " AND r.type.identifier IN :typeIdentifiers ";
	}

	@Override
	protected void applySearchCriteriaParameters(QueryWrapper<?> queryWrapper,AbstractFieldValueSearchCriteriaSet searchCriteria) {
		super.applySearchCriteriaParameters(queryWrapper, searchCriteria);
		queryWrapper.parameter("typeIdentifiers", ids(((SearchCriteria)searchCriteria).getCommentTypes()));
	}

}
 