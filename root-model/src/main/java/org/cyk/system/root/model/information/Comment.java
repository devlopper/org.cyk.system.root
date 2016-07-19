package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Comment extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne private CommentType type;
	
	@Column(nullable=false,length=1024 * 1) @NotNull private String message;

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<CommentType> commentTypes = new ArrayList<>();
				
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
		public SearchCriteria addCommentTypes(Collection<CommentType> commentTypes){
			this.commentTypes.addAll(commentTypes);
			return this;
		}
		public SearchCriteria addCommentType(CommentType commentType){
			return addCommentTypes(Arrays.asList(commentType));
		}
	}
	
	/**/
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(Comment.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(Comment.class,object);
	}
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(Comment.class, aClass);
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
	public static final String FIELD_MESSAGE = "message";
}
