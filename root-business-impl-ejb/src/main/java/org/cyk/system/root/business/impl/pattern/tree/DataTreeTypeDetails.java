package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.AbstractDataTreeTypeDetails;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataTreeTypeDetails extends AbstractDataTreeTypeDetails<DataTreeType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	public DataTreeTypeDetails(DataTreeType dataTreeType) {
		super(dataTreeType);
	}
	
}
