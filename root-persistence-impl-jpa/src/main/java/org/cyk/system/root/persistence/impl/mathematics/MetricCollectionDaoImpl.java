package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionDaoImpl;

public class MetricCollectionDaoImpl extends AbstractCollectionDaoImpl<MetricCollection,Metric> implements MetricCollectionDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByTypes;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByTypes, _select().whereIdentifierIn(MetricCollection.FIELD_TYPE));
	}
	
	@Override
	public Collection<MetricCollection> readByTypes(Collection<MetricCollectionType> types) {
		return namedQuery(readByTypes).parameterIdentifiers(types).resultMany();
	}

}
 