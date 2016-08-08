package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class IdentifiableCollectionItem extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected Collection<IdentifiableCollectionType> commentTypes = new ArrayList<>();
				
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
		public SearchCriteria addCommentTypes(Collection<IdentifiableCollectionType> commentTypes){
			this.commentTypes.addAll(commentTypes);
			return this;
		}
		public SearchCriteria addCommentType(IdentifiableCollectionType commentType){
			return addCommentTypes(Arrays.asList(commentType));
		}
	}
	
	/**/
	
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(IdentifiableCollectionItem.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(IdentifiableCollectionItem.class,object);
	}
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(IdentifiableCollectionItem.class, aClass);
	}
	
	/**/
	
}
