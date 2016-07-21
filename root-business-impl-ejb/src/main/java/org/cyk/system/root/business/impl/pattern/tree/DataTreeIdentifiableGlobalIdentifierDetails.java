package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;

@Getter @Setter
public class DataTreeIdentifiableGlobalIdentifierDetails extends AbstractJoinGlobalIdentifierDetails<DataTreeIdentifiableGlobalIdentifier> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	
	public DataTreeIdentifiableGlobalIdentifierDetails(DataTreeIdentifiableGlobalIdentifier dataTreeIdentifiableGlobalIdentifier) {
		super(dataTreeIdentifiableGlobalIdentifier);
		
	}
	
}