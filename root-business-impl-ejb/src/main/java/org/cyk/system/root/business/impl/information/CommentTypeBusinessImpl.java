package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.CommentTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.CommentType;
import org.cyk.system.root.persistence.api.information.CommentTypeDao;

public class CommentTypeBusinessImpl extends AbstractEnumerationBusinessImpl<CommentType, CommentTypeDao> implements CommentTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public CommentTypeBusinessImpl(CommentTypeDao dao) {
		super(dao); 
	}   
	
}
