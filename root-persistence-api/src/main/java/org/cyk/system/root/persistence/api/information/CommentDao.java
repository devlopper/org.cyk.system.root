package org.cyk.system.root.persistence.api.information;

import java.util.Collection;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;
import org.cyk.system.root.persistence.api.TypedDao;

public interface CommentDao extends TypedDao<Comment> {

	Collection<Comment> readByCriteria(SearchCriteria searchCriteria);
	Long countByCriteria(SearchCriteria searchCriteria);
	
}
