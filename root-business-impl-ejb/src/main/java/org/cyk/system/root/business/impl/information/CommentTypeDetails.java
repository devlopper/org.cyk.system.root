package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.information.CommentType;

@Getter @Setter
public class CommentTypeDetails extends AbstractEnumerationDetails<CommentType> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	public CommentTypeDetails(CommentType commentType) {
		super(commentType);
		
	}
	
}