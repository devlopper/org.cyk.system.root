package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class IdentifiableCollectionItem extends AbstractCollectionItem<IdentifiableCollection> implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@Transient @Accessors(chain=true) private AbstractIdentifiable joinedIdentifiable;

}
