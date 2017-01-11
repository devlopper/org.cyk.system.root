package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class IdentifiableCollection extends AbstractEnumeration implements Serializable  {

	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne private IdentifiableCollectionType type;
	
	@Transient private IdentifiableRuntimeCollection<IdentifiableCollectionItem> collection;
	
	public IdentifiableCollection setType(IdentifiableCollectionType type){
		this.type = type;
		return this;
	}
	
	public IdentifiableRuntimeCollection<IdentifiableCollectionItem> getCollection(){
		if(collection==null)
			collection = new IdentifiableRuntimeCollection<>();
		return collection;
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
}

