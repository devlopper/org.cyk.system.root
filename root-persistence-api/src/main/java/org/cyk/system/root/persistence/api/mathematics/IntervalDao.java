package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface IntervalDao extends AbstractEnumerationDao<Interval> {

	Collection<Interval> readByCollection(IntervalCollection collection);

}
