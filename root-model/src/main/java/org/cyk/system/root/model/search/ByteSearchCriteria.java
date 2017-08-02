package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ByteSearchCriteria extends AbstractNumberSearchCriteria<Byte> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	public ByteSearchCriteria(Byte value) {
		super(value);
	}
	
	public ByteSearchCriteria(ByteSearchCriteria criteria) {
		super(criteria);
	}

}
