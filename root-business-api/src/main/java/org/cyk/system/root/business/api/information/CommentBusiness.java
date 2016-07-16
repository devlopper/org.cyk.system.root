package org.cyk.system.root.business.api.information;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;

public interface CommentBusiness extends TypedBusiness<Comment> {

	Collection<Comment> findByCriteria(SearchCriteria searchCriteria);
	Long countByCriteria(SearchCriteria searchCriteria);
	
}
