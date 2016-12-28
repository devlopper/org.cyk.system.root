package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.value.ValueCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ValueCollectionDetails extends AbstractOutputDetails<ValueCollection> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	
	public ValueCollectionDetails(ValueCollection languageCollection) {
		super(languageCollection);
	}
	

	
}