package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class IdentifiableCollection extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne private IdentifiableCollectionType type;
	
	/**/
	
	public static final String FIELD_TYPE = "type";
}

