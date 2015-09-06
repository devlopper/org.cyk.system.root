package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Entity
@Getter @Setter @NoArgsConstructor
public class SecretQuestion extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -1426919647717880937L;
	
}
