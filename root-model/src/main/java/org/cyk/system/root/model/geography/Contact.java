package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Model.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.Model.CrudStrategy;

@Getter @Setter @Entity
@Inheritance(strategy=InheritanceType.JOINED)
@AllArgsConstructor @NoArgsConstructor 
@Model(crudStrategy=CrudStrategy.ENUMERATION,crudInheritanceStrategy=CrudInheritanceStrategy.CHILDREN_ONLY)
public class Contact extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
		
	@ManyToOne private ContactManager manager;
	
	private Byte orderIndex;
	
}
