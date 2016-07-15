package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.information.CommentBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.persistence.api.information.CommentDao;

@Stateless
public class CommentBusinessImpl extends AbstractTypedBusinessService<Comment, CommentDao> implements CommentBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public CommentBusinessImpl(CommentDao dao) {
		super(dao); 
	}
	
}
