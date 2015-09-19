package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollectionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class BusinessService extends AbstractCollectionItem<BusinessServiceCollection> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public BusinessService(BusinessServiceCollection collection,String code,String name) {
		super(collection,code, name);
	}

	
}