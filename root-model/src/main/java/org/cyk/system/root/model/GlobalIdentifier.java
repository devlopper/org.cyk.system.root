package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @EqualsAndHashCode(callSuper=false,of="identifier")
public class GlobalIdentifier extends AbstractModelElement implements Identifiable<String>, Serializable {

	private static final long serialVersionUID = -8743545996393946779L;

	@Id
	private String identifier;
	
	@Override
	public String getUiString() {
		return toString();
	}

	@Override
	public String toString() {
		return identifier;
	}
	
}
