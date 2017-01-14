package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass @Deprecated
public abstract class AbstractLog extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	/**/
	
}
