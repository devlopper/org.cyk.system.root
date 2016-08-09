package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.persistence.api.geography.PostalBoxDao;

public class PostalBoxDaoImpl extends AbstractContactDaoImpl<PostalBox> implements PostalBoxDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(PostalBox.FIELD_VALUE));
    }
    
	@Override
	public Collection<PostalBox> readByValue(String address) {
		return namedQuery(readByValue).parameter(PostalBox.FIELD_VALUE, address).resultMany();
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(PostalBox.FIELD_VALUE, address).resultOne();
	}
	
   
 
}
 