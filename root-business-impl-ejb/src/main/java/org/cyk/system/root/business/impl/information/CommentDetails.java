package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.information.Comment;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class CommentDetails extends AbstractJoinGlobalIdentifierDetails<Comment> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String date,party,message;
	
	public CommentDetails(Comment comment) {
		super(comment);
		if(comment.getGlobalIdentifier()!=null){
			date = formatDateTime(comment.getGlobalIdentifier().getCreationDate());
			party = formatUsingBusiness(comment.getGlobalIdentifier().getCreatedBy());
		}
		message = comment.getMessage();
	}
	
	public static final String FIELD_DATE = "date";
	public static final String FIELD_PARTY = "party";
	public static final String FIELD_MESSAGE = "message";
}