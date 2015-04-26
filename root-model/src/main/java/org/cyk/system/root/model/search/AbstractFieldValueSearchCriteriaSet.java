package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.computation.DataReadConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	protected DataReadConfig readConfig = new DataReadConfig();
	protected Collection<AbstractFieldValueSearchCriteria<?>> criterias = new ArrayList<>();
	
	protected void setStringSearchCriteria(StringSearchCriteria stringSearchCriteria,String value){
		stringSearchCriteria.setValue(value);
		criterias.add(stringSearchCriteria);
	}
	
}
