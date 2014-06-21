package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.utility.common.validation.Client;

@Getter @Setter 
@Entity @NoArgsConstructor
public class Party extends AbstractIdentifiable  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	protected String firstName;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private ContactCollection contactManager = new ContactCollection();
	
}