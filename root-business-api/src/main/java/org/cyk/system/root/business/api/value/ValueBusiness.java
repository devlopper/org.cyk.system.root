package org.cyk.system.root.business.api.value;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.value.Value;

public interface ValueBusiness extends TypedBusiness<Value> {

	void setRandomly(Collection<Value> values);
	
}
