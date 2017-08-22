package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=BusinessServiceCollection.class)
public class BusinessService extends AbstractCollectionItem<BusinessServiceCollection> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	
	
}