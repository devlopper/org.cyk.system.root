package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.DateSearchCriteria;
import org.cyk.system.root.model.search.StringSearchCriteria;

@Getter @Setter
public class UserAccountSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 3134811510557411588L;

	private DateSearchCriteria creationDateSearchCriteria;
	private StringSearchCriteria usernameSearchCriteria;
	private Collection<UserAccount> userAccountExcluded = new ArrayList<>();
	private Collection<Role> roleExcluded = new ArrayList<>();
	
	public UserAccountSearchCriteria(String username){
		usernameSearchCriteria = new StringSearchCriteria();
		setStringSearchCriteria(usernameSearchCriteria, username);
	}
	
}
