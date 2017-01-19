package org.cyk.system.root.business.api.party;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Party.PartySearchCriteria;

public interface AbstractPartyBusiness<PARTY extends Party,SEARCH_CRITERIA extends PartySearchCriteria> extends TypedBusiness<PARTY> {

	Collection<PARTY> findByCriteria(SEARCH_CRITERIA criteria);
	Long countByCriteria(SEARCH_CRITERIA criteria);
	
	Collection<ContactCollection> getContactCollections(Collection<PARTY> parties);
	
	/**/
	/*
	void completeInstanciationOfOneFromValues(PARTY party,CompletePartyInstanciationOfOneFromValuesArguments<PARTY> arguments);
	void completeInstanciationOfManyFromValues(List<PARTY> parties,CompletePartyInstanciationOfManyFromValuesArguments<PARTY> arguments);
	*/
	@Getter @Setter
	public static class CompletePartyInstanciationOfOneFromValuesArguments<PARTY extends Party> extends AbstractCompleteInstanciationOfOneFromValuesArguments<PARTY> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		protected Integer codeIndex,creationDateIndex,nameIndex,birthDateIndex;
		
	}
	
	@Getter @Setter
	public static class CompletePartyInstanciationOfManyFromValuesArguments<PARTY extends Party> extends AbstractCompleteInstanciationOfManyFromValuesArguments<PARTY> implements Serializable{

		private static final long serialVersionUID = 6568108456054174796L;
		
		private CompletePartyInstanciationOfOneFromValuesArguments<PARTY> instanciationOfOneFromValuesArguments = new CompletePartyInstanciationOfOneFromValuesArguments<>();
		
	}
}
