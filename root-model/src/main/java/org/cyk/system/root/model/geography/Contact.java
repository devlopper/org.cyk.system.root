package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Inheritance(strategy=InheritanceType.JOINED)
@ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Contact extends AbstractCollectionItem<ContactCollection> implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;

	public Contact() {
		super();
	}

	public Contact(ContactCollection collection, Byte orderNumber) {
		super(collection, null, null);
		setOrderNumber(orderNumber);
	}
	
}
