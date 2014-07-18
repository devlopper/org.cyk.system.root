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
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
@Entity @NoArgsConstructor @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) 
public class Party extends AbstractIdentifiable  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@UIField
	@NotNull(groups=Client.class)
	//@Pattern(regexp="(\\w)+",groups=Client.class)
	@Column(nullable=false)
	protected String name;
	
	/**
	 * This is an image which visually represent this party
	 */
	@OneToOne(cascade=CascadeType.ALL)
    protected File image;
	
	@UIField
	@OneToOne protected ContactCollection contactCollection = new ContactCollection();
	
}