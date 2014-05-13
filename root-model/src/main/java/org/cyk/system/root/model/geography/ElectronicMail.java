package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor
public class ElectronicMail extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	private String address;
	
	public ElectronicMail() {}

	public ElectronicMail(ContactManager manager,String address) {
		super(manager,null);
		this.address = address;
	}
		
	@Override
	public String toString() {
		return address;
	}
}
