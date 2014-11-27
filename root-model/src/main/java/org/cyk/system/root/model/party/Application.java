package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.security.License;

@Entity @Getter @Setter @NoArgsConstructor
public class Application extends Party implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@OneToOne
	private License license;
	
}
