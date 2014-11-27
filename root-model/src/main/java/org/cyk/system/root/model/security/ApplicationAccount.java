package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class ApplicationAccount extends UserAccount implements Serializable {

	private static final long serialVersionUID = -23914558440705885L;
	
}
