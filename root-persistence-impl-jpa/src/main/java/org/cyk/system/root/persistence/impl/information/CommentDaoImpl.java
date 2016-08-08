package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.persistence.api.information.CommentDao;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class CommentDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<Comment,Comment.SearchCriteria> implements CommentDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	

}
 