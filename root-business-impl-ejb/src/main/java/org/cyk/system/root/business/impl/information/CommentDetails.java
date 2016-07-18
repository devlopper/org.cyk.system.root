package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.information.Comment;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Input.RendererStrategy;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class CommentDetails extends AbstractOutputDetails<Comment> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String date,party,type,message;
	@Input(rendererStrategy=RendererStrategy.ADMINISTRATION) @InputText private String identifiableGlobalIdentifier;
	
	public CommentDetails(Comment comment) {
		super(comment);
		if(comment.getGlobalIdentifier()!=null){
			date = formatDateTime(comment.getGlobalIdentifier().getCreationDate());
			party = formatUsingBusiness(comment.getGlobalIdentifier().getCreatedBy());
		}
		type = formatUsingBusiness(comment.getType());
		identifiableGlobalIdentifier = comment.getIdentifiableGlobalIdentifier().getIdentifier();
		message = comment.getMessage();
	}
	
}