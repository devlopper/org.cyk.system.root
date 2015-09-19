package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollectionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class UserInterface extends AbstractCollectionItem<UserInterfaceCollection> implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	public UserInterface(UserInterfaceCollection collection,String code,String name) {
		super(collection,code, name);
	}

	
}