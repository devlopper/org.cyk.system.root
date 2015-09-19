package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass @Getter @Setter @NoArgsConstructor
public abstract class AbstractCollection<ITEM> extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@Transient protected Collection<ITEM> collection = new ArrayList<>();

}
