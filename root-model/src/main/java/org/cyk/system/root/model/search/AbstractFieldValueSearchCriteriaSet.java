package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	protected Collection<AbstractFieldValueSearchCriteria<?>> criterias = new ArrayList<>();
	
	
}
