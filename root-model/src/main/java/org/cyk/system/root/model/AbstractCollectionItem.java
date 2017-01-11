package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @MappedSuperclass @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL,crudInheritanceStrategy=CrudInheritanceStrategy.ALL)
public abstract class AbstractCollectionItem<COLLECTION> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	/*
	 * an item can be a simple attribute of another model bean. 
	 * In that case , it might not belongs to a collection. 
	 */
	@ManyToOne protected COLLECTION collection;
	
	public AbstractCollectionItem(COLLECTION collection,String code,String name) {
		super(code, name, null, null);
		this.collection = collection;
	}
	/*
	@Override
	public String toString() {
		return "("+collection+")"+super.toString();
	}*/
	
	public static final String FIELD_COLLECTION = "collection";
	
}