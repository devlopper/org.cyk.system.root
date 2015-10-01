package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class IntervalDaoImpl extends AbstractEnumerationDaoImpl<Interval> implements IntervalDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByCollection;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByCollection, _select().where("collection"));
    }
    
	@Override
	public Collection<Interval> readByCollection(IntervalCollection collection) {
		return namedQuery(readByCollection).parameter("collection", collection)
                .resultMany();
	}

}
 