package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor
public class PostalBox extends Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 923076998880521464L;

	private String value;
	

	@Override
	public String toString() {
		return value;
	}
}
