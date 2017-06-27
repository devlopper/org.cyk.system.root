package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.AbstractDataTreeDetails;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataTreeDetails extends AbstractDataTreeDetails<DataTree,DataTreeType> implements Serializable {

	private static final long serialVersionUID = -4747519269632371426L;

	public DataTreeDetails(DataTree dataTree) {
		super(dataTree);
	}
	
}
