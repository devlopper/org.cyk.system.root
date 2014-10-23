package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.validation.Client;

@Getter @Setter //@ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
@Entity @NoArgsConstructor @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) 
public class Party extends AbstractIdentifiable  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Input
	@InputText
	@NotNull(groups=Client.class)
	//@Pattern(regexp="(\\w)+",groups=Client.class)
	@Column(nullable=false)
	protected String name;
	 
	/**
	 * This is an image which visually represent this party
	 */
	//TODO input to be handled
	@OneToOne(cascade=CascadeType.ALL)
    protected File image;
	
	//TODO input to be handled
	//@Input
	@OneToOne protected ContactCollection contactCollection = new ContactCollection();
	
	public Party(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String getUiString() {
		return name;
	}



	
}