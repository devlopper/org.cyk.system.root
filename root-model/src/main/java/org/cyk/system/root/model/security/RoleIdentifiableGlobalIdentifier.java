package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

/**
 * A join between a role and an identifiable
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor 
public class RoleIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private Role role;
	
	@Embedded private Rud rud = new Rud();
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
	}
	
	public static void define(Class<? extends AbstractIdentifiable> aClass){
		define(RoleIdentifiableGlobalIdentifier.class, aClass);
	}
	public static Boolean isUserDefinedClass(Class<?> aClass){
		return isUserDefinedClass(RoleIdentifiableGlobalIdentifier.class,aClass);
	}
	public static Boolean isUserDefinedObject(Object object){
		return isUserDefinedObject(RoleIdentifiableGlobalIdentifier.class,object);
	}
	
}