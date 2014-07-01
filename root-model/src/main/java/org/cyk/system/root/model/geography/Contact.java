package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity
@Inheritance(strategy=InheritanceType.JOINED)
@AllArgsConstructor @NoArgsConstructor 
@ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Contact extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
		
	@ManyToOne @NotNull private ContactCollection collection;
	
	@NotNull @Column(nullable=false)
	private Byte orderIndex;
	
}
