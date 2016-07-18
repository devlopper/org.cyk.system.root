package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Comment extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	public static final Collection<Class<? extends AbstractIdentifiable>> USER_DEFINED_COMMENTABLE_CLASSES = new HashSet<>();
	
	@ManyToOne private CommentType type;
	
	@ManyToOne private GlobalIdentifier identifiableGlobalIdentifier;
	
	@Column(nullable=false,length=1024 * 1) @NotNull private String message;

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<GlobalIdentifier> globalIdentifiers = new ArrayList<>();
		protected Collection<CommentType> commentTypes = new ArrayList<>();
		
		public SearchCriteria(){
			
		}
		
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			this.globalIdentifiers.addAll(globalIdentifiers);
			return this;
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return addGlobalIdentifiers(Arrays.asList(globalIdentifier));
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
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
	public static final String FIELD_MESSAGE = "message";
}
