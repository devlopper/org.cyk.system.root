package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;

@Getter @Setter @Entity @NoArgsConstructor
public class BusinessService extends AbstractCollectionItem<BusinessServiceCollection> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	
	
}