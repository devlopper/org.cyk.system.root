package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @MappedSuperclass @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL,crudInheritanceStrategy=CrudInheritanceStrategy.ALL)
public abstract class AbstractCollectionItem<COLLECTION> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	/*
	 * an item can be a simple attribute of another model bean. 
	 * In that case , it might not belongs to a collection. 
	 */
	@ManyToOne @JoinColumn(name=COLUMN_COLLECTION) @Accessors(chain=true) protected COLLECTION collection;
	
	public AbstractCollectionItem(COLLECTION collection,String code,String name) {
		super(code, name, null, null);
		this.collection = collection;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractCollectionItem<COLLECTION> setCollectionFromCode(String code){
		this.collection = (COLLECTION) InstanceHelper.getInstance().getByIdentifier(ClassHelper.getInstance().getParameterAt(getClass(), 0, AbstractCollection.class), code
				, ClassHelper.Listener.IdentifierType.BUSINESS);
		return this;
	}
	
	public static final String FIELD_COLLECTION = "collection";
	
	public static final String COLUMN_COLLECTION = "collection";
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Filter<T extends AbstractCollectionItem<?>> extends AbstractEnumeration.Filter<T> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
		public Filter() {
			
		}
    }
}