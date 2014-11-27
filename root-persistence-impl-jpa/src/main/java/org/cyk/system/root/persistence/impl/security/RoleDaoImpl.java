package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class RoleDaoImpl extends AbstractEnumerationDaoImpl<Role> implements RoleDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByClass;
	
	@Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByClass, "SELECT role FROM Role role WHERE TYPE(role) = :aClass");
    }

	@SuppressWarnings("unchecked")
	@Override
	public <ROLE extends Role> ROLE readByClass(Class<ROLE> aClass) {
		return  (ROLE) namedQuery(readByClass).parameter("aClass", aClass).resultOne();
	}
    
   

}
 