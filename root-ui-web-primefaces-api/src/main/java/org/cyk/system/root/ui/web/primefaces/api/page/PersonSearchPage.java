package org.cyk.system.root.ui.web.primefaces.api.page;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.primefaces.model.SortOrder;

@Named @RequestScoped
public class PersonSearchPage extends AbstractSearchPage<Person, Person, PersonBusiness> implements Serializable {

	private static final long serialVersionUID = 2069491746669275213L;

	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}

	@Override
	protected Collection<Person> __load__(int first, int pageSize,String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer __count__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
