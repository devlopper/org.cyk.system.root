package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @AllArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class ElectronicMail extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@Email @NotNull @Column(unique=true,nullable=false)
	private String address;
	
	public ElectronicMail() {}

	public ElectronicMail(ContactCollection collection,String address) {
		super(collection,null);
		this.address = address;
	}
		
	@Override
	public String toString() {
		return address;
	}
	
	@Override
	public String getUiString() {
		return address;
	}
	
	public static final String FIELD_ADDRESS = "address";
}
