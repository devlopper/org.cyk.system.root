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
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity
@Inheritance(strategy=InheritanceType.JOINED)
@AllArgsConstructor @NoArgsConstructor 
@ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Contact extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	/*
	 * a contact can compose another model bean. 
	 * In that case , it might not belongs to a collection , not have an order index 
	 */
	
	@ManyToOne private ContactCollection collection;
	
	private Byte orderIndex;
	
	/**/
	
	public static final String FIELD_COLLECTION = "collection";
	public static final String FIELD_ORDER_INDEX = "orderIndex";
	
}
