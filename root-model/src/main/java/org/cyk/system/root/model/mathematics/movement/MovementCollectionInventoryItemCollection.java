package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementCollectionInventoryItemCollection extends AbstractCollection<MovementCollectionInventoryItemCollectionItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	
}

