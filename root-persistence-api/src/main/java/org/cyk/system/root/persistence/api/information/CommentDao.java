package org.cyk.system.root.persistence.api.information;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface CommentDao extends JoinGlobalIdentifierDao<Comment,Comment.SearchCriteria> {
	
}
