package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @Entity
public class CommentType extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public CommentType(String code, String libelle) {
		super(code, libelle,null, null);
	}
	
	
}