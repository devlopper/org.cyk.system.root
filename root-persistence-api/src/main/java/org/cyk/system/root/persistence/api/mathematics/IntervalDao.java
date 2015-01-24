package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.persistence.api.TypedDao;

public interface IntervalDao extends TypedDao<Interval> {

	Collection<Interval> readByCollection(IntervalCollection collection);

}
