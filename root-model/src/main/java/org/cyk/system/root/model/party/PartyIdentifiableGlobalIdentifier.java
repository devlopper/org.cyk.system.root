package org.cyk.system.root.model.party;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A join between a party and an identifiable.
 * Define authorized actions on persisted data. It is DATA CENTRIC.
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @Entity  @NoArgsConstructor 
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {PartyIdentifiableGlobalIdentifier.FIELD_PARTY
		,PartyIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER,PartyIdentifiableGlobalIdentifier.FIELD_ROLE})})
public class PartyIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	 
	@ManyToOne @JoinColumn(name=COLUMN_PARTY) @NotNull @Accessors(chain=true) private Party party;
	@ManyToOne @JoinColumn(name=COLUMN_ROLE) @NotNull @Accessors(chain=true) private PartyBusinessRole role;
	
	@Override
	public PartyIdentifiableGlobalIdentifier setIdentifiableGlobalIdentifier(GlobalIdentifier identifiableGlobalIdentifier) {
		return (PartyIdentifiableGlobalIdentifier) super.setIdentifiableGlobalIdentifier(identifiableGlobalIdentifier);
	}
	
	public static final String FIELD_PARTY = "party";
	public static final String FIELD_ROLE = "role";
	
	public static final String COLUMN_PARTY = FIELD_PARTY;
	public static final String COLUMN_ROLE = FIELD_ROLE;
	
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
	
	/**/
	
	public static class Filter extends AbstractJoinGlobalIdentifier.Filter<PartyIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
		
    }
}