package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity 
@Getter @Setter @NoArgsConstructor
public class UserInterfaceCollection extends AbstractCollection<UserInterface> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

}
