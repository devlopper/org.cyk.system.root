package org.cyk.system.root.model;

import java.io.Serializable;

import org.cyk.utility.common.helper.CollectionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class IdentifiableRuntimeCollection<T> extends CollectionHelper.Instance<T> implements Serializable {

	private static final long serialVersionUID = -130189077130420874L;

	@Override
	public IdentifiableRuntimeCollection<T> setSynchonizationEnabled(Boolean synchonizationEnabled) {
		return (IdentifiableRuntimeCollection<T>) super.setSynchonizationEnabled(synchonizationEnabled);
	}

}
