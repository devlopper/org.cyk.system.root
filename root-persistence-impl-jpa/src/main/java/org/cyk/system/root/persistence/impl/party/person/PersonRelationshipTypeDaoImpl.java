package org.cyk.system.root.persistence.impl.party.person;

import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeDao;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;

public class PersonRelationshipTypeDaoImpl extends AbstractDataTreeDaoImpl<PersonRelationshipType,PersonRelationshipTypeGroup> implements PersonRelationshipTypeDao {

	private static final long serialVersionUID = 6920278182318788380L;

}
